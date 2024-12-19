package com.application.poppool.domain.admin.popup.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CreatePopUpStoreRequest {

    @Schema(description = "팝업스토어명")
    @NotBlank(message = "팝업스토어명을 입력해주세요.")
    private String name;
    @Schema(description = "카테고리 ID")
    @NotNull(message = "카테고리를 지정해주세요.")
    private Integer categoryId;
    @Schema(description = "팝업스토어 설명")
    private String desc;
    @Schema(description = "팝업스토어 주소")
    @NotBlank(message = "팝업스토어 주소를 입력해주세요")
    private String address;
    @Schema(description = "팝업스토어 시작일시" , format = "yyyy-MM-ddTHH:mm:ss")
    @NotNull(message = "시작일시를 입력해주세요.")
    private LocalDateTime startDate;
    @Schema(description = "팝업스토어 종료일시" , format = "yyyy-MM-ddTHH:mm:ss")
    @NotNull(message = "종료일시를 입력해주세요.")
    private LocalDateTime endDate;
    @Schema(description = "팝업스토어 대표 이미지 URL")
    @NotBlank(message = "대표 이미지를 등록해주세요.")
    private String mainImageUrl;
    @Schema(description = "배너 여부")
    private boolean bannerYn;
    @Schema(description = "팝업스토어 이미지 URL 리스트")
    private List<String> imageUrlList = new ArrayList<>();
    
    @Schema(description = "팝업스토어 위도")
    @DecimalMin(value = "-180.0", message = "위도는 -180.0보다 크거나 같아야 합니다.")
    @DecimalMax(value = "180.0", message = "위도는 180.0보다 작거나 같아야 합니다.")
    private double latitude;
    @Schema(description = "팝업스토어 경도")
    @DecimalMin(value = "-180.0", message = "경도는 -180.0보다 크거나 같아야 합니다.")
    @DecimalMax(value = "180.0", message = "경도는 180.0보다 작거나 같아야 합니다.")
    private double longitude;
    @Schema(description = "팝업스토어 마커 제목")
    private String markerTitle;
    @Schema(description = "팝업스토어 마커 설명")
    private String markerSnippet;

    @AssertTrue(message = "종료 일자는 시작 일자보다 이후여야 합니다.")
    public boolean isStartDateBeforeEndDate() {
        if (startDate != null && endDate != null) {
            return startDate.isBefore(endDate);
        }
        return true;
    }

}
