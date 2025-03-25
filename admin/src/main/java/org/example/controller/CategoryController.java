package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.example.annotation.SystemLog;
import org.example.domain.ResponseResult;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content/category")
@Tag(name = "分类", description = "分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    @SystemLog(businessName = "分类列表")
    @Operation(summary = "分类列表", description = "分类列表")
    public ResponseResult listAllCategory() {
        return categoryService.listAllCategory();
    }

    @PreAuthorize("@permissionService.hasPermission('content:category:export')")
    @GetMapping("/export")
    @Operation(summary = "导出分类", description = "导出分类为Excel")
    public void export(HttpServletResponse response) {
        categoryService.export(response);
    }
}
