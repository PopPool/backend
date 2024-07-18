package com.application.poppool.domain.notice.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetNoticeDetailResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDateTime;

}
