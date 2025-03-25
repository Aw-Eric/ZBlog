package org.example.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文章列表vo")
public class ArticleListVo {

    /**
     * 文章ID
     */
    @Schema(description = "文章ID")
    private Long id;

    /**
     * 文章标题
     */
    @Schema(description = "文章标题")
    private String title;

    /**
     * 文章摘要
     */
    @Schema(description = "文章摘要")
    private String summary;

    /**
     * 所属分类ID
     */
    @Schema(description = "所属分类ID")
    private String categoryName;

    /**
     * 缩略图
     */
    @Schema(description = "缩略图")
    private String thumbnail;

    /**
     * 访问量
     */
    @Schema(description = "访问量")
    private Long viewCount;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;
}
