package com.application.poppool.domain.comment.dto.request;

import com.application.poppool.domain.comment.enums.CommentType;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateCommentRequest {

    private Long popUpStoreId;
    private String content;
    private CommentType commentType;
    private List<String> imageUrlList;
}
