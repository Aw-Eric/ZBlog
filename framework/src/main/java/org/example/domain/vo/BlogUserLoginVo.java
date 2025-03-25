package org.example.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户登录vo")
public class BlogUserLoginVo {

    /**
     * token
     */
    @Schema(description = "token")
    private String token;

    /**
     * 用户信息
     */
    @Schema(description = "用户信息")
    private UserInfoVo userInfo;
}
