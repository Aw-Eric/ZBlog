package org.example.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.annotation.SystemLog;
import org.example.domain.ResponseResult;
import org.example.domain.dto.AddMenuDto;
import org.example.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/menu")
@Tag(name = "菜单", description = "菜单相关接口")
public class MenuController {

    @Autowired
    private MenuService menuService;


    @GetMapping("/list")
    @SystemLog(businessName = "菜单列表")
    @Tag(name = "菜单", description = "菜单列表")
    public ResponseResult list(
            @Parameter(description = "状态") String status,
            @Parameter(description = "菜单名") String menuName) {
        return menuService.ArticleList(status, menuName);
    }

    @PostMapping
    @SystemLog(businessName = "新增菜单")
    @Tag(name = "菜单", description = "新增菜单")
    public ResponseResult add(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "菜单信息") @RequestBody AddMenuDto addMenuDto) {
        return menuService.add(addMenuDto);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "根据id查询菜单")
    @Tag(name = "菜单", description = "根据id查询菜单")
    public ResponseResult getMenuById(
            @Parameter(description = "菜单id") @PathVariable Long id) {
        return menuService.getMenuById(id);
    }

    @PutMapping
    @SystemLog(businessName = "修改菜单")
    @Tag(name = "菜单", description = "修改菜单")
    public ResponseResult update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "菜单信息") @RequestBody AddMenuDto addMenuDto) {
        return menuService.updateMenu(addMenuDto);
    }

    @DeleteMapping("/{menuId}")
    @SystemLog(businessName = "删除菜单")
    @Tag(name = "菜单", description = "删除菜单")
    public ResponseResult delete(
            @Parameter(description = "菜单id") @PathVariable Long menuId) {
        return menuService.deleteMenu(menuId);
    }

    @GetMapping("/treeselect")
    @SystemLog(businessName = "菜单树")
    @Tag(name = "菜单", description = "菜单树")
    public ResponseResult treeSelect() {
        return menuService.treeSelect();
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    @SystemLog(businessName = "角色菜单树")
    @Tag(name = "菜单", description = "角色菜单树")
    public ResponseResult roleMenuTreeselect(
            @Parameter(description = "角色id") @PathVariable Long id) {
        return menuService.roleMenuTreeSelect(id);
    }

}
