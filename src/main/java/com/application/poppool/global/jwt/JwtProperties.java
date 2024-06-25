package com.application.poppool.global.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secretKey;
    private AccessTokenProperties access;
    private RefreshTokenProperties refresh;

    @Getter
    @Setter
    public static class AccessTokenProperties {
        private long expiration;
        private String header;
    }

    @Getter
    @Setter
    public static class RefreshTokenProperties {
        private long expiration;
        private String header;
    }
}
