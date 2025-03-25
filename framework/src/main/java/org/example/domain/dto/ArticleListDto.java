package org.example.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "模糊查询文章列表dto")
public class ArticleListDto {

    @Schema(description = "标题")
    private String title;

    @Schema(description = "文章摘要")
    private String summary;
}
