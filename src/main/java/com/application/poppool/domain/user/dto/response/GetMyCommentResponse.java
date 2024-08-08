package com.application.poppool.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetMyCommentResponse{

    private List<MyCommentInfo> myCommentList;
    private int totalPages;
    private long totalElements;

    @Getter
    @Builder
    public static class MyCommentInfo {
        private Long commentId;
        private String content;
        private long likeCount;
        private LocalDateTime createDateTime;
        private MyCommentedPopUpInfo popUpStoreInfo;
    }

    @Getter
    @Builder
    public static class MyCommentedPopUpInfo {
        private Long popUpStoreId;
        private String popUpStoreName;
        private String mainImageUrl;
        private boolean isClosed;
    }

}
