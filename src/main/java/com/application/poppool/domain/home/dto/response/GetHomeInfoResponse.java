package com.application.poppool.domain.home.dto.response;

import com.application.poppool.domain.category.enums.Category;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetHomeInfoResponse {

    private List<BannerPopUpStore> bannerPopUpStoreList;
    private String nickname;
    private List<PopUpStore> customPopUpStoreList;
    private int customPopUpStoreTotalPages; // 맞춤 팝업 리스트 페이지 수
    private long customPopUpStoreTotalElements; // 맞춤 팝업 리스트 전체 개수
    private List<PopUpStore> popularPopUpStoreList;
    private int popularPopUpStoreTotalPages; // 인기 팝업 리스트 페이지 수
    private long popularPopUpStoreTotalElements; // 인기 팝업 리스트 전체 개수
    private List<PopUpStore> newPopUpStoreList;
    private int newPopUpStoreTotalPages; // 신규 팝업 리스트 페이지 수
    private long newPopUpStoreTotalElements; // 신규 팝업 리스트 전체 개수
    private boolean loginYn;

    @Getter
    @Builder
    public static class BannerPopUpStore {
        private Long id;
        private String name;
        private String mainImageUrl;
    }

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
