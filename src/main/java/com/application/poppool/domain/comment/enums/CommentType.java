package com.application.poppool.domain.comment.enums;

import com.application.poppool.global.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum CommentType implements BaseEnum {
    NORMAL("일반"), INSTAGRAM("인스타");

    private final String value;

    CommentType(String value) {
        this.value = value;
    }

}
