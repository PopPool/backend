package com.application.poppool.domain.user.controller;

import com.application.poppool.domain.auth.dto.request.SignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "사용자(유저) API")
public interface UserControllerDoc {

    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    void signUp(@RequestBody @Valid SignUpRequest signUpRequest);

}
