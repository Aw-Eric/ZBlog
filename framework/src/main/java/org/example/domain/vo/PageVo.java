package org.example.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分页vo")
public class PageVo {

    /**
     * 当前页
     */
    @Schema(description = "当前页")
    private List rows;

    /**
     * 每页显示条数
     */
    @Schema(description = "每页显示条数")
    private Long total;
}
