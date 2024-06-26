package com.application.poppool.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    private static final String BASE_PACKAGE = "com.application.poppool.domain";

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Collections.singletonList(securityRequirement));
    }

    @Bean
    public GroupedOpenApi authApi() {
        final String name = "auth";
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch("/auth" + "/**")
                .packagesToScan(BASE_PACKAGE + ".auth")
                .build();
    }

    @Bean
    public GroupedOpenApi signUpApi() {
        final String name = "signUp";
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch("/signup" + "/**")
                .packagesToScan(BASE_PACKAGE + ".sign_up")
                .build();
    }

}
