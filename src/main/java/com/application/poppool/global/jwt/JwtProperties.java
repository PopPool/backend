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
    private TokenProperties token;

    @Getter
    @Setter
    public static class TokenProperties {
        private String header;
        private ExpirationProperties access;
        private ExpirationProperties refresh;
    }

    @Getter
    @Setter
    public static class ExpirationProperties {
        private long expiration;
    }
}
