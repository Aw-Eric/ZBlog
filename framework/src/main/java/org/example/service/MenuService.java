package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.domain.ResponseResult;
import org.example.domain.dto.AddMenuDto;
import org.example.domain.entity.Menu;
import org.example.domain.vo.MenuVo;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2025-03-18 15:48:20
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<MenuVo> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult ArticleList(String status, String menuName);

    ResponseResult add(AddMenuDto addMenuDto);

    ResponseResult getMenuById(Long id);

    ResponseResult updateMenu(AddMenuDto addMenuDto);

    ResponseResult deleteMenu(Long menuId);

    ResponseResult treeSelect();
}
