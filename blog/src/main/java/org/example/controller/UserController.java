package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.annotation.SystemLog;
import org.example.domain.ResponseResult;
import org.example.domain.entity.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "用户", description = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @SystemLog(businessName = "获取用户信息")
    @Operation(summary = "获取用户信息", description = "获取用户信息")
    public ResponseResult userInfo() {
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    @Operation(summary = "更新用户信息", description = "更新用户信息")
    public ResponseResult updateUserInfo(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "更新后的用户信息") @RequestBody User user) {
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @SystemLog(businessName = "用户注册")
    @Operation(summary = "用户注册", description = "用户注册")
    public ResponseResult register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "新注册的用户信息") @RequestBody User user) {
        return userService.register(user);
    }
}
