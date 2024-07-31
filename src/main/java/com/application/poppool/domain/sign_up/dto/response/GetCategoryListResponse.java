package com.application.poppool.domain.sign_up.dto.response;

import com.application.poppool.domain.category.enums.Category;
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
        private Long categoryId;
        private Category category;
    }

}
