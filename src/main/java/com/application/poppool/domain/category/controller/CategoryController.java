package com.application.poppool.domain.category.controller;

import com.application.poppool.domain.category.service.CategoryService;
import com.application.poppool.domain.sign_up.dto.response.GetCategoryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController implements CategoryControllerDoc{

    private final CategoryService categoryService;

    /**
     * 관심 카테고리 목록 조회
     *
     * @return
     */
    @Override
    @GetMapping
    public ResponseEntity<GetCategoryListResponse> getCategoryList() {
        return ResponseEntity.ok(categoryService.getCategoryList());
    }

}
