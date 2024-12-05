package com.application.poppool.domain.category.repository;

import com.application.poppool.domain.category.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    List<CategoryEntity> findAllByOrderByCategoryId();

    Optional<CategoryEntity> findByCategoryId(Integer categoryId);

    Optional<CategoryEntity> findByCategoryName(String categoryName);

}
