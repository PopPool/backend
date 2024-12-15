package com.application.poppool.domain.comment.dto.request;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UpdateCommentRequest {

    private Long popUpStoreId;
    private Long commentId;
    private String content;
    private List<ImageAction> imageUrlList = new ArrayList<>();

    @Getter
    public static class ImageAction {
        private Long imageId;
        private String imageUrl;
        private ActionType actionType;

        public enum ActionType {
            ADD, DELETE, KEEP
        }
    }
}
