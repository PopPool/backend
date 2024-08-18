package com.application.poppool.global.converter;

import org.springframework.core.convert.converter.Converter;
import com.application.poppool.domain.category.enums.Category;
import org.springframework.stereotype.Component;

@Component
public class StringToCategoryConverter implements Converter<String, Category> {

    @Override
    public Category convert(String value) {
        return Category.fromValueToEnum(value);
    }
}
