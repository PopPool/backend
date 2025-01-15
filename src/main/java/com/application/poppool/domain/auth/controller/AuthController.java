package com.application.poppool.domain.auth.controller;


import com.application.poppool.domain.auth.dto.request.AppleLoginRequest;
import com.application.poppool.domain.auth.dto.request.KakaoLoginRequest;
import com.application.poppool.domain.auth.dto.response.LoginResponse;
import com.application.poppool.domain.auth.dto.response.TokenResponse;
import com.application.poppool.domain.auth.service.apple.AppleAuthService;
import com.application.poppool.domain.auth.service.kakao.KakaoAuthService;
import com.application.poppool.global.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthControllerDoc {

    private final KakaoAuthService kakaoAuthService;
    private final AppleAuthService appleAuthService;
    private final JwtService jwtService;

    @Override
    @PostMapping("/kakao")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestBody @Valid KakaoLoginRequest kakaoLoginRequest, HttpServletResponse response) {
        log.info("카카오 로그인");
        return ResponseEntity.ok(kakaoAuthService.kakaoLogin(kakaoLoginRequest, response));
    }

    @Override
    @PostMapping("/apple")
    public ResponseEntity<LoginResponse> appleLogin(@RequestBody @Valid AppleLoginRequest appleLoginRequest, HttpServletResponse response) {
        log.info("애플 로그인");
        return ResponseEntity.ok(appleAuthService.appleLogin(appleLoginRequest, response));
    }

    @Override
    @PostMapping("/token/reissue")
    public ResponseEntity<TokenResponse> reIssueToken(HttpServletRequest request, HttpServletResponse response) {
        log.info("토큰 재발급");
        String refreshToken = jwtService.getToken(request);
        return ResponseEntity.ok(jwtService.reIssueToken(refreshToken, response));
    }

}
