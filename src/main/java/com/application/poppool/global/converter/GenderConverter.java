package com.application.poppool.global.converter;

import com.application.poppool.domain.user.enums.Gender;

public class GenderConverter extends EnumToStringConverter<Gender> {
    public GenderConverter() {
        super(Gender.class);
    }
}
