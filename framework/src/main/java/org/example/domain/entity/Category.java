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
 * 分类表(Category)表实体类
 *
 * @author makejava
 * @since 2025-03-06 11:15:01
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("category")
@Schema(description = "分类表")
public class Category {

    @TableId
    @Schema(description = "分类id")
    private Long id;

    /**
     * 分类名
     */
    @Schema(description = "分类名")
    private String name;

    /**
     * 父分类id，如果没有父分类为-1
     */
    @Schema(description = "父分类id，如果没有父分类为-1")
    private Long pid;

    /**
     * 分类描述
     */
    @Schema(description = "分类描述")
    private String description;

    /**
     * 状态（0正常，1禁用）
     */
    @Schema(description = "状态（0正常，1禁用）")
    private String status;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建者")
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "更新者")
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

