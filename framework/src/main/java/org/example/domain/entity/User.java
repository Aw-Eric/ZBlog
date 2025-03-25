package org.example.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户表(User)表实体类
 *
 * @author makejava
 * @since 2025-03-08 14:20:07
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
@Schema(description = "用户表")
public class User {
    /**
     * 主键
     */
    @TableId
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
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 用户类型：0代表普通用户，1代表管理员
     */
    @Schema(description = "用户类型：0代表普通用户，1代表管理员")
    private String type;

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
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建人的用户id")
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "更新人")
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "更新时间")
    private Date updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @Schema(description = "删除标志（0代表未删除，1代表已删除）")
    private Integer delFlag;
}