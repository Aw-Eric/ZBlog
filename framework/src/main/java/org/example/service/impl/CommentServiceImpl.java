package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.constants.SystemConstants;
import org.example.domain.ResponseResult;
import org.example.domain.entity.Comment;
import org.example.domain.vo.CommentVo;
import org.example.domain.vo.PageVo;
import org.example.enums.AppHttpCodeEnum;
import org.example.exception.SystemException;
import org.example.mapper.CommentMapper;
import org.example.service.CommentService;
import org.example.service.UserService;
import org.example.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2025-03-11 13:22:01
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    /**
     * 查询文章对应的评论列表
     * @param articleId 文章ID
     * @param pageNum 页码
     * @param pageSize 每页显示数量
     * @return 评论列表
     */
    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        // 查询对应文章的根评论:对articleId进行判断
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConstants.COMMENT_TYPE_ARTICLE.equals(commentType), Comment::getArticleId, articleId);
        // 根评论rootId为-1
        queryWrapper.eq(Comment::getRootId, SystemConstants.ROOT_ID_NULL);
        // 评论类型
        queryWrapper.eq(Comment::getType, commentType);
        // 分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        // 查询所有根评论对应的子评论集合并赋值给对应的属性
        for (CommentVo commentVo : commentVoList) {
            // 查询对应根评论的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            // 将子评论集合赋值给对应的属性
            commentVo.setChildren(children);
        }
        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    /**
     * 添加评论
     * @param comment 评论实体
     * @return 添加结果
     */
    @Override
    public ResponseResult addComment(Comment comment) {
        // 评论内容不能为空
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 通过根评论ID查询对应的子评论集合
     * @param id 根评论ID
     * @return 子评论集合
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);
        return toCommentVoList(comments);
    }

    /**
     * 将Comment集合转换为CommentVo集合
     * @param commentList Comment集合
     * @return CommentVo集合
     */
    private List<CommentVo> toCommentVoList(List<Comment> commentList) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(commentList, CommentVo.class);
        // 遍历vo集合
        for (CommentVo commentVo : commentVos) {
            // 通过createBy查询用户昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            // 通过toCommentUserId查询用户昵称并赋值
            // 如果toCommentUserId不为-1才进行查询
            if (commentVo.getToCommentUserId() != SystemConstants.ROOT_ID_NULL) {
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
            // 通过createBy查询用户头像并赋值
            String avatar = userService.getById(commentVo.getCreateBy()).getAvatar();
            commentVo.setAvatar(avatar);
            commentVo.setAvatar(userService.getById(commentVo.getCreateBy()).getAvatar());
        }
        return commentVos;
    }
}
