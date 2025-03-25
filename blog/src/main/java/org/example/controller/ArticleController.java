package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.annotation.SystemLog;
import org.example.domain.ResponseResult;
import org.example.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@Tag(name = "文章", description = "文章相关接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    @SystemLog(businessName = "获取热门文章列表")
    @Operation(summary = "热门文章列表", description = "获取热门文章列表")
    public ResponseResult hotArticleList() {
        return articleService.hotArticleList();
    }

    @GetMapping("/articleList")
    @SystemLog(businessName = "获取文章列表")
    @Operation(summary = "文章列表", description = "获取文章列表")
    public ResponseResult articleList(
            @Parameter(description = "页号") @RequestParam Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam Integer pageSize,
            @Parameter(description = "文章类型") @RequestParam Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "获取文章详情")
    @Operation(summary = "文章详情", description = "获取文章详情")
    public ResponseResult getArticleDetail(
            @Parameter(description = "文章id") @PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    @SystemLog(businessName = "更新文章浏览量")
    @Operation(summary = "更新文章浏览量", description = "更新文章浏览量")
    public ResponseResult updateViewCount(
            @Parameter(description = "文章id") @PathVariable Long id) {
        return articleService.updateViewCount(id);
    }
}
