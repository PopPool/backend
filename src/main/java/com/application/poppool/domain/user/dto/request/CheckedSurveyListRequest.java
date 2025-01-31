package com.application.poppool.domain.user.dto.request;

import com.application.poppool.domain.user.enums.WithDrawlSurvey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CheckedSurveyListRequest {

    List<CheckedSurvey> checkedSurveyList = new ArrayList<>();

    @Getter
    public static class CheckedSurvey {
        @NotNull(message = "설문 항목 ID는 필수 값입니다.")
        @Schema(description = "설문 항목 ID")
        private Long id;
        @Schema(description = "설문 항목")
        @NotNull(message = "설문 항목을 입력해주세요.")
        private WithDrawlSurvey survey;
    }

}
