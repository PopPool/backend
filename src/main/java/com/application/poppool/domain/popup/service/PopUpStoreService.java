package com.application.poppool.domain.popup.service;

import com.application.poppool.domain.bookmark.repository.BookmarkPopUpStoreRepository;
import com.application.poppool.domain.comment.dto.response.GetCommentsResponse;
import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.comment.enums.CommentType;
import com.application.poppool.domain.comment.repository.CommentRepository;
import com.application.poppool.domain.comment.service.CommentService;
import com.application.poppool.domain.image.entity.PopUpStoreImageEntity;
import com.application.poppool.domain.popup.dto.resonse.*;
import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.popup.repository.PopUpStoreRepository;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.UserPopUpStoreViewEntity;
import com.application.poppool.domain.user.repository.UserPopUpStoreViewRepository;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.global.enums.PopUpSortCode;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PopUpStoreService {

    private final UserRepository userRepository;
    private final PopUpStoreRepository popUpStoreRepository;
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final UserPopUpStoreViewRepository userPopUpStoreViewRepository;
    private final BookmarkPopUpStoreRepository bookmarkPopUpStoreRepository;


    /**
     * 팝업 상세 조회
     *
     * @param userId
     * @param popUpStoreId
     * @return
     */
    @Transactional
    public GetPopUpStoreDetailResponse getPopUpStoreDetail(String userId, CommentType commentType, Long popUpStoreId, boolean viewCountYn) {

        PopUpStoreEntity popUpStore = popUpStoreRepository.findById(popUpStoreId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POPUP_STORE_NOT_FOUND));

        /** 로그인 여부 체크 */
        boolean loginYn = false;

        /** 찜 여부 체크 */
        boolean bookmarkYn = false;
        
        /** 이 팝업에 코멘트 작성했는지에 대한 여부 */
        boolean hasCommented = false;
        
        /** 팝업 스토어 이미지 리스트 조회 */ 
        List<PopUpStoreImageEntity> popUpStoreImageEntityList = popUpStore.getImages(); // 하나의 팝업 스토어 이므로 n+1 문제 발생하지 않음

        List<GetPopUpStoreDetailResponse.PopUpStoreImage> popUpStoreImageList = popUpStoreImageEntityList.stream()
                .map(popUpStoreImageEntity -> GetPopUpStoreDetailResponse.PopUpStoreImage.builder()
                        .id(popUpStoreImageEntity.getId())
                        .imageUrl(popUpStoreImageEntity.getUrl())
                        .build())
                .toList();

        /** 코멘트 조회 */
        List<CommentEntity> commentEntities = commentRepository.getPopUpStoreComments(userId, commentType, popUpStoreId);
        List<GetCommentsResponse.Comment> commentList = new ArrayList<>();

        System.out.println("aaaa");
        for (CommentEntity c : commentEntities) {
            System.out.println(c.getUser().getUserId());
        }
        System.out.println("bbbb");
        /** 로그인 유저 */
        if (SecurityUtils.isAuthenticated()) {
            loginYn = true;

            UserEntity user = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));


            /** 찜 여부 체크 */
            bookmarkYn = bookmarkPopUpStoreRepository.existsByUserAndPopUpStore(user, popUpStore);

            if (viewCountYn) { // 신규 진입 시에만 조회 수 증가
                long bookmarkCount;
                if (bookmarkYn == true) {
                    bookmarkCount = 1L;
                } else {
                    bookmarkCount = 0L;
                }

                UserPopUpStoreViewEntity userPopUpStoreView = userPopUpStoreViewRepository.findByUserAndPopUpStore(user, popUpStore)
                        .orElseGet(() -> {
                            UserPopUpStoreViewEntity newUserPopUpStoreView = UserPopUpStoreViewEntity.builder()
                                    .user(user)
                                    .popUpStore(popUpStore)
                                    .viewedAt(LocalDateTime.now())
                                    .viewCount(0)
                                    .commentCount(0)
                                    .bookmarkCount(bookmarkCount)
                                    .build();
                            return userPopUpStoreViewRepository.save(newUserPopUpStoreView);
                        });

                /** 유저 팝업스토어 뷰 엔티티  조회 시간 업데이트 및 조회 수 + 1 */
                userPopUpStoreView.updateViewedAt(LocalDateTime.now());
                userPopUpStoreView.incrementViewCount();

            }

            /** 해당 팝업에 코멘트 달았는지 여부 */
            hasCommented = commentRepository.existsByUserAndPopUpStore(user, popUpStore);

            /** Entity -> Dto, 코멘트 좋아요(도움돼요) 여부 확인 , 좋아요 수 */
            commentList = commentEntityToDto(commentEntities, user);

        }

        /** 비로그인 유저 */
        else {
            /** Entity -> Dto, 코멘트 좋아요(도움돼요) 여부 확인 , 좋아요 수 */
            commentList = commentEntityToDto(commentEntities, null);
        }

        /** 비슷한 팝업 리스트 조회 */
        List<GetPopUpStoreDetailResponse.PopUpStore> similarPopUpStoreList = popUpStoreRepository
                .getSimilarPopUpStoreList(popUpStoreId, popUpStore.getCategory().getCategoryId());

        /** 동시성 이슈 방지를 위한 retry 및 예외 처리*/
        int retryCount = 0;
        int maxRetryCount = 3; // 최대 재시도 횟수

        if (viewCountYn) {  // 신규 진입 시에만 조회 수 증가
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
        }

        return GetPopUpStoreDetailResponse.builder()
                .name(popUpStore.getName())
                .desc(popUpStore.getDesc())
                .startDate(popUpStore.getStartDate())
                .endDate(popUpStore.getEndDate())
                .mainImageUrl(popUpStore.getMainImageUrl())
                .address(popUpStore.getAddress())
                .commentCount(popUpStore.getCommentCount())
                .bookmarkYn(bookmarkYn)
                .loginYn(loginYn)
                .hasCommented(hasCommented)
                .imageList(popUpStoreImageList)
                .commentList(commentList)
                .similarPopUpStoreList(similarPopUpStoreList)
                .build();

    }

    /**
     * 팝업스토어 코멘트 전체 보기
     * @param userId
     * @param commentType
     * @param popUpStoreId
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public GetAllPopUpStoreCommentsResponse getAllPopUpStoreComments(String userId, CommentType commentType, Long popUpStoreId, Pageable pageable) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        List<CommentEntity> commentEntities = commentRepository.getAllPopUpStoreComments(userId, commentType, popUpStoreId, pageable);
        List<GetCommentsResponse.Comment> commentList = commentEntityToDto(commentEntities, user);

        return GetAllPopUpStoreCommentsResponse.builder()
                .commentList(commentList)
                .build();
    }

    /**
     * 코멘트 Entity -> DTO 변환 공통 메서드
     * @param commentEntities
     * @param user
     * @return
     */
    public List<GetCommentsResponse.Comment> commentEntityToDto(List<CommentEntity> commentEntities, UserEntity user) {
        return commentEntities.stream().map(comment -> GetCommentsResponse.Comment.builder()
                        .commentId(comment.getId())
                        .creator(comment.getUser().getUserId())
                        .nickname(comment.getUser().getNickname())
                        .instagramId(comment.getUser().getInstagramId())
                        .profileImageUrl(comment.getUser().getProfileImageUrl())
                        .content(comment.getContent())
                        .likeYn(user != null ? commentService.isCommentLikedByUser(user, comment) : false)
                        .likeCount(comment.getLikeCount())
                        .myCommentYn(user != null ? comment.getUser().getUserId().equals(user.getUserId()) : false)
                        .createDateTime(comment.getCreateDateTime())
                        .commentImageList(
                                comment.getImages() // 코멘트에 해당하는 이미지 리스트
                                        .stream()
                                        .map(commentImage -> GetCommentsResponse.CommentImage.builder()
                                                .id(commentImage.getId())
                                                .imageUrl(commentImage.getImageUrl())
                                                .build())
                                        .collect(Collectors.toList())
                        )
                        .build())
                .toList();
    }


    /**
     * 진행 중(오픈)인 팝업 리스트 조회
     *
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public GetOpenPopUpStoreListResponse getOpenPopUpStoreList(String userId, List<Integer> categories, List<PopUpSortCode> sortCodes, Pageable pageable) {

        /** 로그인 여부 체크 */
        boolean loginYn = false;
        if (SecurityUtils.isAuthenticated()) {
            loginYn = true;
        }

        List<GetOpenPopUpStoreListResponse.PopUpStore> openPopUpStoreList = popUpStoreRepository.getOpenPopUpStoreList(userId, categories, sortCodes, pageable);

        // 오픈 팝업 전체 데이터 수
        long totalElements = popUpStoreRepository.countOpenPopUpStores(categories);

        // 오픈 팝업 페이지 수
        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());

        return GetOpenPopUpStoreListResponse.builder()
                .openPopUpStoreList(openPopUpStoreList)
                .loginYn(loginYn)
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
    public GetClosedPopUpStoreListResponse getClosedPopUpStoreList(String userId, List<Integer> categories, List<PopUpSortCode> sortCodes, Pageable pageable) {

        /** 로그인 여부 체크 */
        boolean loginYn = false;
        if (SecurityUtils.isAuthenticated()) {
            loginYn = true;
        }

        List<GetClosedPopUpStoreListResponse.PopUpStore> closedPopUpStoreList = popUpStoreRepository.getClosedPopUpStoreList(userId, categories, sortCodes, pageable);

        // 종료 팝업 전체 데이터 수
        long totalElements = popUpStoreRepository.countClosedPopUpStores(categories);

        // 종료 팝업 페이지 수
        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());

        return GetClosedPopUpStoreListResponse.builder()
                .closedPopUpStoreList(closedPopUpStoreList)
                .loginYn(loginYn)
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
