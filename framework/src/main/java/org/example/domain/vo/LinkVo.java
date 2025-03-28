package org.example.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "友情链接vo")
public class LinkVo {

    /**
     * ID
     */
    @Schema(description = "ID")
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

    private String status;

}
