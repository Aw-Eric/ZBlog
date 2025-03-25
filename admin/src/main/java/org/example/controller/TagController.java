package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.annotation.SystemLog;
import org.example.domain.ResponseResult;
import org.example.domain.dto.AddTagDto;
import org.example.domain.dto.TagListDto;
import org.example.domain.dto.UpdateTagDto;
import org.example.domain.vo.PageVo;
import org.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
@Tag(name = "标签", description = "标签相关接口")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    @SystemLog(businessName = "标签列表")
    @Operation(summary = "标签列表", description = "标签列表")
    public ResponseResult<PageVo> list(
            @Parameter(description = "页码", required = true) Integer pageNum,
            @Parameter(description = "页面大小", required = true) Integer pageSize,
            @Parameter(description = "标签筛选条件") TagListDto tagListDto) {
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    @PostMapping
    @SystemLog(businessName = "新增标签")
    @Operation(summary = "新增标签", description = "新增标签")
    public ResponseResult addTag(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "标签信息") @RequestBody AddTagDto addTagDto) {
        return tagService.addTag(addTagDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除标签")
    @Operation(summary = "删除标签", description = "删除标签")
    public ResponseResult deleteTag(
            @Parameter(description = "标签id", required = true) @PathVariable("id") List<Long> id) {
        return tagService.deleteTag(id);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "获取标签信息")
    @Operation(summary = "获取标签信息", description = "获取标签信息")
    public ResponseResult getTagById(
            @Parameter(description = "标签id", required = true) @PathVariable("id") Long id) {
        return tagService.getTagById(id);
    }

    @PutMapping
    @SystemLog(businessName = "更新标签")
    @Operation(summary = "更新标签", description = "更新标签")
    public ResponseResult updateTag(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "标签信息") @RequestBody UpdateTagDto updateTagDto) {
        return tagService.updateTag(updateTagDto);
    }

    @GetMapping("/listAllTag")
    @SystemLog(businessName = "查询所有标签")
    @Operation(summary = "查询所有标签", description = "查询所有标签")
    public ResponseResult listAllTag() {
        return tagService.listAllTag();
    }

}
