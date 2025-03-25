package org.example.service.impl;

import org.example.utils.SecurityUtils;
import org.springframework.stereotype.Service;

@Service("permissionService")
public class PermissionService {

    /**
     * 判断当前用户是否具有permission
     * @param permission 权限标识
     * @return 是否具有权限
     */
    public boolean hasPermission(String permission) {
        // 如果是超级管理员，直接返回true
        if (SecurityUtils.isAdmin()) {
            return true;
        }
        // 否则获取当前登录用户所具有的权限列表
        return SecurityUtils.getLoginUser().getPermissions().contains(permission);
    }
}
