package org.example.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.example.domain.entity.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = "用户信息")
public class AdminUserInfoVo {

    /**
     * 用户权限
     */
    @Schema(description = "用户权限")
    private List<String> permissions;

    /**
     * 用户角色
     */
    @Schema(description = "用户角色")
    private List<String> roles;

    /**
     * 用户信息
     */
    @Schema(description = "用户信息")
    private UserInfoVo user;
}
