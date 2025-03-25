package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.annotation.SystemLog;
import org.example.domain.ResponseResult;
import org.example.domain.entity.User;
import org.example.domain.vo.AdminUserInfoVo;
import org.example.domain.vo.RoutersVo;
import org.example.enums.AppHttpCodeEnum;
import org.example.exception.SystemException;
import org.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "登录", description = "登录相关接口")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login")
    @SystemLog(businessName = "用户登录")
    @Operation(summary = "用户登录", description = "后台用户登录")
    public ResponseResult login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "用户信息") @RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            // 提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @PostMapping("/user/logout")
    @SystemLog(businessName = "用户登出")
    @Operation(summary = "用户登出", description = "后台用户登出")
    public ResponseResult logout() {
        return loginService.logout();
    }

    @GetMapping("getInfo")
    @SystemLog(businessName = "获取用户信息")
    @Operation(summary = "获取用户信息", description = "获取用户信息")
    public ResponseResult<AdminUserInfoVo> getInfo() {
        return loginService.getInfo();
    }

    @GetMapping("/getRouters")
    @SystemLog(businessName = "获取路由")
    @Operation(summary = "获取路由", description = "获取路由")
    public ResponseResult<RoutersVo> getRouters() {
        return loginService.getRouters();
    }

}
