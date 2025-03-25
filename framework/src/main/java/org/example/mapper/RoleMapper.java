package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2025-03-18 15:55:58
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleByUserId(Long userId);
}
