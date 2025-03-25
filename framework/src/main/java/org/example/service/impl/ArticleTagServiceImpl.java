package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.domain.entity.ArticleTag;
import org.example.domain.entity.Tag;
import org.example.mapper.ArticleTagMapper;
import org.example.service.ArticleTagService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2025-03-20 17:30:31
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    /**
     * 通过文章id查询标签id列表
     * @param id 文章id
     * @return 标签id列表
     */
    @Override
    public List<Long> listTagsIdByArticleId(Long id) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, id);
        List<ArticleTag> articleTags = list(queryWrapper);
        return articleTags.stream().map(ArticleTag::getTagId).toList();
    }
}
