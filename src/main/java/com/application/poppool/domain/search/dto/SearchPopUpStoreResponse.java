package com.application.poppool.domain.search.dto;

import com.application.poppool.domain.category.enums.Category;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class SearchPopUpStoreResponse {

    private List<PopUpStore> popUpStoreList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PopUpStore {
        private Long id;
        private String name;
        private Category category;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String address;
    }


}
