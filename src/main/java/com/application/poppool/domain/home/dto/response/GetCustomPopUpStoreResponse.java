package com.application.poppool.domain.home.dto.response;

import com.application.poppool.domain.category.enums.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetCustomPopUpStoreResponse {

    private List<GetHomeInfoResponse.CustomPopUpStore> customPopUpStoreList;
    /**
     * 맞춤 팝업
     */
    @Getter
    @Builder
    public static class CustomPopUpStore {
        private Long id;
        private Category category;
        private String name;
        private String address;
        private String mainImageUrl;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private int totalPages;
        private long totalElements;
    }

}
