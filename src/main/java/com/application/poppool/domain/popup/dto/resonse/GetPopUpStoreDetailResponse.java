package com.application.poppool.domain.popup.dto.resonse;

import lombok.*;

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
    private boolean bookmarkYn;
    private boolean loginYn;
    private String mainImageUrl;
    private List<PopUpStoreImage> imageList;
    private List<Comment> commentList;
    private List<PopUpStore> similarPopUpStoreList;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PopUpStore {
        private Long id;
        private String name;
        private String mainImageUrl;
        private LocalDateTime endDate;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Comment {
        private String nickname;
        private String instagramId;
        private String profileImageUrl;
        private String content;
        private boolean likeYn;
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
