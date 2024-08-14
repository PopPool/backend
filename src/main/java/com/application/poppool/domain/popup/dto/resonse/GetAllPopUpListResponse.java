package com.application.poppool.domain.popup.dto.resonse;

import com.application.poppool.domain.category.enums.Category;
import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetAllPopUpListResponse {

    private List<PopUpStore> popUpStoreList;

    /**
     * 맞춤 팝업
     */
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
    }

}
