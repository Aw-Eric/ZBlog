package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.annotation.SystemLog;
import org.example.domain.ResponseResult;
import org.example.domain.dto.AddRoleDto;
import org.example.domain.dto.ChangeStatusDto;
import org.example.domain.dto.RoleListDto;
import org.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
@Tag(name = "角色", description = "角色相关接口")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    @SystemLog(businessName = "角色列表")
    @Operation(summary = "角色列表", description = "角色列表")
    public ResponseResult list(
            @Parameter(description = "页码", required = true) Integer pageNum,
            @Parameter(description = "页面大小", required = true) Integer pageSize,
            @Parameter(description = "角色筛选条件") RoleListDto roleListDto
    ) {
        return roleService.roleList(pageNum, pageSize, roleListDto);
    }

    @PutMapping("/changeStatus")
    @SystemLog(businessName = "修改角色状态")
    @Operation(summary = "修改角色状态", description = "修改角色状态")
    public ResponseResult changeStatus(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "修改角色启用信息") @RequestBody ChangeStatusDto changeStatusDto) {
        return roleService.changeStatus(changeStatusDto);
    }

    @PostMapping
    @SystemLog(businessName = "新增角色")
    @Tag(name = "角色", description = "新增角色")
    public ResponseResult addRole(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "角色信息") @RequestBody AddRoleDto addRoleDto) {
        return roleService.add(addRoleDto);
    }
}
