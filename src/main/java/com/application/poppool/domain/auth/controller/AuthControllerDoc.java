package com.application.poppool.domain.auth.controller;


import com.application.poppool.domain.auth.dto.request.KakaoLoginRequest;
import com.application.poppool.domain.auth.dto.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name =  "인증/인가 - 로그인 API")
public interface AuthControllerDoc {
    @Operation(summary = "카카오 로그인", description = "카카오 로그인을 진행합니다.")
    ResponseEntity<LoginResponse> kakaoLogin(@RequestBody @Valid KakaoLoginRequest kakaoLoginRequest);

}
