package com.application.poppool.domain.file.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PreSignedUrlResponse {

    private String preSignedUrl;
}
