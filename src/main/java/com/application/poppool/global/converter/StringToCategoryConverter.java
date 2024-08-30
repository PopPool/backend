package com.application.poppool.global.converter;

import com.application.poppool.domain.category.enums.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategoryConverter implements Converter<String, Category> {

    @Override
    public Category convert(String value) {
        return Category.fromValueToEnum(value);
    }
}
