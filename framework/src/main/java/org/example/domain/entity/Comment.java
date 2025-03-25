package org.example.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/***
 * 评论表 (Comment) 表实体类
 *
 * @author makejava
 * @since 2025-03-11 13:22:00
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("comment")
@Schema(description = "评论表")
public class Comment {

    /***
     * 评论ID (主键)
     */
    @TableId
    @Schema(description = "评论ID")
    private Long id;

    /***
     * 评论类型（0代表文章评论，1代表友链评论）
     */
    @Schema(description = "评论类型（0代表文章评论，1代表友链评论）")
    private String type;

    /***
     * 文章ID
     */
    @Schema(description = "文章ID")
    private Long articleId;

    /***
     * 根评论ID
     */
    @Schema(description = "根评论ID")
    private Long rootId;

    /***
     * 评论内容
     */
    @Schema(description = "评论内容")
    private String content;

    /***
     * 所回复的目标评论的用户ID
     */
    @Schema(description = "所回复的目标评论的用户ID")
    private Long toCommentUserId;

    /***
     * 回复目标评论ID
     */
    @Schema(description = "回复目标评论ID")
    private Long toCommentId;

    /***
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建人ID")
    private Long createBy;

    /***
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private Date createTime;

    /***
     * 更新人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新人ID")
    private Long updateBy;

    /***
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private Date updateTime;

    /***
     * 删除标志（0代表未删除，1代表已删除）
     */
    @Schema(description = "删除标志（0代表未删除，1代表已删除）")
    private Integer delFlag;
}
