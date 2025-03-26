package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.ResponseResult;
import org.example.domain.dto.AddCategoryDto;
import org.example.domain.dto.CategoryListDto;
import org.example.domain.dto.ChangeCategoryStatusDto;
import org.example.domain.dto.UpdateCategoryDto;
import org.example.domain.entity.Category;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2025-03-06 11:15:02
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    void export(HttpServletResponse response);

    ResponseResult pageList(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto);

    ResponseResult add(AddCategoryDto addCategoryDto);

    ResponseResult updateCategory(UpdateCategoryDto updateCategoryDto);

    ResponseResult deleteCategory(List<Long> id);

    ResponseResult getCategoryById(Long id);

    ResponseResult changeStatus(ChangeCategoryStatusDto changeCategoryStatusDto);
}
