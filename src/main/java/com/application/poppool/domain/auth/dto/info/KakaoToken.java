package com.application.poppool.domain.auth.dto.info;

import lombok.Getter;

@Getter
public class KakaoToken {
    private Long id;
    private Integer expires_in;
}