package com.application.poppool.domain.user.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
public class GetMyPageResponse {

    private String nickname;
    private String profileImageUrl;
    private String intro;
    private String instagramId;
    private boolean loginYn;
    private boolean adminYn;
    private List<MyCommentedPopUpInfo> myCommentedPopUpList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyCommentedPopUpInfo {
        private Long popUpStoreId;
        private String popUpStoreName;
        private String mainImageUrl;
    }

}
