package com.application.poppool.domain.user.dto.request;

import com.application.poppool.domain.user.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateMyTailoredInfoRequest {

    @Schema(description = "성별")
    private Gender gender;
    @Schema(description = "나이")
    private int age;
}
