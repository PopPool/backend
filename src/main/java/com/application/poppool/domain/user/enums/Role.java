package com.application.poppool.domain.user.enums;

import com.application.poppool.global.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum Role implements BaseEnum {
    USER("ROLE_USER","사용자"), ADMIN("ROLE_ADMIN","관리자");

    private final String value;
    private final String desc;

    Role(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }


}
