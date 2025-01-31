package com.application.poppool.domain.comment.service;

import com.application.poppool.domain.comment.dto.request.CreateCommentRequest;
import com.application.poppool.domain.comment.dto.request.UpdateCommentRequest;
import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.comment.repository.CommentRepository;
import com.application.poppool.domain.image.entity.CommentImageEntity;
import com.application.poppool.domain.image.repository.CommentImageRepository;
import com.application.poppool.domain.like.service.LikeService;
import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.popup.repository.PopUpStoreRepository;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.UserPopUpStoreViewEntity;
import com.application.poppool.domain.user.repository.UserPopUpStoreViewRepository;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.global.exception.BadRequestException;
import com.application.poppool.global.exception.ConcurrencyException;
import com.application.poppool.global.exception.ErrorCode;
import com.application.poppool.global.exception.NotFoundException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final LikeService likeService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PopUpStoreRepository popUpStoreRepository;
    private final CommentImageRepository commentImageRepository;
    private final UserPopUpStoreViewRepository userPopUpStoreViewRepository;


    /**
     * 코멘트 작성
     * @param userId
     * @param request
     */
    @Transactional
    public void createComment(String userId, CreateCommentRequest request) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        PopUpStoreEntity popUpStore = popUpStoreRepository.findById(request.getPopUpStoreId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.POPUP_STORE_NOT_FOUND));

        UserPopUpStoreViewEntity userPopUpStoreView = userPopUpStoreViewRepository.findByUserAndPopUpStore(user, popUpStore)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DATA_NOT_FOUND));

        /** 코멘트 생성 및 저장 */
        CommentEntity comment = CommentEntity.builder()
                .user(user)
                .popUpStore(popUpStore)
                .content(request.getContent())
                .commentType(request.getCommentType())
                .build();

        /** 코멘트 이미지 엔티티 생성 및 저장 */
        for (String imageUrl : request.getImageUrlList()) {
            CommentImageEntity commentImage = CommentImageEntity.builder()
                    .comment(comment)
                    .imageUrl(imageUrl)
                    .build();
            // 연관관계 편의 메소드(양방향으로 값 셋팅)
            comment.addImage(commentImage);
        }

        // 코멘트 저장
        commentRepository.save(comment);

        /** 동시성 이슈 방지를 위한 retry 및 예외 처리*/
        int retryCount = 0;
        int maxRetryCount = 3; // 최대 재시도 횟수

        while (retryCount < maxRetryCount) {
            try {
                // 팝업스토어의 코멘트 수 + 1
                popUpStore.incrementCommentCount();
                break;
            } catch (OptimisticLockException e) {
                retryCount++;
                if (retryCount >= maxRetryCount) {
                    throw new ConcurrencyException(ErrorCode.CONCURRENCY_ERROR);
                }
            }
        }

        // 팝업 스토어 뷰 코멘트 수 + 1
        userPopUpStoreView.incrementCommentCount();

    }

    /**
     * 코멘트 수정
     * @param userId
     * @param request
     */
    @Transactional
    public void updateComment(String userId, UpdateCommentRequest request) {

        CommentEntity comment = commentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        // 코멘트 내용(텍스트) 수정
        comment.updateComment(request.getContent());


        // 코멘트 이미지 수정
        for (UpdateCommentRequest.ImageAction imageAction : request.getImageUrlList()) {
            switch (imageAction.getActionType()) {
                case ADD:
                    // 이미지 추가
                    CommentImageEntity commentImageToAdd = CommentImageEntity.builder()
                            .imageUrl(imageAction.getImageUrl())
                            .comment(comment)
                            .build();
                    commentImageRepository.save(commentImageToAdd);
                    break;
                case DELETE:
                    // 이미지 삭제
                    CommentImageEntity commentImageToDelete = commentImageRepository.findById(imageAction.getImageId())
                            .orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_IMAGE_NOT_FOUND));
                    commentImageRepository.delete(commentImageToDelete);
                    break;
                case KEEP:
                    // 아무 작업하지 않음
                    break;
            }
        }
    }

    /**
     * 코멘트 삭제
     * @param userId
     * @param popUpStoreId
     * @param commentId
     */
    @Transactional
    public void deleteComment(String userId, Long popUpStoreId, Long commentId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        PopUpStoreEntity popUpStore = popUpStoreRepository.findById(popUpStoreId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POPUP_STORE_NOT_FOUND));

        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        UserPopUpStoreViewEntity userPopUpStoreView = userPopUpStoreViewRepository.findByUserAndPopUpStore(user, popUpStore)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DATA_NOT_FOUND));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new BadRequestException(ErrorCode.NOT_MY_COMMENT);
        }

        /** 코멘트 삭제, CASCADE 설정으로 코멘트와 관련된 이미지들도 모두 삭제됨 */
        commentRepository.delete(comment);

        /** 동시성 이슈 방지를 위한 retry 및 예외 처리*/
        int retryCount = 0;
        int maxRetryCount = 3; // 최대 재시도 횟수

        while (retryCount < maxRetryCount) {
            try {
                // 팝업 스토어 코멘트 수 - 1
                popUpStore.decrementCommentCount();
                break;
            } catch (OptimisticLockException e) {
                retryCount++;
                if (retryCount >= maxRetryCount) {
                    throw new ConcurrencyException(ErrorCode.CONCURRENCY_ERROR);
                }
            }
        }
        // 팝업 스토어 뷰 코멘트 수 - 1
        userPopUpStoreView.decrementCommentCount();
    }

    @Transactional(readOnly = true)
    public boolean isCommentLikedByUser(UserEntity user, CommentEntity comment) {
        return likeService.existsByUserAndComment(user, comment);
    }

    @Transactional(readOnly = true)
    public long getLikeCount(CommentEntity comment) {
        return likeService.countByComment(comment);
    }

}
