package org.example.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "管理员文章列表vo")
public class AdminArticleListVo {

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
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

}
