package com.application.poppool.domain.popup.dto.resonse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPopUpStoreDirectionResponse {

    private Long id;
    private String categoryName;
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
