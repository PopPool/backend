package com.application.poppool.domain.comment.service;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.like.service.LikeService;
import com.application.poppool.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final LikeService likeService;

    public boolean isCommentLikedByUser(UserEntity user, CommentEntity comment) {
        return likeService.existsByUserAndComment(user, comment);
    }

}
