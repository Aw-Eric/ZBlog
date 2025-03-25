package org.example.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户列表vo")
public class AdminUserVo {

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
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 创建人的用户id
     */
    @Schema(description = "创建人的用户id")
    private Long createBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private Long updateBy;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date updateTime;

}
