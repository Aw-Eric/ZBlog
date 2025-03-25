package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.example.annotation.SystemLog;
import org.example.domain.ResponseResult;
import org.example.domain.dto.UserListDto;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
