package com.application.poppool.domain.user.controller;

import com.application.poppool.domain.user.dto.response.GetMyPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "마이페이지의 회원 API")
public interface UserControllerDoc {

    @Operation(summary = "마이페이지 조회", description = "마이페이지를 조회합니다.")
    ResponseEntity<GetMyPageResponse> getMyPage(@PathVariable String userId);

    @Operation(summary = "회원 로그아웃", description = "회원 로그아웃을 진행합니다.")
    void logout(HttpServletRequest request);
}
