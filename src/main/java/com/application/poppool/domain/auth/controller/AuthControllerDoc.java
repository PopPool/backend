package com.application.poppool.domain.auth.controller;


import com.application.poppool.domain.auth.dto.request.AppleLoginRequest;
import com.application.poppool.domain.auth.dto.request.KakaoLoginRequest;
import com.application.poppool.domain.auth.dto.response.LoginResponse;
import com.application.poppool.domain.auth.dto.response.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "인증/인가 - 회원가입/로그인 API")
public interface AuthControllerDoc {
    @Operation(summary = "카카오 로그인", description = "카카오 로그인을 진행합니다.")
    ResponseEntity<LoginResponse> kakaoLogin(@RequestBody @Valid KakaoLoginRequest kakaoLoginRequest, HttpServletResponse response);

    @Operation(summary = "애플 로그인", description = "애플 로그인을 진행합니다.")
    ResponseEntity<LoginResponse> appleLogin(@RequestBody @Valid AppleLoginRequest appleLoginRequest, HttpServletResponse response);

    @Operation(summary = "토큰 재발급", description = "토큰을 재발급합니다.")
    ResponseEntity<TokenResponse> reIssueToken(HttpServletRequest request, HttpServletResponse response);

}
