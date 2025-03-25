package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.domain.entity.ArticleTag;
import org.example.domain.entity.Tag;

import java.util.List;


/**
 * 文章标签关联表(ArticleTag)表服务接口
 *
 * @author makejava
 * @since 2025-03-20 17:30:31
 */
public interface ArticleTagService extends IService<ArticleTag> {


    List<Long> listTagsIdByArticleId(Long id);
}
