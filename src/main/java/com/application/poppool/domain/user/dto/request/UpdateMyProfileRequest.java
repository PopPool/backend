package com.application.poppool.domain.user.dto.request;

import lombok.Getter;

@Getter
public class UpdateMyProfileRequest {

    private String profileImage;
    private String nickname;
    private String email;
    private String instagramId;
    private String intro;

}
