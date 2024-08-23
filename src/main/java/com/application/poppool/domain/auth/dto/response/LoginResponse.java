package com.application.poppool.domain.auth.dto.response;

import com.application.poppool.domain.auth.enums.SocialType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String userId;
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime accessTokenExpiresAt;
    private LocalDateTime refreshTokenExpiresAt;
    private SocialType socialType;
    private boolean registeredUserYn;

}
