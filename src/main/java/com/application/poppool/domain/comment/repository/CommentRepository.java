package com.application.poppool.domain.comment.repository;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import jakarta.persistence.LockModeType;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity,Long>, CommentRepositoryCustom {
    @Lock(LockModeType.OPTIMISTIC) // 조회시점부터 트랜잭션 끝날 때까지 다른 트랜잭션에 의해 변경되지 않음을 보장
    @NonNull
    Optional<CommentEntity> findById(@NonNull Long id);

    Page<CommentEntity> findByUser(UserEntity user, Pageable pageable);

    @Query("SELECT c FROM CommentEntity c JOIN FETCH c.popUpStore WHERE c.user.userId = :userId")
    List<CommentEntity> findMyCommentedWithPopUpStoreList(@Param("userId") String userId);

    @Query("SELECT c FROM CommentEntity c JOIN FETCH c.popUpStore WHERE c.user.userId = :userId")
    Page<CommentEntity> findByMyCommentsWithPopUpStorePage(@Param("userId") String userId, Pageable pageable);

}
