package com.application.poppool.domain.token.repository;

import com.application.poppool.domain.token.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {

    Optional<RefreshTokenEntity> findByUserId(String userId);

    Optional<RefreshTokenEntity> deleteByUserId(String userId);
}
