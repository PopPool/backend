package com.application.poppool.domain.file.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PreSignedUrlResponse {

    private List<PreSignedUrl> preSignedUrlList;

    @Getter
    @Builder
    public static class PreSignedUrl {
        private String objectKey;
        private String preSignedUrl;
    }

}
