package com.application.poppool.domain.sign_up.controller;

import com.application.poppool.domain.sign_up.dto.request.SignUpRequest;
import com.application.poppool.domain.sign_up.dto.response.GetCategoryListResponse;
import com.application.poppool.domain.sign_up.dto.response.GetGenderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Tag(name = "회원가입 관련 API")
public interface SignUpControllerDoc {

    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    void signUp(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid SignUpRequest signUpRequest, HttpServletResponse response) throws IOException;

    @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복 확인을 진행합니다.")
    ResponseEntity<Boolean> checkNicknameDuplicate(@RequestParam String nickName);

    @Operation(summary = "성별 후보군 목록 조회", description = "성별 후보군 목록을 조회합니다.")
    ResponseEntity<List<GetGenderResponse>> getGenderList();

    @Operation(summary = "카테고리 목록 조회", description = "카테고리 목록을 조회합니다.")
    ResponseEntity<GetCategoryListResponse> getCategoryList();

}
