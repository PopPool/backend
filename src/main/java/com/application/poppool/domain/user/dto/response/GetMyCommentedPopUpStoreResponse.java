package com.application.poppool.domain.user.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetMyCommentedPopUpStoreResponse {

    private List<PopUpInfo> popUpInfoList;

    @Getter
    @Builder
    public static class PopUpInfo {
        private Long popUpStoreId;
        private String popUpStoreName;
        private String desc;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String address;
        private boolean closedYn;
    }

}
