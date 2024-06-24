package com.application.poppool.domain.Interest.repository;

import com.application.poppool.domain.Interest.entity.InterestEntity;
import com.application.poppool.domain.Interest.enums.InterestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterestRepository extends JpaRepository<InterestEntity, InterestType> {

    Optional<InterestEntity> findByInterestId(InterestType interestType);

}
