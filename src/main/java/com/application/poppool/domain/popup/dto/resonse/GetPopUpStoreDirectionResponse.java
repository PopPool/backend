package com.application.poppool.domain.popup.dto.resonse;

import com.application.poppool.domain.category.enums.Category;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetPopUpStoreDirectionResponse {

    private Long id;
    private Category category;
    private String name;
    private String address;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double latitude;
    private double longitude;
    private Long markerId;
    private String markerTitle;
    private String markerSnippet;
}
