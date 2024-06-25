package com.application.poppool.domain.auth.dto.request;

import lombok.Getter;

@Getter
public class KakaoLoginRequest {

    private Long kakaoUserId;
    private String kakaoAccessToken;

}
