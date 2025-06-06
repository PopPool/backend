package com.application.poppool.domain.location.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetViewBoundPopUpStoreListResponse {

    private List<PopUpStore> popUpStoreList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PopUpStore {
        private Long id;
        private String categoryName;
        private String name;
        private String address;
        private String mainImageUrl;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private double latitude;
        private double longitude;
        private Long markerId;
        private String markerTitle;
        private String markerSnippet;
    }


}


