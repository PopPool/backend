package com.application.poppool.global.converter;

import com.application.poppool.domain.interest.enums.InterestCategory;

public class InterestCategoryConverter extends EnumToStringConverter<InterestCategory>{
    public InterestCategoryConverter() {
        super(InterestCategory.class);
    }
}
