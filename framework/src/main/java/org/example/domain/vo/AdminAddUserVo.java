package org.example.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "添加用户表")
public class AdminAddUserVo {

    /**
     * 主键
     */
    @Schema(description = "用户id")
    private Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String userName;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phonenumber;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickName;

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
     * 用户性别（0男，1女，2未知）
     */
    @Schema(description = "用户性别（0男，1女，2未知）")
    private String sex;

}
