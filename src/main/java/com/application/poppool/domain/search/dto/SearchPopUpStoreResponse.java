package com.application.poppool.domain.search.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchPopUpStoreResponse {

    private List<PopUpStore> popUpStoreList;

    @Getter
    @Builder
    public static class PopUpStore {
        private Long id;
        private String name;
        private String address;
    }


}
