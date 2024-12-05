package com.application.poppool.domain.sign_up.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetCategoryListResponse {

    private List<CategoryResponse> categoryResponseList;

    @Getter
    @Builder
    public static class CategoryResponse {
        private Integer categoryId;
        private String categoryName;
    }

}
