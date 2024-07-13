package com.application.poppool.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

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
        private String image;
        private int likeCount;
    }
}
