package com.application.poppool.domain.user.service;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.comment.repository.CommentRepository;
import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.token.service.BlackListTokenService;
import com.application.poppool.domain.token.service.RefreshTokenService;
import com.application.poppool.domain.user.dto.request.CheckedSurveyListRequest;
import com.application.poppool.domain.user.dto.response.*;
import com.application.poppool.domain.user.entity.BlockedUserEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.WithDrawalSurveyEntity;
import com.application.poppool.domain.user.repository.*;
import com.application.poppool.global.exception.BadRequestException;
import com.application.poppool.global.exception.ErrorCode;
import com.application.poppool.global.exception.NotFoundException;
import com.application.poppool.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final WithDrawlRepository withDrawlSurveyRepository;
    private final BlockedUserRepository blockedUserRepository;
    private final BlockedUserRepositoryCustom blockedUserRepositoryCustom;
    private final UserPopUpStoreViewRepository userPopUpStoreViewRepository;
    private final BlackListTokenService blackListTokenService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;


    /**
     * 마이페이지 조회
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public GetMyPageResponse getMyPage(String userId) {

        UserEntity user = this.findUserByUserId(userId);


        /***
         * 마이페이지 조회 시, 코멘트 단 팝업 스토어 정보도 넘겨줌
         */
        List<GetMyPageResponse.PopUpInfo> popUpInfoList = user.getComments().stream()
                .map(comment -> comment.getPopUpStore())
                .map(popUpStore -> GetMyPageResponse.PopUpInfo.builder()
                        .popUpStoreId(popUpStore.getId())
                        .popUpStoreName(popUpStore.getName())
                        .build())
                .toList();

        return GetMyPageResponse.builder()
                .nickname(user.getNickname())
                .instagramId(user.getInstagramId())
                .popUpInfoList(popUpInfoList)
                .build();
    }

    /**
     * 내가 쓴 일반 코멘트 조회
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public GetMyCommentResponse getMyCommentList(String userId, Pageable pageable) {
        UserEntity user = this.findUserByUserId(userId);

        // 회원의 코멘트 조회
        Page<CommentEntity> myCommentList = commentRepository.findByUser(user, pageable);

        // Entity to Dto
        List<GetMyCommentResponse.MyCommentInfo> myCommentInfoList = myCommentList.stream()
                .map(myComment -> GetMyCommentResponse.MyCommentInfo.builder()
                        .commentId(myComment.getId())
                        .content(myComment.getContent())
                        .image(myComment.getImage())
                        .likeCount(myComment.getLikeCount())
                        .build())
                .toList();

        return GetMyCommentResponse.builder()
                .myCommentList(myCommentInfoList)
                .totalPages(myCommentList.getTotalPages())
                .totalElements(myCommentList.getTotalElements())
                .build();
    }

    /**
     * 내가 코멘트 단 팝업스토어 전체 조회
     */
    @Transactional(readOnly = true)
    public GetMyCommentedPopUpStoreListResponse getMyCommentedPopUpStoreList(String userId, Pageable pageable) {
        UserEntity user = this.findUserByUserId(userId);

        // 회원이 코멘트 단 팝업 스토어 전체 조회
        Page<PopUpStoreEntity> popUpStores = commentRepository.findPopUpStoresByUserComment(userId, pageable);


        // Entity to Dto
        List<GetMyCommentedPopUpStoreListResponse.PopUpInfo> popUpInfoList = popUpStores.stream()
                .map(popUpStore -> GetMyCommentedPopUpStoreListResponse.PopUpInfo.builder()
                        .popUpStoreId(popUpStore.getId())
                        .popUpStoreName(popUpStore.getName())
                        .desc(popUpStore.getDesc())
                        .startDate(popUpStore.getStartDate())
                        .endDate(popUpStore.getEndDate())
                        .address(popUpStore.getAddress())
                        .closedYn(popUpStore.getClosedYn())
                        .build())
                .toList();

        return GetMyCommentedPopUpStoreListResponse.builder()
                .popUpInfoList(popUpInfoList)
                .totalPages(popUpStores.getTotalPages())
                .totalElements(popUpStores.getTotalElements())
                .build();
    }

    /**
     * 최근 본 팝업스토어 조회
     * @param userId
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public GetMyRecentViewPopUpStoreListResponse getMyRecentViewPopUpStoreList(String userId, Pageable pageable) {
        UserEntity user = this.findUserByUserId(userId);

        // 최근 본 팝업스토어 조회
        Page<PopUpStoreEntity> recentViewPopUpStores = userPopUpStoreViewRepository.findRecentViewPopUpStoresByUserId(userId, pageable);

        List<GetMyRecentViewPopUpStoreListResponse.PopUpInfo> popUpInfoList = recentViewPopUpStores.stream()
                .map(popUpStoreEntity -> GetMyRecentViewPopUpStoreListResponse.PopUpInfo.builder()
                        .popUpStoreId(popUpStoreEntity.getId())
                        .popUpStoreName(popUpStoreEntity.getName())
                        .desc(popUpStoreEntity.getDesc())
                        .startDate(popUpStoreEntity.getStartDate())
                        .endDate(popUpStoreEntity.getEndDate())
                        .address(popUpStoreEntity.getAddress())
                        .closedYn(popUpStoreEntity.getClosedYn())
                        .build())
                .toList();

        return GetMyRecentViewPopUpStoreListResponse.builder()
                .popUpInfoList(popUpInfoList)
                .totalPages(recentViewPopUpStores.getTotalPages())
                .totalElements(recentViewPopUpStores.getTotalElements())
                .build();
    }

    /**
     * 차단한 사용자 목록 조회
     * @param userId
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public GetBlockedUserListResponse getBlockedUserList(String userId, Pageable pageable) {
        UserEntity user = this.findUserByUserId(userId);

        Page<GetBlockedUserListResponse.BlockedUserInfo> blockedUserInfoPage = blockedUserRepositoryCustom.getBlockedUserList(userId, pageable);

        List<GetBlockedUserListResponse.BlockedUserInfo> blockedUserInfoList = blockedUserInfoPage.stream()
                .map(blockedUserInfo -> GetBlockedUserListResponse.BlockedUserInfo.builder()
                        .userId(blockedUserInfo.getUserId())
                        .profileImage(blockedUserInfo.getProfileImage())
                        .nickname(blockedUserInfo.getNickname())
                        .instagramId(blockedUserInfo.getInstagramId())
                        .build())
                .toList();

        return GetBlockedUserListResponse.builder()
                .blockedUserInfoList(blockedUserInfoList)
                .totalPages(blockedUserInfoPage.getTotalPages())
                .totalElements(blockedUserInfoPage.getTotalElements())
                .build();
    }


    /**
     * 유저(사용자) 차단
     * @param userId // 차단을 한 사용자
     * @param blockedUserId // 차단된 사용자
     */
    @Transactional
    public void blockUser(String userId, String blockedUserId) {
        UserEntity user = this.findUserByUserId(userId);
        UserEntity blocked = this.findUserByUserId(blockedUserId);

        BlockedUserEntity blockedUser = BlockedUserEntity.builder()
                .user(user)
                .blockedUser(blocked)
                .blockedAt(LocalDateTime.now())
                .build();

        // 차단 정보 저장
        blockedUserRepository.save(blockedUser);

    }

    /**
     * 유저(사용자) 차단 해제
     * @param BlockerUserId
     * @param BlockedUserId
     */
    @Transactional
    public void unblockUser(String BlockerUserId, String BlockedUserId) {
        UserEntity blocker = this.findUserByUserId(BlockerUserId);
        UserEntity blocked = this.findUserByUserId(BlockedUserId);

        BlockedUserEntity blockedUser = blockedUserRepository.findByUserAndBlockedUser(blocker, blocked)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        // 차단 해제
        blockedUserRepository.delete(blockedUser);

    }

    /**
     * 회원 탈퇴
     *
     * @param userId
     */
    @Transactional
    public void deleteUser(String userId, CheckedSurveyListRequest request) {
        UserEntity user = this.findUserByUserId(userId);

        // 회원 탈퇴 설문 항목 수 증가
        for (CheckedSurveyListRequest.CheckedSurvey checkedSurvey : request.getCheckedSurveyList()) {
            WithDrawalSurveyEntity surveyEntity = withDrawlSurveyRepository.findById(checkedSurvey.getId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.DATA_NOT_FOUND));

            // survey id로 가져온 엔티티의 실제 survey와 요청으로 넘어온 survey가 같은지 비교
            if (!surveyEntity.getSurvey().equals(checkedSurvey.getSurvey())) {
                throw new BadRequestException(ErrorCode.DATA_VALIDATION_ERROR);
            }
            // survey 해당 숫자 증가
            surveyEntity.incrementCount();
            withDrawlSurveyRepository.save(surveyEntity);
        }

        // 회원 삭제
        userRepository.delete(user);
    }

    /**
     * 회원 탈퇴 설문 항목 조회
     */
    @Transactional(readOnly = true)
    public GetWithDrawlSurveyResponse getWithDrawlSurvey() {
        List<WithDrawalSurveyEntity> surveyEntityList = withDrawlSurveyRepository.findAll();

        List<GetWithDrawlSurveyResponse.Survey> withDrawlSurveyList = surveyEntityList.stream()
                .map(surveyEntity -> GetWithDrawlSurveyResponse.Survey.builder()
                        .id(surveyEntity.getId())
                        .survey(surveyEntity.getSurvey())
                        .build())
                .toList();

        return GetWithDrawlSurveyResponse.builder().withDrawlSurveyList(withDrawlSurveyList).build();
    }

    /**
     * 회원 로그아웃
     *
     * @param accessToken
     * @param expiryDateTime
     */
    @Transactional
    public void logout(String accessToken, LocalDateTime expiryDateTime) {

        // accessToken 블랙리스트에 추가
        blackListTokenService.addTokenToBlackList(accessToken, expiryDateTime);

        // refreshToken DB에서 삭제
        refreshTokenService.deleteRefreshToken(jwtService.getUserId(accessToken));

    }

    /**
     * 유저 엔티티 조회
     * @param userId
     * @return
     */
    public UserEntity findUserByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

}
