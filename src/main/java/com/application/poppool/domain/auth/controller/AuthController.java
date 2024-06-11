package com.application.poppool.domain.auth.controller;


import com.application.poppool.domain.auth.dto.request.KakaoLoginRequest;
import com.application.poppool.domain.auth.dto.response.LoginResponse;
import com.application.poppool.domain.auth.service.kakao.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthControllerDoc {

    private final AuthService authService;

    @PostMapping("/oauth/kakao/login")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestBody @Valid KakaoLoginRequest kakaoLoginRequest) {
        return ResponseEntity.ok(authService.kakaoLogin(kakaoLoginRequest));
    }




}
