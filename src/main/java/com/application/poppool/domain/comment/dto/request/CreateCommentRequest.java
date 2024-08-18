package com.application.poppool.domain.comment.dto.request;

import com.application.poppool.domain.comment.enums.CommentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CreateCommentRequest {

    private String userId;
    private Long popUpStoreId;
    private String content;
    private CommentType commentType;
    private List<String> imageUrlList = new ArrayList<>();
}
