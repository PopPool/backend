package com.application.poppool.domain.auth.controller;


import com.application.poppool.domain.auth.dto.request.SignUpRequest;
import com.application.poppool.domain.auth.dto.request.AppleLoginRequest;
import com.application.poppool.domain.auth.dto.request.KakaoLoginRequest;
import com.application.poppool.domain.auth.dto.response.LoginResponse;
import com.application.poppool.domain.auth.service.apple.AppleAuthService;
import com.application.poppool.domain.auth.service.kakao.KakaoAuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthControllerDoc {

    private final KakaoAuthService kakaoAuthService;
    private final AppleAuthService appleAuthService;


    @PostMapping("/kakao")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestBody @Valid KakaoLoginRequest kakaoLoginRequest, HttpServletResponse response) {
        log.info("카카오 로그인");
        return ResponseEntity.ok(kakaoAuthService.kakaoLogin(kakaoLoginRequest,response));
    }

    @PostMapping("/apple")
    public ResponseEntity<LoginResponse> appleLogin(@RequestBody @Valid AppleLoginRequest appleLoginRequest,HttpServletResponse response) {
        log.info("애플 로그인");
        return ResponseEntity.ok(appleAuthService.appleLogin(appleLoginRequest,response));
    }


}
