package org.example.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "添加用户返回结果")
public class AdminAddUserResult {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private List<Long> roleIds;

    /**
     * 角色
     */
    @Schema(description = "角色")
    private List<AdminAddUserRoleVo> roles;

    /**
     * 用户信息
     */
    @Schema(description = "用户信息")
    private AdminAddUserVo user;
}
