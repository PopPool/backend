package com.application.poppool.domain.auth.service.kakao;

import com.application.poppool.domain.auth.dto.info.KakaoToken;
import com.application.poppool.domain.auth.dto.request.KakaoLoginRequest;
import com.application.poppool.domain.auth.dto.response.LoginResponse;
import com.application.poppool.domain.auth.enums.SocialType;
import com.application.poppool.domain.auth.service.apple.AppleAuthFeignClient;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.global.exception.BadRequestException;
import com.application.poppool.global.exception.ErrorCode;
import com.application.poppool.global.jwt.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final KakaoAuthFeignClient kakaoAuthFeignClient;
    private final AppleAuthFeignClient appleAuthFeignClient;

    /**
     * 카카오 로그인
     *
     * @param kakaoLoginRequest
     * @param response
     * @return
     */
    @Transactional(readOnly = true)
    public LoginResponse kakaoLogin(KakaoLoginRequest kakaoLoginRequest, HttpServletResponse response) {

        // 유저 ID
        String userId = kakaoLoginRequest.getKakaoUserId() + SocialType.KAKAO.getSocialSuffix();

        // 서버에서 한 번 더 넘어온 유저 id와, 토큰이 유효한지 검증
        if (!validateKakaoAccessToken(userId, kakaoLoginRequest.getKakaoAccessToken())) {
            throw new BadRequestException(ErrorCode.AUTHENTICATION_FAIL_EXCEPTION);
        }

        boolean isRegisteredUser = true;
        boolean isTemporaryToken = false;

        /**
         * 기존 회원이 아닌 경우, 회원가입 대상 -> isUser 구분 값 false 설정 및 임시토큰 설정
         */
        if (!userRepository.findByUserId(userId).isPresent()) {
            isRegisteredUser = false;
            isTemporaryToken = true;
        }

        // 로그인 응답
        LoginResponse loginResponse = jwtService.createJwtToken(userId, isTemporaryToken);
        // 헤더에 토큰 싣기
        jwtService.setHeaderAccessToken(response, loginResponse.getAccessToken());
        jwtService.setHeaderRefreshToken(response, loginResponse.getRefreshToken());

        return LoginResponse.builder()
                .userId(userId)
                .grantType(loginResponse.getGrantType())
                .accessToken(loginResponse.getAccessToken())
                .accessTokenExpiresIn(loginResponse.getAccessTokenExpiresIn())
                .refreshToken(loginResponse.getRefreshToken())
                .refreshTokenExpiresIn(loginResponse.getRefreshTokenExpiresIn())
                .socialType(SocialType.KAKAO) // 자체 로그인이므로 소셜 타입은 null
                .isRegisteredUser(isRegisteredUser)
                .build();

    }

    /**
     * 카카오 accessToken 검증
     *
     * @param userId
     * @param kakaoAccessToken
     * @return
     */
    public boolean validateKakaoAccessToken(String userId, String kakaoAccessToken) {

        KakaoToken kakaoToken = kakaoAuthFeignClient.getKakaoTokenInfo("Bearer " + kakaoAccessToken);
        if (kakaoToken != null && kakaoToken.getId() != null
                && kakaoToken.getExpires_in() > 0
                && (String.valueOf(kakaoToken.getId()) + SocialType.KAKAO.getSocialSuffix()).equals(userId)) {
            return true;
        }
        return false;
    }
}
