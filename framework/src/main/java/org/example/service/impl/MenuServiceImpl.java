package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.constants.SystemConstants;
import org.example.domain.ResponseResult;
import org.example.domain.dto.AddMenuDto;
import org.example.domain.entity.Menu;
import org.example.domain.vo.AdminAddRoleMenuVo;
import org.example.domain.vo.AdminMenuVo;
import org.example.domain.vo.MenuVo;
import org.example.enums.AppHttpCodeEnum;
import org.example.mapper.MenuMapper;
import org.example.service.MenuService;
import org.example.utils.BeanCopyUtils;
import org.example.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2025-03-18 15:48:21
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        // 如果是管理员，返回所有权限
        if (SecurityUtils.isAdmin()) {
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU_TYPE_MENU, SystemConstants.MENU_TYPE_BUTTON);
            wrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .toList();
            return perms;
        }
        // 否则返回所具有的权限
        return menuMapper.selectPermsByUserId(id);
    }

    @Override
    public List<MenuVo> selectRouterMenuTreeByUserId(Long userId) {
        List<Menu> menus;
        // 判断是否是管理员
        if (SecurityUtils.isAdmin()) {
            // 如果是，返回所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }
        else {
            // 否则当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        // 构建tree
        // 先找出第一层的菜单 然后去找他们的子菜单设置到children属性中
        List<MenuVo> menuVo = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        return builderMenuTree(menuVo, 0L);
    }

    /**
     * 获取文章列表
     * @param status 文章状态
     * @param menuName 菜单名
     * @return 文章列表
     */
    @Override
    public ResponseResult ArticleList(String status, String menuName) {
        // 构建查询条件
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(status), Menu::getStatus, status);
        queryWrapper.like(StringUtils.hasText(menuName), Menu::getMenuName, menuName);
        // 排序
        queryWrapper.orderByAsc(Menu::getParentId);
        queryWrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
        List<AdminMenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, AdminMenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    @Override
    public ResponseResult add(AddMenuDto addMenuDto) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(addMenuDto.getMenuName())) {
            queryWrapper.eq(Menu::getMenuName, addMenuDto.getMenuName());
        }
        if (StringUtils.hasText(addMenuDto.getPath())) {
            queryWrapper.eq(Menu::getPath, addMenuDto.getPath());
        }
        if (count(queryWrapper) > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }
        Menu menu = BeanCopyUtils.copyBean(addMenuDto, Menu.class);
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuById(Long id) {
        Menu menu = getById(id);
        if (menu == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        AdminMenuVo adminMenuVo = BeanCopyUtils.copyBean(menu, AdminMenuVo.class);
        return ResponseResult.okResult(adminMenuVo);
    }

    @Override
    public ResponseResult updateMenu(AddMenuDto addMenuDto) {
        Menu menu = getById(addMenuDto.getId());
        if (Objects.isNull(menu)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        if (addMenuDto.getParentId().equals(addMenuDto.getId())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        Menu newMenu = BeanCopyUtils.copyBean(addMenuDto, Menu.class);
        updateById(newMenu);
        return ResponseResult.okResult();
    }

    /**
     * 删除菜单
     *
     * @param menuId 菜单id
     * @return 删除结果
     */
    @Override
    public ResponseResult deleteMenu(Long menuId) {
        Menu menu = getById(menuId);
        if (Objects.isNull(menu)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        List<Menu> menus = list(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, menuId));
        if (!menus.isEmpty()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ILLEGAL);
        }
        LambdaUpdateWrapper<Menu> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Menu::getId, menuId).set(Menu::getDelFlag, SystemConstants.DEL_FLAG_TRUE);
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    /**
     * 获取菜单树
     * @return 菜单树
     */
    @Override
    public ResponseResult treeSelect() {
        List<Menu> menus = list(new LambdaQueryWrapper<Menu>().eq(Menu::getStatus, SystemConstants.STATUS_NORMAL));
        List<AdminAddRoleMenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, AdminAddRoleMenuVo.class);
        for (AdminAddRoleMenuVo menuVo : menuVos) {
            menuVo.setLabel(getById(menuVo.getId()).getMenuName());
        }
        return ResponseResult.okResult(builderMenuTrees(menuVos, 0L));
    }

    /**
     * 构建菜单树
     * @param menus 所有菜单
     * @param parentId 父菜单id
     * @return 菜单树
     */
    private List<MenuVo> builderMenuTree(List<MenuVo> menus, Long parentId) {
        List<MenuVo> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .toList();
        return menuTree;
    }

    /**
     * 获取存入参数的子Menu集合
     * @param menu 父Menu
     * @param menus 所有Menu
     * @return 子Menu集合
     */
    private List<MenuVo> getChildren(MenuVo menu, List<MenuVo> menus) {
        List<MenuVo> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m, menus)))
                .toList();
        return childrenList;
    }

    /**
     * 构建菜单树
     *
     * @param menus    所有菜单
     * @param parentId 父菜单id
     * @return 菜单树
     */
    private List<AdminAddRoleMenuVo> builderMenuTrees(List<AdminAddRoleMenuVo> menus, Long parentId) {
        List<AdminAddRoleMenuVo> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildrens(menu, menus)))
                .toList();
        return menuTree;
    }

    /**
     * 获取存入参数的子Menu集合
     *
     * @param menu  父Menu
     * @param menus 所有Menu
     * @return 子Menu集合
     */
    private List<AdminAddRoleMenuVo> getChildrens(AdminAddRoleMenuVo menu, List<AdminAddRoleMenuVo> menus) {
        List<AdminAddRoleMenuVo> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildrens(m, menus)))
                .toList();
        return childrenList;
    }
}
