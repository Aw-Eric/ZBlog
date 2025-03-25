package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.example.annotation.SystemLog;
import org.example.domain.ResponseResult;
import org.example.domain.dto.AddUserDto;
import org.example.domain.dto.ChangeUserStatusDto;
import org.example.domain.dto.UpdateUserDto;
import org.example.domain.dto.UserListDto;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @SystemLog(businessName = "用户列表")
    @Operation(summary = "用户列表", description = "获取用户列表")
    public ResponseResult list(
            @Parameter(description = "页码", required = true) Integer pageNum,
            @Parameter(description = "每页数量", required = true) Integer pageSize,
            @Parameter(description = "用户名") UserListDto userListDto) {
        return userService.pageList(pageNum, pageSize, userListDto);
    }

    @PostMapping
    @SystemLog(businessName = "新增用户")
    @Operation(summary = "新增用户", description = "新增用户")
    public ResponseResult add(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "添加用户dto") @RequestBody AddUserDto addUserDto) {
        return userService.add(addUserDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除用户")
    @Operation(summary = "删除用户", description = "删除用户")
    public ResponseResult deleteUser(
            @PathVariable("id") @Parameter(description = "用户id", required = true) List<Long> id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "查询用户信息")
    @Operation(summary = "查询用户", description = "查询用户信息")
    public ResponseResult getUserInfoById(
            @PathVariable("id") @Parameter(description = "用户id", required = true) Long id) {
        return userService.getUserInfoById(id);
    }

    @PutMapping
    @SystemLog(businessName = "修改用户信息")
    @Operation(summary = "修改用户信息", description = "修改用户信息")
    public ResponseResult updateUserInfo(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "用户信息") @RequestBody UpdateUserDto user) {
        return userService.updateUser(user);
    }

    @PutMapping("/changeStatus")
    @SystemLog(businessName = "修改用户状态")
    @Operation(summary = "修改用户状态", description = "修改用户状态")
    public ResponseResult changeStatus(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "用户状态") @RequestBody ChangeUserStatusDto changeUserStatusDto) {
        return userService.changeStatus(changeUserStatusDto);
    }
}
