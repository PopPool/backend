package com.application.poppool.domain.auth.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum SocialType {
    KAKAO("@kakao"), APPLE("@apple");

    private final String socialSuffix;

    SocialType(String socialSuffix) {
        this.socialSuffix = socialSuffix;
    }
}
