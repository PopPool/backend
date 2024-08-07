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
        AUTH("auth","/auth"),
        SIGN_UP("signup","/signup"),
        USER("users","/users"),
        NOTICE("notice","/notice"),
        HOME("home","/home"),
        POPUP_STORE("popup","/popup"),
        COMMENT("comments","/comments"),
        LIKE("likes","/likes");

        private final String group;
        private final String urlPrefix;

        ApiUrl(String group, String urlPrefix) {
            this.group = group;
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
        final String name = ApiUrl.AUTH.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.AUTH.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + ".auth")
                .build();
    }

    @Bean
    public GroupedOpenApi signUpApi() {
        final String name = ApiUrl.SIGN_UP.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.SIGN_UP.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + ".sign_up")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        final String name = ApiUrl.USER.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.USER.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + ".user")
                .build();
    }

    @Bean
    public GroupedOpenApi noticeApi() {
        final String name = ApiUrl.NOTICE.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.NOTICE.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + ".notice")
                .build();
    }

    @Bean
    public GroupedOpenApi homeApi() {
        final String name = ApiUrl.HOME.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.HOME.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + ".home")
                .build();
    }

    @Bean
    public GroupedOpenApi popUpStoreApi() {
        final String name = ApiUrl.POPUP_STORE.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.POPUP_STORE.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + ".popup")
                .build();
    }

    @Bean
    public GroupedOpenApi commentApi() {
        final String name = ApiUrl.COMMENT.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.COMMENT.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + ".comment")
                .build();
    }

    @Bean
    public GroupedOpenApi likeApi() {
        final String name = ApiUrl.LIKE.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.LIKE.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + ".like")
                .build();
    }


}
