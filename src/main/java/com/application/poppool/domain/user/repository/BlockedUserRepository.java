package com.application.poppool.domain.user.repository;

import com.application.poppool.domain.user.entity.BlockedUserEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlockedUserRepository extends JpaRepository<BlockedUserEntity,Long>, BlockedUserRepositoryCustom {

    Optional<BlockedUserEntity> findByUserAndBlockedUser(UserEntity user, UserEntity blockedUser);
    List<BlockedUserEntity> findAllByUser(UserEntity user);

    // 두 사용자의 차단 관계가 존재하는지 체크
    boolean existsByUserAndBlockedUser(UserEntity user, UserEntity blockedUser);

}
