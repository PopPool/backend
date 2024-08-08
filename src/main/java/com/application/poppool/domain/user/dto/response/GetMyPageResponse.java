package com.application.poppool.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetMyPageResponse {

    private String nickname;
    private String profileImageUrl;
    private String intro;
    private String instagramId;
    private boolean isLogin;
    private List<MyCommentedPopUpInfo> myCommentedPopUpList;

    @Getter
    @Builder
    public static class MyCommentedPopUpInfo {
        private Long popUpStoreId;
        private String popUpStoreName;
        private String mainImageUrl;
    }

}
