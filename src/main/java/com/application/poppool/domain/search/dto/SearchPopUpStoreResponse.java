package com.application.poppool.domain.search.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class SearchPopUpStoreResponse {

    private List<PopUpStore> popUpStoreList;
    private boolean loginYn;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PopUpStore {
        private Long id;
        private String name;
        private String mainImageUrl;
        private String categoryName;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String address;
        private boolean bookmarkYn;
    }


}
