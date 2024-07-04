package com.application.poppool.domain.user.repository;

import com.application.poppool.domain.user.entity.WithDrawalSurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithDrawlRepository extends JpaRepository<WithDrawalSurveyEntity, Long> {
    List<WithDrawalSurveyEntity> findAll();
}
