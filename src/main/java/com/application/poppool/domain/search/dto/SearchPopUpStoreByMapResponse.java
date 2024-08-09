package com.application.poppool.domain.search.dto;

import com.application.poppool.domain.category.enums.Category;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class SearchPopUpStoreByMapResponse {

    private List<PopUpStore> popUpStoreList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PopUpStore {
        private Long id;
        private Category category;
        private String name;
        private String address;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
    }

}
