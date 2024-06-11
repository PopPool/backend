package com.application.poppool.domain.auth.service.kakao;

import com.application.poppool.domain.auth.SocialType;
import com.application.poppool.domain.auth.dto.request.KakaoLoginRequest;
import com.application.poppool.domain.auth.dto.response.LoginResponse;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final KakaoAuthFeignClient kakaoAuthFeignClient;

    public LoginResponse kakaoLogin(KakaoLoginRequest kakaoLoginRequest) {

        String kakaoUserId = kakaoLoginRequest.getKakaoUserId() + "@kakao";
        Optional<UserEntity> user = userRepository.findByUserId(kakaoUserId);

        /**
         * 기존 회원이 아닌 경우, 회원가입 및 로그인 동시 진행
         */
        if (!user.isPresent()) {
            
            // ios 클라이언트로 전달 받은 카카오 엑세스토큰이 유효한지 검증
            validateKakaoAccessToken(kakaoUserId,kakaoLoginRequest.getKakaoAccessToken());
            
            UserEntity userEntity = UserEntity.builder()
                    .userId(kakaoUserId)
                    .socialType(SocialType.KAKAO)
                    .build();

            // 회원가입
            userRepository.save(userEntity);

            // 로그인 응답
            LoginResponse loginResponse = jwtService.createJwtToken(userEntity.getUserId());
            return LoginResponse.builder()
                    .userId(userEntity.getUserId())
                    .grantType(loginResponse.getGrantType())
                    .accessToken(loginResponse.getAccessToken())
                    .accessTokenExpiresIn(loginResponse.getAccessTokenExpiresIn())
                    .refreshToken(loginResponse.getRefreshToken())
                    .refreshTokenExpiresIn(loginResponse.getRefreshTokenExpiresIn())
                    .socialType(SocialType.KAKAO) // 자체 로그인이므로 소셜 타입은 null
                    .build();
        }

        /**
         * 회원인 경우, 로그인만 진행
         */

        // 클라이언트로부터 넘어온 카카오 userId 값과 DB 유저 테이블에 있는 userId 비교
        if (!kakaoUserId.equals(user.get().getUserId())) {
            return null;
        }

        LoginResponse loginResponse = jwtService.createJwtToken(user.get().getUserId());
        return LoginResponse.builder()
                .userId(user.get().getUserId())
                .grantType(loginResponse.getGrantType())
                .accessToken(loginResponse.getAccessToken())
                .accessTokenExpiresIn(loginResponse.getAccessTokenExpiresIn())
                .refreshToken(loginResponse.getRefreshToken())
                .refreshTokenExpiresIn(loginResponse.getRefreshTokenExpiresIn())
                .socialType(SocialType.KAKAO) // 자체 로그인이므로 소셜 타입은 null
                .build();

    }

    public boolean validateKakaoAccessToken(String kakaoUserId, String kakaoAccessToken) {
        KakaoTokenInfoResponse response = kakaoAuthFeignClient.getKakaoTokenInfo("Bearer " + kakaoAccessToken);
        if (response != null && response.getId() != null
                && response.getExpiresIn() > 0 && response.getId().equals(kakaoUserId)) {
            return true;
        }
        return false;
    }

}
