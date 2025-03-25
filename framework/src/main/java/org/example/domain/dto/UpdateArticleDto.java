package org.example.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 文章表(Article)表实体类
 * 更新文章DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "更新文章dto")
public class UpdateArticleDto {

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long id;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 文章内容
     */
    @Schema(description = "文章内容")
    private String content;

    /**
     * 文章摘要
     */
    @Schema(description = "文章摘要")
    private String summary;

    /**
     * 所属分类ID
     */
    @Schema(description = "所属分类ID")
    private Long categoryId;

    /**
     * 缩略图
     */
    @Schema(description = "缩略图")
    private String thumbnail;

    /**
     * 是否置顶（0 否，1 是）
     */
    @Schema(description = "是否置顶（0 否，1 是）")
    private String isTop;

    /**
     * 状态（0 已发布，1 草稿）
     */
    @Schema(description = "状态（0 已发布，1 草稿）")
    private String status;

    /**
     * 访问量
     */
    @Schema(description = "访问量")
    private Long viewCount;

    /**
     * 是否允许评论（1 是，0 否）
     */
    @Schema(description = "是否允许评论（1 是，0 否）")
    private String isComment;

    /**
     * 创建者ID
     */
    @Schema(description = "创建者ID")
    private Long createBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新者ID
     */
    @Schema(description = "更新者ID")
    private Long updateBy;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date updateTime;

    /**
     * 删除标志（0 代表未删除，1 代表已删除）
     */
    @Schema(description = "删除标志（0 代表未删除，1 代表已删除）")
    private Integer delFlag;

    /**
     * 标签列表
     */
    @Schema(description = "标签列表")
    private List<Long> tags;
}
