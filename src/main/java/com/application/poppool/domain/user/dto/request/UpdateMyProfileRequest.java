package com.application.poppool.domain.user.dto.request;

import lombok.Getter;

@Getter
public class UpdateMyProfileRequest {

    private String profileImage;
    private String nickName;
    private String email;
    private String instagramId;
    private String intro;

}