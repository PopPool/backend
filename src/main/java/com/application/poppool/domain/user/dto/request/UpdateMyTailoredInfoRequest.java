package com.application.poppool.domain.user.dto.request;

import com.application.poppool.domain.user.enums.Gender;
import lombok.Getter;

@Getter
public class UpdateMyTailoredInfoRequest {

    private Gender gender;
    private int age;
}
