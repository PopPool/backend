package com.application.poppool.domain.user.repository;

import com.application.poppool.domain.user.entity.UserInterestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInterestRepository extends JpaRepository<UserInterestEntity, Long> {

}
