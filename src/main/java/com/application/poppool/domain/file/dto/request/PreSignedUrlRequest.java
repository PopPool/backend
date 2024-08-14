package com.application.poppool.domain.file.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class PreSignedUrlRequest {

    private List<String> objectKeyList;
}
