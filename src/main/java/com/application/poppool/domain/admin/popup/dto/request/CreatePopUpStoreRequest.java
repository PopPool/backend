package com.application.poppool.domain.admin.popup.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CreatePopUpStoreRequest {

    private String name;
    private Integer categoryId;
    private String desc;
    private String address;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String mainImageUrl;
    private boolean bannerYn;
    private List<String> imageUrlList;
    private double latitude;
    private double longitude;
    private String markerTitle;
    private String markerSnippet;
}
