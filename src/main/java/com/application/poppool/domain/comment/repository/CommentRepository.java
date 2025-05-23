package com.application.poppool.domain.comment.repository;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import jakarta.persistence.LockModeType;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>, CommentRepositoryCustom {
    @Lock(LockModeType.OPTIMISTIC) // 조회시점부터 트랜잭션 끝날 때까지 다른 트랜잭션에 의해 변경되지 않음을 보장
    @NonNull
    Optional<CommentEntity> findById(@NonNull Long id);

    boolean existsByUserAndPopUpStore(UserEntity user, PopUpStoreEntity popUpStore);

    List<CommentEntity> findByUser(UserEntity user);

}
