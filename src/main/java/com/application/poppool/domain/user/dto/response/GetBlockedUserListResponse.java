package com.application.poppool.domain.user.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
public class GetBlockedUserListResponse {

    private List<BlockedUserInfo> blockedUserInfoList;
    private int totalPages;
    private long totalElements;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BlockedUserInfo {
        private String userId;
        private String profileImageUrl;
        private String nickname;
        private String instagramId;
    }

}
