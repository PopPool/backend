package com.application.poppool.domain.user.dto.response;

import com.application.poppool.domain.user.enums.Gender;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetProfileResponse {

    private String profileImageUrl;
    private String nickname;
    private String instagramId;
    private String intro;
    private Gender gender;
    private int age;
    private List<MyInterestCategoryInfo> interestCategoryList;

    @Getter
    @Builder
    public static class MyInterestCategoryInfo {
        private Integer categoryId;
        private String categoryName;
    }

}
