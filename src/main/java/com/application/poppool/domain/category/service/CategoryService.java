package com.application.poppool.domain.category.service;

import com.application.poppool.domain.category.dto.CreateCategoryRequest;
import com.application.poppool.domain.category.entity.CategoryEntity;
import com.application.poppool.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

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
