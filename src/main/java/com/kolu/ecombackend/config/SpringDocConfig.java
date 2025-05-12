package com.kolu.ecombackend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringDocConfig {

    @Value("${NG_ROK_URL}")
    private String ngrokUrl;

    @Bean
    public OpenAPI springDocOpenAPI() {
        var servers = List.of(
                new Server().url("http://localhost:8080").description("Local server"),
                new Server().url(ngrokUrl).description("ngrok server")
        );
        return new OpenAPI()
                .info(new Info()
                        .title("E-commerce Backend API")
                        .version("0.2.0")
                        .description("API documentation for the E-commerce backend application.")
                )
                .servers(servers)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes(
                        "bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                                .description("JWT authentication")
                ));
    }
}
