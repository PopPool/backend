package com.application.poppool.domain.token.repository;

import com.application.poppool.domain.token.entity.AppleRefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppleRefreshTokenRepository extends JpaRepository<AppleRefreshTokenEntity, String> {

    Optional<AppleRefreshTokenEntity> findByUserId(String userId);
}
