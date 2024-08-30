package com.application.poppool.domain.user.enums;

import com.application.poppool.global.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum WithDrawlSurvey implements BaseEnum {

    NO_DESIRED_POPUP("원하는 팝업에 대한 정보가 없어요"),
    INSUFFICIENT_POPUP_INFO("팝업 정보가 적어요"),
    LOW_USAGE_FREQUENCY("이용빈도가 낮아요"),
    WANT_TO_REJOIN("다시 가입하고 싶어요"),
    FREQUENT_APP_ERRORS("앱에 오류가 많이 생겨요"),
    OTHER("기타");

    @JsonValue
    private final String value;

    WithDrawlSurvey(String value) {
        this.value = value;
    }

    @JsonCreator
    public static WithDrawlSurvey fromValueToEnum(String value) {
        for (WithDrawlSurvey survey : WithDrawlSurvey.values()) {
            if (survey.getValue().equals(value)) {
                return survey;
            }
        }
        return null;
    }
}
