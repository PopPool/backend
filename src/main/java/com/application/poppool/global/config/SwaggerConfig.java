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
        AUTH("auth", "/auth"),
        SIGN_UP("sign_up", "/signup"),
        USER("user", "/users"),
        NOTICE("notice", "/notice"),
        CATEGORY("category","/categories"),
        HOME("home", "/home"),
        POPUP_STORE("popup", "/popup"),
        COMMENT("comment", "/comments"),
        LIKE("like", "/likes"),
        FILE("file", "/files"),
        SEARCH("search", "/search"),
        LOCATION("location", "/locations"),
        ADMIN("admin", "/admin");

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
                .packagesToScan(BASE_PACKAGE + "." + ApiUrl.AUTH.getGroup())
                .build();
    }

    @Bean
    public GroupedOpenApi signUpApi() {
        final String name = ApiUrl.SIGN_UP.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.SIGN_UP.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + ApiUrl.SIGN_UP.getGroup())
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        final String name = ApiUrl.USER.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.USER.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + ApiUrl.USER.getGroup())
                .build();
    }
    @Bean
    public GroupedOpenApi categoryApi() {
        final String name = ApiUrl.CATEGORY.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.CATEGORY.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + ApiUrl.CATEGORY.getGroup())
                .build();
    }


    @Bean
    public GroupedOpenApi noticeApi() {
        final String name = ApiUrl.NOTICE.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.NOTICE.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + ApiUrl.NOTICE.getGroup())
                .build();
    }

    @Bean
    public GroupedOpenApi homeApi() {
        final String name = ApiUrl.HOME.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.HOME.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + ApiUrl.HOME.getGroup())
                .build();
    }

    @Bean
    public GroupedOpenApi popUpStoreApi() {
        final String name = ApiUrl.POPUP_STORE.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.POPUP_STORE.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + ApiUrl.POPUP_STORE.getGroup())
                .build();
    }

    @Bean
    public GroupedOpenApi commentApi() {
        final String name = ApiUrl.COMMENT.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.COMMENT.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + ApiUrl.COMMENT.getGroup())
                .build();
    }

    @Bean
    public GroupedOpenApi likeApi() {
        final String name = ApiUrl.LIKE.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.LIKE.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + ApiUrl.LIKE.getGroup())
                .build();
    }

    @Bean
    public GroupedOpenApi fileApi() {
        final String name = ApiUrl.FILE.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.FILE.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + ApiUrl.FILE.getGroup())
                .build();
    }

    @Bean
    public GroupedOpenApi searchApi() {
        final String name = ApiUrl.SEARCH.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.SEARCH.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + ApiUrl.SEARCH.getGroup())
                .build();
    }

    @Bean
    public GroupedOpenApi locationApi() {
        final String name = ApiUrl.LOCATION.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.LOCATION.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + ApiUrl.LOCATION.getGroup())
                .build();
    }


    @Bean
    public GroupedOpenApi adminApi() {
        final String name = ApiUrl.ADMIN.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.ADMIN.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + ApiUrl.ADMIN.getGroup())
                .build();
    }

    @Bean
    public GroupedOpenApi samApi() {
        final String name = "sample";
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch("/sample" + "/**")
                .build();
    }



}
