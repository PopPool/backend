package com.application.poppool.domain.admin.popup.dto.request;

import com.application.poppool.domain.category.enums.Category;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UpdatePopUpStoreRequest {

    private PopUpStore popUpStore;
    private Location location;
    private List<String> imagesToAdd;
    private List<Long> imagesToDelete;

    @Getter
    public static class PopUpStore {
        private Long id;
        private String name;
        private Category category;
        private String desc;
        private String address;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String mainImageUrl;
        private boolean bannerYn;
        private List<String> imageUrl;
    }

    @Getter
    public static class Location {
        private double latitude;
        private double longitude;
        private String markerTitle;
        private String markerSnippet;
    }

}
