package com.application.poppool.domain.like.service;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.comment.repository.CommentRepository;
import com.application.poppool.domain.like.repository.LikeRepository;
import com.application.poppool.domain.like.entity.LikeEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.global.exception.BadRequestException;
import com.application.poppool.global.exception.ConcurrencyException;
import com.application.poppool.global.exception.ErrorCode;
import com.application.poppool.global.exception.NotFoundException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    /**
     * 좋아요
     * @param userId
     * @param commentId
     */
    @Transactional
    public void likeComment(String userId, Long commentId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DATA_NOT_FOUND));

        if (likeRepository.existsByUserAndComment(user, comment)) {
            throw new BadRequestException(ErrorCode.ALREADY_EXISTS_DATA);
        }

        // 좋아요 생성
        LikeEntity like = LikeEntity.builder()
                .user(user)
                .comment(comment)
                .build();

        // 좋아요 저장
        likeRepository.save(like);

        /** 동시성 이슈 방지를 위한 retry 및 예외 처리*/
        int retryCount = 0;
        int maxRetryCount = 3; // 최대 재시도 횟수

        while (retryCount < maxRetryCount) {
            try {
                // 코멘트 좋아요 수 + 1
                comment.incrementLikeCount();
                break;
            } catch (OptimisticLockException e) {
                retryCount++;
                if (retryCount >= maxRetryCount) {
                    throw new ConcurrencyException(ErrorCode.CONCURRENCY_ERROR);
                }
            }
        }
    }

    /**
     * 좋아요 취소
     * @param userId
     * @param commentId
     */
    @Transactional
    public void unlikeComment(String userId, Long commentId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DATA_NOT_FOUND));

        LikeEntity like = likeRepository.findByUserAndComment(user, comment)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DATA_NOT_FOUND));

        // 좋아요 취소
        likeRepository.delete(like);

        /** 동시성 이슈 방지를 위한 retry 및 예외 처리*/
        int retryCount = 0;
        int maxRetryCount = 3; // 최대 재시도 횟수

        while (retryCount < maxRetryCount) {
            try {
                // 코멘트 좋아요 수 - 1
                comment.decrementLikeCount();
                break;
            } catch (OptimisticLockException e) {
                retryCount++;
                if (retryCount >= maxRetryCount) {
                    throw new ConcurrencyException(ErrorCode.CONCURRENCY_ERROR);
                }
            }
        }
    }


    /**
     * 좋아요 여부 확인
     * @param user
     * @param comment
     * @return
     */
    @Transactional(readOnly = true)
    public boolean existsByUserAndComment(UserEntity user, CommentEntity comment) {
        return likeRepository.existsByUserAndComment(user, comment);
    }

    /**
     * 코멘트에 달린 좋아요 수 count
     * @param comment
     * @return
     */
    @Transactional(readOnly = true)
    public long countByComment(CommentEntity comment) {
        return likeRepository.countByComment(comment);
    }

}
