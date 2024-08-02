package com.application.poppool.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetMyCommentedPopUpStoreListResponse {

    private List<PopUpInfo> popUpInfoList;
    private int totalPages;
    private long totalElements;

    @Getter
    @Builder
    public static class PopUpInfo {
        private Long popUpStoreId;
        private String popUpStoreName;
        private String desc;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String address;
        private boolean isClosed;
    }

}
