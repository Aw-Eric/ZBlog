package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apiguardian.api.API;
import org.example.annotation.SystemLog;
import org.example.constants.SystemConstants;
import org.example.domain.ResponseResult;
import org.example.domain.dto.AddCommentDto;
import org.example.domain.entity.Comment;
import org.example.service.CommentService;
import org.example.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Tag(name = "评论", description = "评论相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    @SystemLog(businessName = "获取评论列表")
    @Operation(summary = "评论列表", description = "获取评论列表")
    public ResponseResult commentList(
            @Parameter(description = "文章id") @RequestParam Long articleId,
            @Parameter(description = "页号") @RequestParam Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam Integer pageSize) {
        return commentService.commentList(SystemConstants.COMMENT_TYPE_ARTICLE, articleId, pageNum, pageSize);
    }

    @PostMapping
    @SystemLog(businessName = "添加评论")
    @Operation(summary = "添加评论", description = "添加用户新发布的评论")
    public ResponseResult addComment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "增加到评论信息") @RequestBody AddCommentDto addCommentDto) {
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    @SystemLog(businessName = "获取友链评论列表")
    @Operation(summary = "友链评论列表", description = "获取友链评论列表")
    public ResponseResult linkCommentList(
            @Parameter(description = "页号") @RequestParam Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam Integer pageSize) {
        return commentService.commentList(SystemConstants.COMMENT_TYPE_LINK, null, pageNum, pageSize);
    }
}
