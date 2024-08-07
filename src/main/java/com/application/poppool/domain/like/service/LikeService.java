package com.application.poppool.domain.like.service;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.comment.repository.CommentRepository;
import com.application.poppool.domain.like.repository.LikeRepository;
import com.application.poppool.domain.like.entity.LikeEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.global.exception.BadRequestException;
import com.application.poppool.global.exception.ErrorCode;
import com.application.poppool.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

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
    }

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
        
    }


    @Transactional(readOnly = true)
    public boolean existsByUserAndComment(UserEntity user, CommentEntity comment) {
        return likeRepository.existsByUserAndComment(user, comment);
    }

    @Transactional(readOnly = true)
    public long countByComment(CommentEntity comment) {
        return likeRepository.countByComment(comment);
    }

}
