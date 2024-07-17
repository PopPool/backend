package com.application.poppool.domain.user.repository;

import com.application.poppool.domain.user.entity.BlockedUserEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlockedUserRepository extends JpaRepository<BlockedUserEntity,Long> {

    Optional<BlockedUserEntity> findByUserAndBlockedUser(UserEntity user, UserEntity blockedUser);
    List<BlockedUserEntity> findAllByUser(UserEntity user);

}
