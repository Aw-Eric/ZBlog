package org.example.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "AddUserDto", description = "添加用户dto")
public class AddUserDto {

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String userName;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickName;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 账号状态（0正常 1停用）
     */
    @Schema(description = "账号状态（0正常 1停用）")
    private String status;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phonenumber;

    /**
     * 用户性别（0男，1女，2未知）
     */
    @Schema(description = "用户性别（0男，1女，2未知）")
    private String sex;

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    private List<Long> roleIds;
}
