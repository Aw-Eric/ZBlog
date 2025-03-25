package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.domain.ResponseResult;
import org.example.domain.dto.AddArticleDto;
import org.example.domain.dto.ArticleListDto;
import org.example.domain.dto.UpdateArticleDto;
import org.example.domain.entity.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto addArticleDto);

    ResponseResult pageList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);

    ResponseResult detail(Long id);

    ResponseResult updateArticle(UpdateArticleDto updateArticleDto);

    ResponseResult deleteArticle(List<Long> id);
}
