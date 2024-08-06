package com.application.poppool.domain.popup.service;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.popup.dto.resonse.GetPopUpStoreDetailResponse;
import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.popup.repository.PopUpStoreRepository;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.global.exception.ErrorCode;
import com.application.poppool.global.exception.NotFoundException;
import com.application.poppool.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopUpStoreService {

    private final UserRepository userRepository;
    private final PopUpStoreRepository popUpStoreRepository;


    @Transactional(readOnly = true)
    public GetPopUpStoreDetailResponse getPopUpStoreDetail(String userId, Long popUpStoreId) {

        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        boolean isLogin = false;

        if (SecurityUtils.isAuthenticated()) {
            isLogin = true;
        }

        PopUpStoreEntity popUpStore = popUpStoreRepository.findById(popUpStoreId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DATA_NOT_FOUND));


        // 댓글 조회 로직
        List<CommentEntity> comments = popUpStore.getComments();

        // 댓글 좋아요 여부 확인
        List<GetPopUpStoreDetailResponse.Comment> commentsWithLikeStatus = comments.stream()
                .map(comment -> new CommentWithLikeStatusDto(comment, commentService.isCommentLikedByUser(user, comment)))
                .collect(Collectors.toList());


        return GetPopUpStoreDetailResponse.builder()
                .name(popUpStore.getName())
                .desc(popUpStore.getDesc())
                .startDate(popUpStore.getStartDate())
                .endDate(popUpStore.getEndDate())
                .address(popUpStore.getAddress())
                .isLogin(isLogin)
                .build();

    }

}
