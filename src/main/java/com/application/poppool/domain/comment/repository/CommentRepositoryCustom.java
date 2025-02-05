package com.application.poppool.domain.comment.repository;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.comment.enums.CommentType;
import com.application.poppool.domain.user.dto.UserCommentCountByPopUpStore;
import com.application.poppool.domain.user.dto.response.GetMyPageResponse;
import com.application.poppool.global.enums.CommentSortCode;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentEntity> getPopUpStoreComments(String userId, CommentType commentType, Long popUpStoreId);

    List<CommentEntity> getAllPopUpStoreComments(String userId, CommentType commentType, Long popUpStoreId, Pageable pageable);


    List<GetMyPageResponse.MyCommentedPopUpInfo> findMyCommentedPopUpInfo(String userId);

    List<CommentEntity> findMyCommentsWithPopUpStore(String userId, CommentType commentType, List<CommentSortCode> sortCodes, Pageable pageable);
    long countMyComments(String userId, CommentType commentType);

    List<UserCommentCountByPopUpStore> findCommentCountGroupedByPopupStore(String userId);

    List<CommentEntity> findCommenterCommentsWithPopUpStore(String userId, CommentType commentType, Pageable pageable);

    long countCommenterComments(String userId, CommentType commentType);

}
