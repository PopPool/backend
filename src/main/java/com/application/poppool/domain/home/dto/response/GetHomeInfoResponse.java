package com.application.poppool.domain.home.dto.response;

import com.application.poppool.domain.category.enums.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetHomeInfoResponse {

    private String nickname;

    @Getter
    @Builder
    public static class newPopUpStores {
        private Category category;
        private String image;
    }

}
