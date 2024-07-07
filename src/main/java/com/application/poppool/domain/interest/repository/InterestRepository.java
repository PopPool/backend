package com.application.poppool.domain.interest.repository;

import com.application.poppool.domain.interest.entity.InterestEntity;
import com.application.poppool.domain.interest.enums.InterestCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterestRepository extends JpaRepository<InterestEntity, Long> {

    Optional<InterestEntity> findByInterestId(Long interestId);
    Optional<InterestEntity> findByInterestCategory(InterestCategory interestCategory);

}
