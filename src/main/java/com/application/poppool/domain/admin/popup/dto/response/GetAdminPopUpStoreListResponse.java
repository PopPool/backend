package com.application.poppool.domain.admin.popup.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
public class GetAdminPopUpStoreListResponse {

    private List<PopUpStore> popUpStoreList;
    private int totalPages; // 맞춤 팝업 리스트 페이지 수
    private long totalElements; // 맞춤 팝업 리스트 전체 개수

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PopUpStore {
        private Long id;
        private String name;
        private String categoryName;
        private String mainImageUrl;

    }
}
