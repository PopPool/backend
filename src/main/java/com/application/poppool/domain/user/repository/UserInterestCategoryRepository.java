package com.application.poppool.domain.user.repository;

import com.application.poppool.domain.user.entity.UserInterestCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInterestCategoryRepository extends JpaRepository<UserInterestCategoryEntity, Long> {

}
