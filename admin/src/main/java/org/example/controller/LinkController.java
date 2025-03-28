package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.example.annotation.SystemLog;
import org.example.domain.ResponseResult;
import org.example.domain.dto.LinkListDto;
import org.example.domain.vo.LinkVo;
import org.example.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    @SystemLog(businessName = "友链列表")
    @Operation(summary = "友链列表", description = "获取友链列表")
    public ResponseResult list(
            @Parameter(description = "页码", required = true) Integer pageNum,
            @Parameter(description = "每页数量", required = true) Integer pageSize,
            @Parameter(description = "链接名称") LinkListDto linkListDto) {
        return linkService.pageList(pageNum, pageSize, linkListDto);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "友链详情")
    @Operation(summary = "友链详情", description = "获取友链详情")
    public ResponseResult getById(
            @PathVariable @Parameter(description = "友链ID", required = true) Long id) {
        return linkService.get(id);
    }

    @PutMapping
    @SystemLog(businessName = "修改友链")
    @Operation(summary = "修改友链", description = "修改友链")
    public ResponseResult update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "友链") @RequestBody LinkVo linkVo) {
        return linkService.updateLink(linkVo);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除友链")
    @Operation(summary = "删除友链", description = "删除友链")
    public ResponseResult delete(
            @Parameter(description = "友链ID", required = true) @PathVariable List<Long> id) {
        return linkService.deleteLink(id);
    }
}
