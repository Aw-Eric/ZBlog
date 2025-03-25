package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.constants.SystemConstants;
import org.example.domain.ResponseResult;
import org.example.domain.dto.AddRoleDto;
import org.example.domain.dto.ChangeStatusDto;
import org.example.domain.dto.RoleListDto;
import org.example.domain.dto.UpdateRoleDto;
import org.example.domain.entity.Role;
import org.example.domain.entity.RoleMenu;
import org.example.domain.vo.AdminRoleVo;
import org.example.domain.vo.AdminUpdateRoleVo;
import org.example.domain.vo.PageVo;
import org.example.enums.AppHttpCodeEnum;
import org.example.mapper.RoleMapper;
import org.example.mapper.RoleMenuMapper;
import org.example.service.RoleService;
import org.example.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2025-03-18 15:55:59
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMenuMapper roleMenuMapper;

    /**
     * 根据用户id查询角色权限
     * @param id 用户id
     * @return 角色列表
     */
    @Override
    public List<String> selectRoleByUserId(Long id) {
        // 判断是否是管理员 如果是，返回集合中只需要有admin
        if (id == 1) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add(SystemConstants.USER_ROLE_ADMIN);
            return roleKeys;
        }
        // 否则，查询用户所具有的角色信息
        return getBaseMapper().selectRoleByUserId(id);
    }

    @Override
    public ResponseResult roleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto) {
        // 查询角色列表
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(roleListDto.getStatus()), Role::getStatus, roleListDto.getStatus());
        queryWrapper.like(StringUtils.hasText(roleListDto.getRoleName()), Role::getRoleName, roleListDto.getRoleName());
        queryWrapper.orderByAsc(Role::getRoleSort);
        // 分页查询
        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<AdminRoleVo> adminRoleVos = BeanCopyUtils.copyBeanList(page.getRecords(), AdminRoleVo.class);
        return ResponseResult.okResult(new PageVo(adminRoleVos, page.getTotal()));
    }

    /**
     * 修改角色状态
     * @param changeStatusDto 修改角色状态
     * @return 结果
     */
    @Override
    public ResponseResult changeStatus(ChangeStatusDto changeStatusDto) {
        if (changeStatusDto.getRoleId() == SystemConstants.ADMIN_USER_ID) {
            return ResponseResult.errorResult(AppHttpCodeEnum.CHANGE_STATUS_ILLEGAL);
        }
        if (Objects.isNull(getById(changeStatusDto.getRoleId()))) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Role::getId, changeStatusDto.getRoleId())
                .set(Role::getStatus, changeStatusDto.getStatus());
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    /**
     * 新增角色
     *
     * @param addRoleDto 新增角色信息
     * @return 结果
     */
    @Override
    public ResponseResult add(AddRoleDto addRoleDto) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(addRoleDto.getRoleName())) {
            queryWrapper.eq(Role::getRoleName, addRoleDto.getRoleName());
        }
        if (count(queryWrapper) > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }
        List<RoleMenu> roleMenus = new ArrayList<>();
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        save(role);
        // 搜索角色信息，查询id
        LambdaQueryWrapper<Role> roleQueryWrapper = new LambdaQueryWrapper<>();
        roleQueryWrapper.eq(Role::getRoleName, addRoleDto.getRoleName());
        roleQueryWrapper.eq(Role::getRoleKey, addRoleDto.getRoleKey());
        roleQueryWrapper.eq(Role::getRoleSort, addRoleDto.getRoleSort());
        List<Role> list = list(roleQueryWrapper);
        Long roleId = -1L;
        if (list.size() == 1) {
            roleId = list.get(0).getId();
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }
        for (Long menuId : addRoleDto.getMenuIds()) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenus.add(roleMenu);
        }
        roleMenuMapper.insert(roleMenus);
        return ResponseResult.okResult();
    }

    /**
     * 根据id查询角色信息
     *
     * @param id 角色id
     * @return 角色信息
     */
    @Override
    public ResponseResult getRoleById(Long id) {
        if (Objects.isNull(getById(id))) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getId, id);
        List<Role> list = list(queryWrapper);
        AdminUpdateRoleVo adminUpdateRoleVo = BeanCopyUtils.copyBean(list.get(0), AdminUpdateRoleVo.class);
        return ResponseResult.okResult(adminUpdateRoleVo);
    }

    /**
     * 修改角色信息
     *
     * @param updateRoleDto 修改角色信息
     * @return 结果
     */
    @Override
    public ResponseResult updateRole(UpdateRoleDto updateRoleDto) {
        if (updateRoleDto.getId() == SystemConstants.ADMIN_USER_ID) {
            return ResponseResult.errorResult(AppHttpCodeEnum.CHANGE_STATUS_ILLEGAL);
        }
        if (Objects.isNull(getById(updateRoleDto.getId()))) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        List<RoleMenu> roleMenus = new ArrayList<>();
        Role role = BeanCopyUtils.copyBean(updateRoleDto, Role.class);
        updateById(role);
        LambdaQueryWrapper<RoleMenu> roleMenuQueryWrapper = new LambdaQueryWrapper<>();
        roleMenuQueryWrapper.eq(RoleMenu::getRoleId, updateRoleDto.getId());
        roleMenuMapper.delete(roleMenuQueryWrapper);
        for (Long menuId : updateRoleDto.getMenuIds()) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(updateRoleDto.getId());
            roleMenu.setMenuId(menuId);
            roleMenus.add(roleMenu);
        }
        roleMenuMapper.insert(roleMenus);
        return ResponseResult.okResult();
    }

    /**
     * 删除角色
     *
     * @param ids 角色id
     * @return 结果
     */
    @Override
    public ResponseResult deleteRole(List<Long> ids) {
        for (Long id : ids) {
            if (id == SystemConstants.ADMIN_USER_ID) {
                return ResponseResult.errorResult(AppHttpCodeEnum.CHANGE_STATUS_ILLEGAL);
            }
            if (Objects.isNull(getById(id))) {
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
            }
            LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Role::getId, id).set(Role::getDelFlag, SystemConstants.DEL_FLAG_TRUE);
            update(updateWrapper);
            LambdaQueryWrapper<RoleMenu> roleMenuQueryWrapper = new LambdaQueryWrapper<>();
            roleMenuQueryWrapper.eq(RoleMenu::getRoleId, id);
            roleMenuMapper.delete(roleMenuQueryWrapper);
        }
        return ResponseResult.okResult();
    }
}
