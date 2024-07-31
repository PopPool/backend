package com.application.poppool.domain.category.repository;

import com.application.poppool.domain.category.entity.CategoryEntity;
import com.application.poppool.domain.category.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findAllByOrderByCategoryId();

    Optional<CategoryEntity> findByCategoryId(Long categoryId);
    Optional<CategoryEntity> findByCategory(Category category);

}
