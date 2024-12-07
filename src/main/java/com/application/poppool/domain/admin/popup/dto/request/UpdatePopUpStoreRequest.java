package com.application.poppool.domain.admin.popup.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
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
        @Schema(description = "팝업스토어 ID")
        @NotBlank(message = "팝업스토어 ID는 필수입니다.")
        private Long id;
        @Schema(description = "팝업스토어명")
        @NotBlank(message = "팝업스토어명을 입력해주세요.")
        private String name;
        @Schema(description = "팝업스토어 카테고리 ID")
        @NotNull(message = "카테고리를 지정해주세요.")
        private Integer categoryId;
        @Schema(description = "팝업스토어 설명")
        private String desc;
        @Schema(description = "팝업스토어 주소")
        @NotBlank(message = "팝업 스토어 주소를 입력해주세요.")
        private String address;
        @Schema(description = "팝업스토어 시작일시")
        @NotNull(message = "시작일시를 입력해주세요.")
        private LocalDateTime startDate;
        @Schema(description = "팝업스토어 종료일시")
        @NotNull(message = "종료일시를 입력해주세요.")
        private LocalDateTime endDate;
        @Schema(description = "팝업스토어 대표 이미지 URL")
        @NotBlank(message = "대표 이미지를 등록해주세요.")
        private String mainImageUrl;
        @Schema(description = "배너 여부")
        private boolean bannerYn;
        @Schema(description = "팝업스토어 이미지 URL 리스트")
        private List<String> imageUrl;

        @AssertTrue(message = "종료 일자는 시작 일자보다 이후여야 합니다.")
        public boolean isStartDateBeforeEndDate() {
            if (startDate != null && endDate != null) {
                return startDate.isBefore(endDate);
            }
            return true;
        }
    }

    @Getter
    public static class Location {
        @DecimalMin(value = "-180.0", message = "위도는 -180.0보다 크거나 같아야 합니다.")
        @DecimalMax(value = "180.0", message = "위도는 180.0보다 작거나 같아야 합니다.")
        private double latitude;
        @DecimalMin(value = "-180.0", message = "경도는 -180.0보다 크거나 같아야 합니다.")
        @DecimalMax(value = "180.0", message = "경도는 180.0보다 작거나 같아야 합니다.")
        private double longitude;
        private String markerTitle;
        private String markerSnippet;
    }

}
