package com.application.poppool.domain.admin.popup.dto.response;

import com.application.poppool.domain.category.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
public class GetAdminPopUpStoreListResponse {

    private List<PopUpStore> popUpStoreList;
    private int totalPages; // 맞춤 팝업 리스트 페이지 수
    private long totalElements; // 맞춤 팝업 리스트 전체 개수

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PopUpStore {
        private Long id;
        private String name;
        private Category category;
        private String mainImageUrl;

    }
}
