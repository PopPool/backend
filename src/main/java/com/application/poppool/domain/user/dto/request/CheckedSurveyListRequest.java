package com.application.poppool.domain.user.dto.request;

import com.application.poppool.domain.user.enums.WithDrawlSurvey;
import lombok.Getter;

import java.util.List;

@Getter
public class CheckedSurveyListRequest {

    List<CheckedSurvey> checkedSurveyList;

    @Getter
    public static class CheckedSurvey {
        private Long id;
        private WithDrawlSurvey survey;
        private String question;
    }

}
