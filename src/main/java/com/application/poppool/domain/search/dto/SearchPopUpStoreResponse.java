package com.application.poppool.domain.search.dto;

import lombok.*;

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
        private String address;
    }


}
