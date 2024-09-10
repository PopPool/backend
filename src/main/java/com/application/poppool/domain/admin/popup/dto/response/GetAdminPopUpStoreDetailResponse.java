package com.application.poppool.domain.admin.popup.dto.response;

import com.application.poppool.domain.category.enums.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetAdminPopUpStoreDetailResponse {

    private Long id;
    private String name;
    private Category category;
    private String desc;
    private String address;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String createUserId;
    private LocalDateTime createDateTime;
    private String mainImageUrl;
    private List<Image> imageList;
    private double latitude;
    private double longitude;
    private String markerTitle;
    private String markerSnippet;

    @Getter
    @Builder
    public static class Image {
        private Long id;
        private String imageUrl;
    }

}
