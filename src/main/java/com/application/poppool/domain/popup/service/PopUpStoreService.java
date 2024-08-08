package com.application.poppool.domain.popup.service;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.comment.service.CommentService;
import com.application.poppool.domain.popup.dto.resonse.GetPopUpStoreDetailResponse;
import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.popup.repository.PopUpStoreRepository;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.UserPopUpStoreViewEntity;
import com.application.poppool.domain.user.repository.BookMarkPopUpStoreRepository;
import com.application.poppool.domain.user.repository.UserPopUpStoreViewRepository;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.global.exception.ErrorCode;
import com.application.poppool.global.exception.NotFoundException;
import com.application.poppool.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PopUpStoreService {

    private final UserRepository userRepository;
    private final PopUpStoreRepository popUpStoreRepository;
    private final CommentService commentService;
    private final UserPopUpStoreViewRepository userPopUpStoreViewRepository;
    private final BookMarkPopUpStoreRepository bookMarkPopUpStoreRepository;


    /**
     * 팝업 상세 조회
     * @param userId
     * @param popUpStoreId
     * @return
     */
    @Transactional(readOnly = true)
    public GetPopUpStoreDetailResponse getPopUpStoreDetail(String userId, Long popUpStoreId) {

        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        PopUpStoreEntity popUpStore = popUpStoreRepository.findById(popUpStoreId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POPUP_STORE_NOT_FOUND));

        UserPopUpStoreViewEntity userPopUpStoreView = userPopUpStoreViewRepository.findByUserAndPopUpStore(user,popUpStore)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DATA_NOT_FOUND));


        /** 찜 여부 체크 */
        boolean isBookmarked = bookMarkPopUpStoreRepository.existsByUserAndPopUpStore(user, popUpStore);

        /** 로그인 여부 체크 */
        boolean isLogin = false;
        if (SecurityUtils.isAuthenticated()) {
            isLogin = true;
        }

        /** 댓글 조회 */
        List<CommentEntity> comments = commentService.getPopUpStoreComments(userId, popUpStoreId);


        /** Entity -> Dto, 댓글 좋아요(도움돼요) 여부 확인 , 좋아요 수 */
        List<GetPopUpStoreDetailResponse.Comment> commentList = comments.stream()
                .map(comment -> GetPopUpStoreDetailResponse.Comment.builder()
                        .nickname(comment.getUser().getNickname())
                        .profileImageUrl(comment.getUser().getProfileImageUrl())
                        .content(comment.getContent())
                        .isLiked(commentService.isCommentLikedByUser(user, comment))
                        .likeCount(comment.getLikeCount())
                        .createDateTime(comment.getCreateDateTime())
                        .build())
                .toList();


        /** 팝업스토어 조회 수 + 1*/
        popUpStore.incrementViewCount();

        /** 유저 팝업스토어 뷰 엔티티  조회 시간 업데이트 및 조회 수 + 1 */
        userPopUpStoreView.updateViewedAt(LocalDateTime.now());
        userPopUpStoreView.incrementViewCount();

        return GetPopUpStoreDetailResponse.builder()
                .name(popUpStore.getName())
                .desc(popUpStore.getDesc())
                .startDate(popUpStore.getStartDate())
                .endDate(popUpStore.getEndDate())
                .address(popUpStore.getAddress())
                .isBookmarked(isBookmarked)
                .isLogin(isLogin)
                .commentList(commentList)
                .build();

    }

}
