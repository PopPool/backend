package com.application.poppool.domain.user.enums;

import com.application.poppool.global.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender implements BaseEnum {
    MALE("남성"),
    FEMALE("여성"),
    NONE("선택안함");

    @JsonValue
    private final String value;

    @JsonCreator
    public static Gender fromValueToEnum(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.getValue().equals(value)) {
                return gender;
            }
        }
        return null;
    }
}
