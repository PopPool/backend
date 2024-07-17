package com.application.poppool.domain.user.dto.response;

import com.application.poppool.domain.interest.enums.InterestCategory;
import com.application.poppool.domain.user.enums.Gender;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetProfileResponse {

    private String profileImage;
    private String nickname;
    private String email;
    private String instagramId;
    private String intro;
    private Gender gender;
    private Integer age;
    private List<MyInterestInfo> interestList;

    @Getter
    @Builder
    public static class MyInterestInfo {
        private Long interestId;
        private InterestCategory interestCategory;
    }

}
