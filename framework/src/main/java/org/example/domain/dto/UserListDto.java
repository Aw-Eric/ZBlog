package org.example.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户列表dto")
public class UserListDto {

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String userName;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phonenumber;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String status;

}
