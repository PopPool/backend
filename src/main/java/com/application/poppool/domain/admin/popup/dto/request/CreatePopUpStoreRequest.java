package com.application.poppool.domain.admin.popup.dto.request;

import com.application.poppool.domain.category.enums.Category;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CreatePopUpStoreRequest {

    private String name;
    private Category category;
    private String desc;
    private String address;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String mainImageUrl;
    private List<String> imageUrlList;
    private double latitude;
    private double longitude;
}
