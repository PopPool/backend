package com.application.poppool.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetWithDrawlSurveyResponse {

    private List<Survey> withDrawlSurveyList;

    @Getter
    @Builder
    public static class Survey {
        private Long id;
        private String question;
    }
}
