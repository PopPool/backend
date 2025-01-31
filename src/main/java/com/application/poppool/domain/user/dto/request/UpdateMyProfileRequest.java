package com.application.poppool.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateMyProfileRequest {

    @Schema(description = "프로필 이미지 URL")
    private String profileImageUrl;
    @Schema(description = "회원 닉네임")
    private String nickname;
    @Schema(description = "회원 인스타그램 ID")
    private String instagramId;
    @Schema(description = "회원 자기소개")
    private String intro;

}
