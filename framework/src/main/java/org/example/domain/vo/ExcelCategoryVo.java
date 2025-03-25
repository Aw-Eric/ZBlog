package org.example.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Excel导出vo")
public class ExcelCategoryVo {

    /**
     * 分类名称
     */
    @ExcelProperty("分类名称")
    @Schema(description = "分类名称")
    private String name;

    /**
     * 描述
     */
    @ExcelProperty("描述")
    @Schema(description = "描述")
    private String description;

    /**
     * 状态
     */
    @ExcelProperty("状态(0-正常，1-禁用)")
    @Schema(description = "状态")
    private String status;
}