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
    private long commentCount;
    private boolean isBookmarked;
    private boolean isLogin;
    private String popUpStoreMainImageUrl;
    private List<PopUpStoreImage> popUpStoreImageList;
    private List<Comment> commentList;

    @Getter
    @Builder
    public static class Comment {
        private String nickname;
        private String instagramId;
        private String profileImageUrl;
        private String content;
        private boolean isLiked;
        private long likeCount;
        private LocalDateTime createDateTime;
        private List<CommentImage> commentImageList;
    }

    @Getter
    @Builder
    public static class PopUpStoreImage {
        private Long id;
        private String imageUrl;
    }

    @Getter
    @Builder
    public static class CommentImage {
        private Long id;
        private String imageUrl;
    }

}
