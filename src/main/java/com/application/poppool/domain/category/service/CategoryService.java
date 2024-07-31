package com.application.poppool.domain.category.service;

import com.application.poppool.domain.category.entity.CategoryEntity;
import com.application.poppool.domain.category.enums.Category;
import com.application.poppool.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 관리자가 카테고리를 DB에 등록
     *
     * @param categories
     */
    @Transactional
    public void addCategory(Set<Category> categories) {
        for (Category category : categories) {
            CategoryEntity categoryEntity = categoryRepository.findByCategory(category)
                    .orElseGet(() -> categoryRepository.save(
                            CategoryEntity.builder()
                                    .category(category)
                                    .build()
                    ));
        }
    }
}
