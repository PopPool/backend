package com.application.poppool.domain.auth.service.apple;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "oauth.apple")  // "oauth.apple" 프리픽스를 사용
public class AppleProperties {

    private String clientId;
    private String teamId;
    private String keyId;
    private String authUrl;
    private String privateKeyPath;

}
