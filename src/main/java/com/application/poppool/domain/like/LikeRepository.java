package com.application.poppool.domain.like;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.like.entity.LikeEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    boolean existsByUserAndComment(UserEntity user, CommentEntity comment);
    Optional<LikeEntity> findByUserAndComment(UserEntity user, CommentEntity comment);
    long countByComment(CommentEntity comment);
}
