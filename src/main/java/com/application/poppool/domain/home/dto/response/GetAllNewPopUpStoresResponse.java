package com.application.poppool.domain.home.dto.response;

import com.application.poppool.domain.category.enums.Category;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetAllNewPopUpStoresResponse {

    private List<GetHomeInfoResponse.PopUpStore> newPopUpStoreList;
    private int newPopUpStoreTotalPages; // 신규 팝업 리스트 페이지 수
    private long newPopUpStoreTotalElements; // 신규 팝업 리스트 전체 개수

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PopUpStore {
        private Long id;
        private Category category;
        private String name;
        private String address;
        private String mainImageUrl;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private boolean bookmarkYn;
    }
}
