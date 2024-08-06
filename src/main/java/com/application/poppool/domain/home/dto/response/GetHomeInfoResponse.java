package com.application.poppool.domain.home.dto.response;

import com.application.poppool.domain.category.enums.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetHomeInfoResponse {

    private String nickname;
    private List<CustomPopUpStore> customPopUpStoreList;
    private List<PopularPopUpStore> popularPopUpStoreList;
    private List<NewPopUpStore> newPopUpStoreList;
    private boolean isLogin;

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
        private String image;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private int totalPages;
        private long totalElements;
    }


    /**
     * 인기 팝업
     */
    @Getter
    @Builder
    public static class PopularPopUpStore {
        private Long id;
        private Category category;
        private String name;
        private String address;
        private String image;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private int totalPages;
        private long totalElements;
    }

    /**
     * 신규 팝업
     */
    @Getter
    @Builder
    public static class NewPopUpStore {
        private Long id;
        private Category category;
        private String name;
        private String address;
        private String image;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private int totalPages;
        private long totalElements;
    }

}
