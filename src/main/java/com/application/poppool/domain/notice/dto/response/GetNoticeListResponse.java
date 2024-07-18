package com.application.poppool.domain.notice.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetNoticeListResponse {

    private List<NoticeInfo> noticeInfoList;

    @Getter
    @Builder
    public static class NoticeInfo {
        private Long id;
        private String title;
        private LocalDateTime createdDateTime;
    }


}
