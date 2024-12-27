package com.application.poppool.domain.user.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetCommenterCommentListResponse {

    private List<Comment> commentList;
    private int totalPages;
    private long totalElements;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Comment {
        private Long commentId;
        private String content;
        private long likeCount;
        private LocalDateTime createDateTime;
        private CommentedPopUpStore popUpStoreInfo;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentedPopUpStore {
        private Long popUpStoreId;
        private String popUpStoreName;
        private String mainImageUrl;
        private boolean closeYn;
    }

}
