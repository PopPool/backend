package com.application.poppool.domain.comment.dto.request;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CreateCommentRequest {

    private String userId;
    private Long popUpStoreId;
    private String content;
    private List<String> imageUrlList = new ArrayList<>();
}
