package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.domain.entity.UserRole;

import java.util.List;


/**
 * 用户和角色关联表(UserRole)表服务接口
 *
 * @author makejava
 * @since 2025-03-25 19:46:18
 */
public interface UserRoleService extends IService<UserRole> {

    void add(Long id, List<Long> roleIds);
}
