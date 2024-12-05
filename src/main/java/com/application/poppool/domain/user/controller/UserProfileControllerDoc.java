package com.application.poppool.domain.user.controller;


import com.application.poppool.domain.user.dto.request.UpdateMyInterestCategoryRequest;
import com.application.poppool.domain.user.dto.request.UpdateMyProfileRequest;
import com.application.poppool.domain.user.dto.request.UpdateMyTailoredInfoRequest;
import com.application.poppool.domain.user.dto.response.GetProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "회원 프로필 API")
public interface UserProfileControllerDoc {

    @Operation(summary = "회원 프로필 조회", description = "회원 프로필을 조회합니다.")
    ResponseEntity<GetProfileResponse> getMyProfile(@AuthenticationPrincipal UserDetails userDetails);


    @Operation(summary = "회원 프로필 수정", description = "회원 프로필을 수정합니다.")
    void updateMyProfile(@AuthenticationPrincipal UserDetails userDetails,
                         @RequestBody UpdateMyProfileRequest request);

    @Operation(summary = "회원 관심 카테고리 수정", description = "회원 관심 카테고리를 수정합니다.")
    void updateMyInterestCategory(@AuthenticationPrincipal UserDetails userDetails,
                                  @RequestBody @Valid UpdateMyInterestCategoryRequest request);

    @Operation(summary = "회원 맞춤 정보 수정", description = "회원 맞춤 정보를 수정합니다.")
    public void updateMyTailoredInfo(@AuthenticationPrincipal UserDetails userDetails,
                                     @RequestBody @Valid UpdateMyTailoredInfoRequest updateMyTailoredInfoRequest);
}
