package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.domain.ResponseResult;
import org.example.domain.dto.ChangeStatusDto;
import org.example.domain.dto.RoleListDto;
import org.example.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2025-03-18 15:55:59
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleByUserId(Long userId);

    ResponseResult roleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto);

    ResponseResult changeStatus(ChangeStatusDto changeStatusDto);
}
