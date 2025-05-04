package com.application.poppool.domain.category.service;

import com.application.poppool.domain.category.dto.CreateCategoryRequest;
import com.application.poppool.domain.category.entity.CategoryEntity;
import com.application.poppool.domain.category.repository.CategoryRepository;
import com.application.poppool.domain.sign_up.dto.response.GetCategoryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    public GetCategoryListResponse getCategoryList() {

        // 카테고리 목록 전체 조회
        List<CategoryEntity> categoryList = categoryRepository.findAllByOrderByCategoryId();


        List<GetCategoryListResponse.CategoryResponse> interestResponse = categoryList.stream()
                .map(category -> GetCategoryListResponse.CategoryResponse.builder()
                        .categoryId(category.getCategoryId())
                        .categoryName(category.getCategoryName())
                        .build())
                .collect(Collectors.toList());

        return GetCategoryListResponse.builder().categoryResponseList(interestResponse).build();
    }


    /**
     * 관리자가 카테고리를 DB에 등록
     *
     * @param request
     */
    @Transactional
    public void addCategory(CreateCategoryRequest request) {
        CategoryEntity categoryEntity = categoryRepository.findByCategoryName(request.getCategoryName())
                .orElseGet(() -> categoryRepository.save(
                        CategoryEntity.builder()
                                .categoryName(request.getCategoryName())
                                .build()
                ));
        }

    }
