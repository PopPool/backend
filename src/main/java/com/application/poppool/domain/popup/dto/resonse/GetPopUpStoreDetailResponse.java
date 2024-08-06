package com.application.poppool.domain.popup.dto.resonse;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetPopUpStoreDetailResponse {

    private String name;
    private String desc;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String address;
    private List<Comment> commentList;
    private boolean isLogin;

    @Getter
    @Builder
    public static class Comment {
        private String nickname;
        private String profileImage;
        private String content;
        private boolean isLiked;
        private LocalDateTime createDateTime;
    }

}
