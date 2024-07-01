package com.application.poppool.domain.token.repository;

import com.application.poppool.domain.token.entity.BlackListTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListTokenRepository extends JpaRepository<BlackListTokenEntity, Long> {


    boolean existsByToken(String token);


}
