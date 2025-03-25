package org.example.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "标签vo")
public class TagVo {

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

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}
