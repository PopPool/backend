package com.application.poppool.domain.comment.dto.response;

import com.application.poppool.domain.popup.dto.resonse.GetPopUpStoreDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class GetCommentsResponse {

    List<Comment> commentList;

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
    public static class CommentImage {
        private Long id;
        private String imageUrl;
    }
}
