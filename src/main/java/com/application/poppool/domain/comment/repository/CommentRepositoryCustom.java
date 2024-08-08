package com.application.poppool.domain.comment.repository;

import com.application.poppool.domain.comment.entity.CommentEntity;

import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentEntity> findAllPopUpStoreComments(String userId, Long popUpStoreId);
}
