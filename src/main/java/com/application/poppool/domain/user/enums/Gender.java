package com.application.poppool.domain.user.enums;

import com.application.poppool.global.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Gender implements BaseEnum {
    MALE("남성"),
    FEMALE("여성"),
    NONE("선택안함");

    @JsonValue
    private final String value;

    Gender(String value) {
        this.value = value;
    }

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
