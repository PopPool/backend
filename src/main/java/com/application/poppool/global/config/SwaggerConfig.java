package com.application.poppool.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Getter;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    private static final String BASE_PACKAGE = "com.application.poppool.domain";

    @Getter
    public enum ApiUrl {
        AUTH("/auth"),
        SIGN_UP("/signup"),
        USER("/users"),
        NOTICE("/notice"),
        HOME("/home");

        private final String urlPrefix;

        ApiUrl(String urlPrefix) {
            this.urlPrefix = urlPrefix;
        }
    }

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
        final String name = ApiUrl.AUTH.getUrlPrefix();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.AUTH.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + ".auth")
                .build();
    }

    @Bean
    public GroupedOpenApi signUpApi() {
        final String name = ApiUrl.SIGN_UP.getUrlPrefix();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.SIGN_UP.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + ".sign_up")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        final String name = ApiUrl.USER.getUrlPrefix();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.USER.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + ".user")
                .build();
    }

    @Bean
    public GroupedOpenApi noticeApi() {
        final String name = ApiUrl.NOTICE.getUrlPrefix();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.NOTICE.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + ".notice")
                .build();
    }

    @Bean
    public GroupedOpenApi homeApi() {
        final String name = ApiUrl.HOME.getUrlPrefix();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.HOME.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + ".notice")
                .build();
    }


}
