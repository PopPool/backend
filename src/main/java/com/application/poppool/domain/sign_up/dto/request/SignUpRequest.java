package com.application.poppool.domain.sign_up.dto.request;

import com.application.poppool.domain.auth.enums.SocialType;
import com.application.poppool.domain.user.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Set;

@Getter
public class SignUpRequest {
    
    @Schema(description = "애플 인가 코드")
    private String appleAuthorizationCode;
    @Schema(description = "회원 닉네임")
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
    @Schema(description = "회원 성별")
    @NotNull(message = "성별을 입력해주세요,")
    private Gender gender;
    @Schema(description = "회원 연령")
    private int age;
    @Schema(description = "회원 이메일")
    private String socialEmail;
    @NotNull(message = "소셜 타입은 필수입니다.")
    @Schema(description = "소셜 타입(카카오/애플)")
    private SocialType socialType;
    @Schema(description = "카테고리 ID")
    @NotEmpty(message = "카테고리를 선택해주세요.")
    private Set<Integer> interestCategories;

}
