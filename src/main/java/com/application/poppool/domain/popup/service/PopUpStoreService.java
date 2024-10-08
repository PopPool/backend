package com.application.poppool.domain.popup.service;

import com.application.poppool.domain.category.enums.Category;
import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.comment.enums.CommentType;
import com.application.poppool.domain.comment.service.CommentService;
import com.application.poppool.domain.image.entity.PopUpStoreImageEntity;
import com.application.poppool.domain.popup.dto.resonse.GetClosedPopUpStoreListResponse;
import com.application.poppool.domain.popup.dto.resonse.GetOpenPopUpStoreListResponse;
import com.application.poppool.domain.popup.dto.resonse.GetPopUpStoreDetailResponse;
import com.application.poppool.domain.popup.dto.resonse.GetPopUpStoreDirectionResponse;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
     *
     * @param userId
     * @param popUpStoreId
     * @return
     */
    @Transactional
    public GetPopUpStoreDetailResponse getPopUpStoreDetail(String userId, CommentType commentType, Long popUpStoreId) {

        PopUpStoreEntity popUpStore = popUpStoreRepository.findById(popUpStoreId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POPUP_STORE_NOT_FOUND));

        /** 로그인 여부 체크 */
        boolean loginYn = false;

        /** 찜 여부 체크 */
        boolean bookmarkYn = false;
        
        /** 팝업 스토어 이미지 리스트 조회 */ 
        List<PopUpStoreImageEntity> popUpStoreImageEntityList = popUpStore.getImages(); // 하나의 팝업 스토어 이므로 n+1 문제 발생하지 않음

        List<GetPopUpStoreDetailResponse.PopUpStoreImage> popUpStoreImageList = popUpStoreImageEntityList.stream()
                .map(popUpStoreImageEntity -> GetPopUpStoreDetailResponse.PopUpStoreImage.builder()
                        .id(popUpStoreImageEntity.getId())
                        .imageUrl(popUpStoreImageEntity.getUrl())
                        .build())
                .toList();

        /** 댓글 조회 */
        List<CommentEntity> comments = commentService.getPopUpStoreComments(userId, commentType, popUpStoreId);
        List<GetPopUpStoreDetailResponse.Comment> commentList = new ArrayList<>();

        /** 로그인 유저 */
        if (SecurityUtils.isAuthenticated()) {
            loginYn = true;

            UserEntity user = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

            UserPopUpStoreViewEntity userPopUpStoreView = userPopUpStoreViewRepository.findByUserAndPopUpStore(user, popUpStore)
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
            bookmarkYn = bookMarkPopUpStoreRepository.existsByUserAndPopUpStore(user, popUpStore);

            /** Entity -> Dto, 댓글 좋아요(도움돼요) 여부 확인 , 좋아요 수 */
            commentList = comments.stream().map(comment -> GetPopUpStoreDetailResponse.Comment.builder()
                            .nickname(comment.getUser().getNickname())
                            .instagramId(comment.getUser().getInstagramId())
                            .profileImageUrl(comment.getUser().getProfileImageUrl())
                            .content(comment.getContent())
                            .likeYn(commentService.isCommentLikedByUser(user, comment))
                            .likeCount(comment.getLikeCount())
                            .createDateTime(comment.getCreateDateTime())
                            .build())
                    .toList();

            /** 유저 팝업스토어 뷰 엔티티  조회 시간 업데이트 및 조회 수 + 1 */
            userPopUpStoreView.updateViewedAt(LocalDateTime.now());
            userPopUpStoreView.incrementViewCount();

        }

        /** 비로그인 유저 */
        else {
            /** Entity -> Dto, 댓글 좋아요(도움돼요) 여부 확인 , 좋아요 수 */
            commentList = comments.stream().map(comment -> GetPopUpStoreDetailResponse.Comment.builder()
                            .nickname(comment.getUser().getNickname())
                            .instagramId(comment.getUser().getInstagramId())
                            .profileImageUrl(comment.getUser().getProfileImageUrl())
                            .content(comment.getContent())
                            .likeYn(false)
                            .likeCount(comment.getLikeCount())
                            .createDateTime(comment.getCreateDateTime())
                            .build())
                    .toList();
        }

        /** 비슷한 팝업 리스트 조회 */
        List<GetPopUpStoreDetailResponse.PopUpStore> similarPopUpStoreList = popUpStoreRepository
                .getSimilarPopUpStoreList(popUpStoreId, popUpStore.getCategory());

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

        return GetPopUpStoreDetailResponse.builder()
                .name(popUpStore.getName())
                .desc(popUpStore.getDesc())
                .startDate(popUpStore.getStartDate())
                .endDate(popUpStore.getEndDate())
                .address(popUpStore.getAddress())
                .commentCount(popUpStore.getCommentCount())
                .bookmarkYn(bookmarkYn)
                .loginYn(loginYn)
                .mainImageUrl(popUpStore.getMainImageUrl())
                .imageList(popUpStoreImageList)
                .commentList(commentList)
                .similarPopUpStoreList(similarPopUpStoreList)
                .build();

    }

    /**
     * 진행 중(오픈)인 팝업 리스트 조회
     *
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public GetOpenPopUpStoreListResponse getOpenPopUpStoreList(List<Category> categories, Pageable pageable) {
        List<GetOpenPopUpStoreListResponse.PopUpStore> openPopUpStoreList = popUpStoreRepository.getOpenPopUpStoreList(categories, pageable);

        // 오픈 팝업 전체 데이터 수
        long totalElements = popUpStoreRepository.countOpenPopUpStores(categories);

        // 오픈 팝업 페이지 수
        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());

        return GetOpenPopUpStoreListResponse.builder()
                .openPopUpStoreList(openPopUpStoreList)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }


    /**
     * 종료 팝업 리스트 조회
     *
     * @param categories
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public GetClosedPopUpStoreListResponse getClosedPopUpStoreList(List<Category> categories, Pageable pageable) {
        List<GetClosedPopUpStoreListResponse.PopUpStore> closedPopUpStoreList = popUpStoreRepository.getClosedPopUpStoreList(categories, pageable);

        // 종료 팝업 전체 데이터 수
        long totalElements = popUpStoreRepository.countOpenPopUpStores(categories);

        // 종료 팝업 페이지 수
        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());

        return GetClosedPopUpStoreListResponse.builder()
                .closedPopUpStoreList(closedPopUpStoreList)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }

    /**
     * 팝업 스토어 찾아가는 길
     *
     * @param popUpStoreId
     * @return
     */
    @Transactional(readOnly = true)
    public GetPopUpStoreDirectionResponse getPopUpStoreDirection(Long popUpStoreId) {
        return popUpStoreRepository.getPopUpStoreDirection(popUpStoreId);
    }

}
