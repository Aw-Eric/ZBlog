package org.example.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "标签列表dto")
public class TagListDto {

    /**
     * 标签名
     */
    @Schema(description = "标签名")
    private String name;

    /**
     * 标签备注
     */
    @Schema(description = "标签备注")
    private String remark;

}
