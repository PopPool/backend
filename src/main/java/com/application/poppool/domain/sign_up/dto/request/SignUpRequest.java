package com.application.poppool.domain.sign_up.dto.request;

import com.application.poppool.domain.interest.enums.InterestType;
import com.application.poppool.domain.auth.enums.SocialType;
import com.application.poppool.domain.user.enums.Gender;
import lombok.Getter;

import java.util.Set;

@Getter
public class SignUpRequest {

    private String userId;
    private String nickName;
    private Gender gender;
    private Integer age;
    private SocialType socialType;
    private Set<InterestType> interests;

}
