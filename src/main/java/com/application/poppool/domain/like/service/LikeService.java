package com.application.poppool.domain.like.service;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.like.LikeRepository;
import com.application.poppool.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    public boolean existsByUserAndComment(UserEntity user, CommentEntity comment) {
        return likeRepository.existsByUserAndComment(user, comment);
    }

}
