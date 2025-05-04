package com.application.poppool.domain.category.controller;

import com.application.poppool.domain.sign_up.dto.response.GetCategoryListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "카테고리 관련 API")
public interface CategoryControllerDoc {


    @Operation(summary = "카테고리 목록 조회(비회원도 접근 가능)")
    ResponseEntity<GetCategoryListResponse> getCategoryList();


}
