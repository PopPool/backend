package com.application.poppool.domain.sign_up.dto.response;

import com.application.poppool.domain.user.enums.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetGenderResponse {

    private Gender gender;
    private String value;

}
