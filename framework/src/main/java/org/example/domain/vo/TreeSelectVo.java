package org.example.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "树形选择vo")
public class TreeSelectVo {

    /**
     * 菜单树
     */
    @Schema(description = "菜单树")
    private List<AdminAddRoleMenuVo> menus;

    /**
     * 角色所关联的菜单权限id列表
     */
    @Schema(description = "角色所关联的菜单权限id列表")
    private List<Long> checkedKeys;
}
