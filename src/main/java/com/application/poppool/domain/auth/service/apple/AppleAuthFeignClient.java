package com.application.poppool.domain.auth.service.apple;

import com.application.poppool.domain.auth.dto.info.ApplePublicKeys;
import com.application.poppool.domain.auth.dto.info.AppleTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "apple-auth-api", url = "${oauth.apple.auth-url}")
public interface AppleAuthFeignClient {

    @GetMapping(value = "/auth/keys")
    ApplePublicKeys getAppleAuthPublicKey();

    @PostMapping(value = "/auth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    AppleTokenResponse getAppleToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("code") String authorizationCode,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret
        );

    @PostMapping(value = "/auth/revoke", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String revokeToken(
            @RequestParam("token") String refreshToken,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret
    );
}
