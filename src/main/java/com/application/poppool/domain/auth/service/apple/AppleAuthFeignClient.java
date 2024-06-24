package com.application.poppool.domain.auth.service.apple;

import com.application.poppool.domain.auth.dto.info.ApplePublicKeys;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "apple-auth-api", url = "${oauth.apple.auth-url}")
public interface AppleAuthFeignClient {

    @GetMapping(value = "/auth/keys")
    ApplePublicKeys getAppleAuthPublicKey();
}
