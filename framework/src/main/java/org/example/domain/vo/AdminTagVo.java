package org.example.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "标签vo")
public class AdminTagVo {

    /**
     * 标签id
     */
    @Schema(description = "标签id")
    private Long id;

    /**
     * 标签名称
     */
    @Schema(description = "标签名称")
    private String name;

}
