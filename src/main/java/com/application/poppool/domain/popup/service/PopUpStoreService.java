package com.application.poppool.domain.popup.service;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.comment.service.CommentService;
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
    private final CommentService commentService;


    @Transactional(readOnly = true)
    public GetPopUpStoreDetailResponse getPopUpStoreDetail(String userId, Long popUpStoreId) {

        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        boolean isLogin = false;

        /** 로그인 여부 체크 */
        if (SecurityUtils.isAuthenticated()) {
            isLogin = true;
        }

        PopUpStoreEntity popUpStore = popUpStoreRepository.findById(popUpStoreId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DATA_NOT_FOUND));


        /** 댓글 조회 */
        List<CommentEntity> comments = commentService.getPopUpStoreComments(popUpStoreId);


        /** Entity -> Dto, 댓글 좋아요(도움돼요) 여부 확인 , 좋아요 수 */
        List<GetPopUpStoreDetailResponse.Comment> commentList = comments.stream()
                .map(comment -> GetPopUpStoreDetailResponse.Comment.builder()
                        .nickname(comment.getUser().getNickname())
                        .profileImageUrl(comment.getUser().getProfileImageUrl())
                        .content(comment.getContent())
                        .isLiked(commentService.isCommentLikedByUser(user, comment))
                        .likeCount(commentService.getLikeCount(comment))
                        .createDateTime(comment.getCreateDateTime())
                        .build())
                .toList();


        return GetPopUpStoreDetailResponse.builder()
                .name(popUpStore.getName())
                .desc(popUpStore.getDesc())
                .startDate(popUpStore.getStartDate())
                .endDate(popUpStore.getEndDate())
                .address(popUpStore.getAddress())
                .isLogin(isLogin)
                .commentList(commentList)
                .build();

    }

}
