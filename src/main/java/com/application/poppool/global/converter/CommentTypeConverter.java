package com.application.poppool.global.converter;

import com.application.poppool.domain.comment.enums.CommentType;

public class CommentTypeConverter extends EnumToStringConverter<CommentType>{
    public CommentTypeConverter() {
        super(CommentType.class);
    }
}
