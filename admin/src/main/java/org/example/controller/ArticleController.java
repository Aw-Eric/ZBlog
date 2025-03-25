package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.annotation.SystemLog;
import org.example.domain.ResponseResult;
import org.example.domain.dto.AddArticleDto;
import org.example.domain.dto.ArticleListDto;
import org.example.domain.dto.UpdateArticleDto;
import org.example.domain.entity.Article;
import org.example.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
@Tag(name = "文章", description = "文章相关接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    @SystemLog(businessName = "添加文章")
    @Operation(summary = "添加文章", description = "添加文章")
    public ResponseResult add(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "文章信息") @RequestBody AddArticleDto addArticleDto) {
        return articleService.add(addArticleDto);
    }

//    @PreAuthorize("permissionService.hasPermission()")
    @GetMapping("/list")
    @SystemLog(businessName = "文章列表")
    @Operation(summary = "文章列表", description = "文章列表")
    public ResponseResult list(
            @Parameter(description = "页码", required = true) Integer pageNum,
            @Parameter(description = "页面大小", required = true) Integer pageSize,
            @Parameter(description = "文章筛选条件") ArticleListDto articleListDto) {
        return articleService.pageList(pageNum, pageSize, articleListDto);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "文章详情")
    @Operation(summary = "文章详情", description = "查询文章详情")
    public ResponseResult detail(
            @Parameter(description = "文章ID", required = true) @PathVariable Long id) {
        return articleService.detail(id);
    }

    @PutMapping
    @SystemLog(businessName = "更新文章")
    @Operation(summary = "更新文章", description = "更新文章")
    public ResponseResult update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "文章信息") @RequestBody UpdateArticleDto updateArticleDto) {
        return articleService.updateArticle(updateArticleDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除文章")
    @Operation(summary = "删除文章", description = "删除文章")
    public ResponseResult delete(
            @Parameter(description = "文章ID", required = true) @PathVariable Long id) {
        return articleService.deleteArticle(id);
    }
}
