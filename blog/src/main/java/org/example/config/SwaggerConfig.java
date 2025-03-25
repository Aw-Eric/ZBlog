package org.example.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "文档标题",
                description = "文档描述",
                version = "1.1.0",
                contact = @Contact(name = "AEPC", url = "http://www.my.com", email = "1446984582@qq.com")
        )
)
public class SwaggerConfig {
}
