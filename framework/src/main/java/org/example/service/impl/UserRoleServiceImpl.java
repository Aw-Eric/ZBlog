package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.domain.entity.UserRole;
import org.example.mapper.UserRoleMapper;
import org.example.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2025-03-25 19:46:19
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public void add(Long id, List<Long> roleIds) {
        // 添加用户新的角色
        roleIds.forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(id);
            userRole.setRoleId(roleId);
            save(userRole);
        });
    }
}
