package com.application.poppool.domain.popup.service;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.comment.enums.CommentType;
import com.application.poppool.domain.comment.service.CommentService;
import com.application.poppool.domain.popup.dto.resonse.GetAllPopUpListResponse;
import com.application.poppool.domain.popup.dto.resonse.GetPopUpStoreDetailResponse;
import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.popup.repository.PopUpStoreRepository;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.UserPopUpStoreViewEntity;
import com.application.poppool.domain.user.repository.BookMarkPopUpStoreRepository;
import com.application.poppool.domain.user.repository.UserPopUpStoreViewRepository;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.global.exception.ConcurrencyException;
import com.application.poppool.global.exception.ErrorCode;
import com.application.poppool.global.exception.NotFoundException;
import com.application.poppool.global.utils.SecurityUtils;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Transactional
    public GetPopUpStoreDetailResponse getPopUpStoreDetail(String userId, CommentType commentType, Long popUpStoreId) {

        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        PopUpStoreEntity popUpStore = popUpStoreRepository.findById(popUpStoreId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POPUP_STORE_NOT_FOUND));

        UserPopUpStoreViewEntity userPopUpStoreView = userPopUpStoreViewRepository.findByUserAndPopUpStore(user,popUpStore)
                .orElseGet(() -> {
                    UserPopUpStoreViewEntity newUserPopUpStoreView = UserPopUpStoreViewEntity.builder()
                            .user(user)
                            .popUpStore(popUpStore)
                            .viewedAt(LocalDateTime.now())
                            .viewCount(0)
                            .commentCount(0)
                            .bookmarkCount(0)
                            .build();
                    return userPopUpStoreViewRepository.save(newUserPopUpStoreView);
                });

        /** 찜 여부 체크 */
        boolean bookmarkYn = bookMarkPopUpStoreRepository.existsByUserAndPopUpStore(user, popUpStore);

        /** 로그인 여부 체크 */
        boolean loginYn = false;
        if (SecurityUtils.isAuthenticated()) {
            loginYn = true;
        }

        /** 댓글 조회 */
        List<CommentEntity> comments = commentService.getPopUpStoreComments(userId, commentType, popUpStoreId);

        /** Entity -> Dto, 댓글 좋아요(도움돼요) 여부 확인 , 좋아요 수 */
        List<GetPopUpStoreDetailResponse.Comment> commentList = comments.stream()
                .map(comment -> GetPopUpStoreDetailResponse.Comment.builder()
                        .nickname(comment.getUser().getNickname())
                        .instagramId(comment.getUser().getInstagramId())
                        .profileImageUrl(comment.getUser().getProfileImageUrl())
                        .content(comment.getContent())
                        .likeYnd(commentService.isCommentLikedByUser(user, comment))
                        .likeCount(comment.getLikeCount())
                        .createDateTime(comment.getCreateDateTime())
                        .build())
                .toList();


        /** 동시성 이슈 방지를 위한 retry 및 예외 처리*/
        int retryCount = 0;
        int maxRetryCount = 3; // 최대 재시도 횟수

        while (retryCount < maxRetryCount) {
            try {
                /** 팝업스토어 조회 수 + 1*/
                popUpStore.incrementViewCount();
                break;
            } catch (OptimisticLockException e) {
                retryCount++;
                if (retryCount >= maxRetryCount) {
                    throw new ConcurrencyException(ErrorCode.CONCURRENCY_ERROR);
                }
            }
        }

        /** 유저 팝업스토어 뷰 엔티티  조회 시간 업데이트 및 조회 수 + 1 */
        userPopUpStoreView.updateViewedAt(LocalDateTime.now());
        userPopUpStoreView.incrementViewCount();

        return GetPopUpStoreDetailResponse.builder()
                .name(popUpStore.getName())
                .desc(popUpStore.getDesc())
                .startDate(popUpStore.getStartDate())
                .endDate(popUpStore.getEndDate())
                .address(popUpStore.getAddress())
                .commentCount(popUpStore.getCommentCount())
                .bookmarkYn(bookmarkYn)
                .loginYn(loginYn)
                .commentList(commentList)
                .build();

    }

    /**
     * 전체 팝업 리스트 조회
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public GetAllPopUpListResponse getAllPopUpList(Pageable pageable) {
        Page<PopUpStoreEntity> popUpStorePage = popUpStoreRepository.findAll(pageable);

        List<GetAllPopUpListResponse.PopUpStore> popUpStoreList = popUpStorePage.stream()
                .map(popUpStore -> GetAllPopUpListResponse.PopUpStore.builder()
                        .id(popUpStore.getId())
                        .category(popUpStore.getCategory())
                        .name(popUpStore.getName())
                        .address(popUpStore.getAddress())
                        .mainImageUrl(popUpStore.getMainImageUrl())
                        .startDate(popUpStore.getStartDate())
                        .endDate(popUpStore.getEndDate())
                        .build())
                .toList();

        return GetAllPopUpListResponse.builder().popUpStoreList(popUpStoreList).build();
    }

}
