package com.application.poppool.global.converter;


import com.application.poppool.domain.category.enums.Category;

public class CategoryConverter extends EnumToStringConverter<Category>{
    public CategoryConverter() {
        super(Category.class);
    }
}
