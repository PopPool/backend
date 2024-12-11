package com.application.poppool.domain.comment.repository;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.comment.enums.CommentType;
import com.application.poppool.domain.user.dto.response.GetMyCommentResponse;
import com.application.poppool.domain.user.dto.response.GetMyPageResponse;
import com.application.poppool.global.enums.CommentSortCode;
import com.application.poppool.global.enums.PopUpSortCode;
import com.application.poppool.global.enums.SortCode;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentEntity> findAllPopUpStoreComments(String userId, CommentType commentType, Long popUpStoreId);

    List<GetMyPageResponse.MyCommentedPopUpInfo> findMyCommentedPopUpInfo(String userId);

    List<GetMyCommentResponse.MyCommentInfo> findByMyCommentsWithPopUpStore(String userId, CommentType commentType, List<CommentSortCode> sortCodes, Pageable pageable);

    long countMyComments(String userId, CommentType commentType);

}
