package com.application.poppool.domain.user.repository;

import com.application.poppool.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {

    Optional<UserEntity> findByUserId(String userId);
    Optional<UserEntity> findByNickName(String nickName);
}
