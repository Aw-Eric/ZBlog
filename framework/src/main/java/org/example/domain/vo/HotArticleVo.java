package org.example.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "热门文章vo")
public class HotArticleVo {

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
     * 缩略图
     */
    @Schema(description = "缩略图")
    private Long viewCount;
}
