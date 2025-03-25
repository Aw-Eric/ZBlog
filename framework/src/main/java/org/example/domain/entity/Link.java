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
 * 友链(Link)表实体类
 *
 * @author makejava
 * @since 2025-03-07 16:22:46
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("link")
@Schema(description = "友链")
public class Link {
    @TableId
    @Schema(description = "友链ID")
    private Long id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * Logo 地址
     */
    @Schema(description = "Logo 地址")
    private String logo;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;

    /**
     * 网站地址
     */
    @Schema(description = "网站地址")
    private String address;

    /**
     * 审核状态 (0: 审核通过, 1: 审核未通过, 2: 未审核)
     */
    @Schema(description = "审核状态 (0: 审核通过, 1: 审核未通过, 2: 未审核)")
    private String status;

    /**
     * 创建者 ID
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建者 ID")
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新者 ID
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "更新者 ID")
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "更新时间")
    private Date updateTime;

    /**
     * 删除标志 (0: 未删除, 1: 已删除)
     */
    @Schema(description = "删除标志 (0: 未删除, 1: 已删除)")
    private Integer delFlag;

}

