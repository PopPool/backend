package com.application.poppool.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class KakaoLoginRequest {

    @NotBlank
    private String kakaoUserId;
    private String kakaoAccessToken;

}
