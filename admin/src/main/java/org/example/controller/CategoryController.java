package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.example.annotation.SystemLog;
import org.example.domain.ResponseResult;
import org.example.domain.dto.AddCategoryDto;
import org.example.domain.dto.CategoryListDto;
import org.example.domain.dto.ChangeCategoryStatusDto;
import org.example.domain.dto.UpdateCategoryDto;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    @SystemLog(businessName = "分类列表")
    @Operation(summary = "分类列表", description = "获取分类列表")
    public ResponseResult list(
            @Parameter(description = "页码", required = true) Integer pageNum,
            @Parameter(description = "每页数量", required = true) Integer pageSize,
            @Parameter(description = "分类名称") CategoryListDto categoryListDto) {
        return categoryService.pageList(pageNum, pageSize, categoryListDto);
    }

    @PostMapping
    @SystemLog(businessName = "新增分类")
    @Operation(summary = "新增分类", description = "新增分类")
    public ResponseResult add(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "分类") @RequestBody AddCategoryDto addCategoryDto) {
        return categoryService.add(addCategoryDto);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "获取分类")
    @Operation(summary = "获取分类", description = "获取分类")
    public ResponseResult get(
            @Parameter(description = "分类id", required = true) @PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping
    @SystemLog(businessName = "修改分类")
    @Operation(summary = "修改分类", description = "修改分类")
    public ResponseResult update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "分类") @RequestBody UpdateCategoryDto updateCategoryDto) {
        return categoryService.updateCategory(updateCategoryDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除分类")
    @Operation(summary = "删除分类", description = "删除分类")
    public ResponseResult delete(
            @Parameter(description = "分类id", required = true) @PathVariable List<Long> id) {
        return categoryService.deleteCategory(id);
    }

    @PutMapping("/changeStatus")
    @SystemLog(businessName = "修改分类状态")
    @Operation(summary = "修改分类状态", description = "修改分类状态")
    public ResponseResult changeStatus(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "分类状态") @RequestBody ChangeCategoryStatusDto changeCategoryStatusDto) {
        return categoryService.changeStatus(changeCategoryStatusDto);
    }
}
