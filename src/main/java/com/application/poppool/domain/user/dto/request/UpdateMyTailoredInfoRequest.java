package com.application.poppool.domain.user.dto.request;

import com.application.poppool.domain.user.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateMyTailoredInfoRequest {

    @Schema(description = "성별")
    @Pattern(regexp = "^(남성|여성|선택안함)$", message = "유효한 성별 값만 입력해주세요.")
    private Gender gender;
    @Schema(description = "나이")
    private int age;
}
