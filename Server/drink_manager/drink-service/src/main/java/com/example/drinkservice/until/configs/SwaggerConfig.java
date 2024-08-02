package com.example.drinkservice.until.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    /**
     * Cấu hình để tạo ra một đối tượng OpenAPI để cấu hình Swagger.
     *
     * @return Đối tượng OpenAPI đã được cấu hình
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MANEGE_DRINK")
                        .version("1.0")
                        .description("DESCRIPTION"));
    }
}
