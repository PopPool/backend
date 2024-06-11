package com.application.poppool.domain.auth.service.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(
        name = "kakao-auth-api",
        url = "${oauth.kakao.auth-url}"
)
public interface KakaoAuthFeignClient {

    /**
     * 카카오 토큰 정보 얻기
     */
    @GetMapping(value = "/v1/user/access_token_info")
    KakaoTokenInfoResponse getKakaoTokenInfo(@RequestHeader("Authorization") String accessToken);


}
