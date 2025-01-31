package com.application.poppool.domain.user.dto.request;

import lombok.Getter;

@Getter
public class UpdateMyProfileRequest {

    private String profileImageUrl;
    private String nickname;
    private String instagramId;
    private String intro;

}
