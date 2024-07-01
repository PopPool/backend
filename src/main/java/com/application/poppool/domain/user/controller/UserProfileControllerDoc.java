package com.application.poppool.domain.user.controller;


import com.application.poppool.domain.user.dto.response.GetProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "회원 프로필 API")
public interface UserProfileControllerDoc {

    @Operation(summary = "회원 관심사 목록 조회", description = "회원 관심사 목록을 조회합니다.")
    ResponseEntity<GetProfileResponse> getMyProfile(@PathVariable("user-id") String userId);
}
