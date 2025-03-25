package org.example.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "评论vo")
public class CommentVo {

    /***
     * 评论ID
     */
    @Schema(description = "评论ID")
    private Long id;

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
     * 所回复的目标评论的昵称
     */
    @Schema(description = "所回复的目标评论的昵称")
    private String toCommentUserName;

    /***
     * 回复目标评论ID
     */
    @Schema(description = "回复目标评论ID")
    private Long toCommentId;

    /***
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    private Long createBy;

    /**
     * 创建人头像
     */
    @Schema(description = "创建人头像")
    private String avatar;

    /***
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /***
     * 评论人昵称
     */
    @Schema(description = "评论人昵称")
    private String username;

    /***
     * 子评论
     */
    @Schema(description = "子评论")
    private List<CommentVo> children;

}
