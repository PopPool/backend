package com.application.poppool.domain.user.service;

import com.application.poppool.domain.auth.enums.SocialType;
import com.application.poppool.domain.auth.service.apple.AppleAuthFeignClient;
import com.application.poppool.domain.auth.service.apple.AppleAuthService;
import com.application.poppool.domain.auth.service.apple.AppleProperties;
import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.comment.enums.CommentType;
import com.application.poppool.domain.comment.repository.CommentRepository;
import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.popup.repository.PopUpStoreRepository;
import com.application.poppool.domain.token.entity.AppleRefreshTokenEntity;
import com.application.poppool.domain.token.entity.RefreshTokenEntity;
import com.application.poppool.domain.token.repository.AppleRefreshTokenRepository;
import com.application.poppool.domain.token.repository.RefreshTokenRepository;
import com.application.poppool.domain.token.service.RefreshTokenService;
import com.application.poppool.domain.user.dto.request.CheckedSurveyListRequest;
import com.application.poppool.domain.user.dto.response.*;
import com.application.poppool.domain.user.entity.*;
import com.application.poppool.domain.user.enums.Role;
import com.application.poppool.domain.user.repository.*;
import com.application.poppool.global.enums.CommentSortCode;
import com.application.poppool.global.exception.BadRequestException;
import com.application.poppool.global.exception.ConcurrencyException;
import com.application.poppool.global.exception.ErrorCode;
import com.application.poppool.global.exception.NotFoundException;
import com.application.poppool.global.jwt.JwtService;
import com.application.poppool.global.utils.SecurityUtils;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final WithDrawlRepository withDrawlSurveyRepository;
    private final BlockedUserRepository blockedUserRepository;
    private final UserPopUpStoreViewRepository userPopUpStoreViewRepository;
    private final BookMarkPopUpStoreRepository bookMarkPopUpStoreRepository;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final PopUpStoreRepository popUpStoreRepository;
    private final AppleAuthFeignClient appleAuthFeignClient;
    private final AppleProperties appleProperties;
    private final AppleAuthService appleAuthService;
    private final AppleRefreshTokenRepository appleRefreshTokenRepository;


    /**
     * 마이페이지 조회
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public GetMyPageResponse getMyPage(String userId) {

        boolean loginYn = false;
        boolean adminYn = false;
        String nickname = null;
        String profileImageUrl = null;
        String intro = null;
        String instagramId = null;
        List<GetMyPageResponse.MyCommentedPopUpInfo> myCommentedPopUpList = new ArrayList<>();

        // 로그인 유저
        if (SecurityUtils.isAuthenticated()) {
            loginYn = true;
            UserEntity user = this.findUserByUserId(userId);
            adminYn = isAdmin(user.getUserRoles());
            nickname = user.getNickname();
            profileImageUrl = user.getProfileImageUrl();
            intro = user.getIntro();
            instagramId = user.getInstagramId();
            
            /***
             * 마이페이지 조회 시, 코멘트 단 팝업 스토어 정보도 넘겨줌
             */
            // 회원의 코멘트 조회
            myCommentedPopUpList = commentRepository.findMyCommentedPopUpInfo(userId);

        }

        return GetMyPageResponse.builder()
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .intro(intro)
                .instagramId(instagramId)
                .myCommentedPopUpList(myCommentedPopUpList)
                .loginYn(loginYn)
                .adminYn(adminYn)
                .build();
    }

    /**
     * 내가 쓴 일반/인스타 코멘트 조회
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public GetMyCommentResponse getMyCommentList(String userId, CommentType commentType, List<CommentSortCode> sortCodes, Pageable pageable) {
        UserEntity user = this.findUserByUserId(userId);

        // 회원의 코멘트 조회
        List<CommentEntity> myCommentEntityList = commentRepository.findByMyCommentsWithPopUpStore(userId, commentType, sortCodes, pageable);

        // 2. 댓글 리스트를 DTO로 변환
        List<GetMyCommentResponse.MyCommentInfo> myCommentList = myCommentEntityList.stream()
                .map(commentEntity -> {

                    // 3. 팝업스토어 정보 가져오기
                    GetMyCommentResponse.MyCommentedPopUpInfo popUpStoreInfo = GetMyCommentResponse.MyCommentedPopUpInfo.builder()
                            .popUpStoreId(commentEntity.getPopUpStore().getId())
                            .popUpStoreName(commentEntity.getPopUpStore().getName())
                            .mainImageUrl(commentEntity.getPopUpStore().getMainImageUrl())
                            .closeYn(isClosedPopUp(commentEntity.getPopUpStore(), LocalDateTime.now()))  // 종료 여부 설정
                            .build();

                    // 4. MyCommentInfo 객체 생성
                    return GetMyCommentResponse.MyCommentInfo.builder()
                            .commentId(commentEntity.getId())
                            .content(commentEntity.getContent())
                            .likeCount(commentEntity.getLikeCount())
                            .createDateTime(commentEntity.getCreateDateTime())
                            .popUpStoreInfo(popUpStoreInfo)  // 팝업스토어 정보 포함
                            .build();
                })
                .toList();

        // 전체 코멘트 수
        long totalElements = commentRepository.countMyComments(userId, commentType);

        // 전체 페이지 수
        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());

        return GetMyCommentResponse.builder()
                .myCommentList(myCommentList)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }

    /**
     * 팝업 상세 다른 유저의 코멘트 목록 전체 조회
     * @param commenterId
     * @param commentType
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public GetCommenterCommentListResponse getCommenterCommentList(String commenterId, CommentType commentType, Pageable pageable) {
        // 팝업 상세 다른 유저의 코멘트 목록 전체 조회
        List<CommentEntity> commenterCommentEntityList = commentRepository.findCommenterCommentsWithPopUpStore(commenterId, commentType, pageable);

        // 댓글 리스트 dto 매핑
        List<GetCommenterCommentListResponse.Comment> commenterCommentList = commenterCommentEntityList.stream()
                .map(commentEntity -> {

                    // 팝업스토어 정보 가져오기
                    GetCommenterCommentListResponse.CommentedPopUpStore popUpStoreInfo = GetCommenterCommentListResponse.CommentedPopUpStore.builder()
                            .popUpStoreId(commentEntity.getPopUpStore().getId())
                            .popUpStoreName(commentEntity.getPopUpStore().getName())
                            .mainImageUrl(commentEntity.getPopUpStore().getMainImageUrl())
                            .closeYn(isClosedPopUp(commentEntity.getPopUpStore(), LocalDateTime.now()))
                            .build();

                    return GetCommenterCommentListResponse.Comment.builder()
                            .commentId(commentEntity.getId())
                            .content(commentEntity.getContent())
                            .likeCount(commentEntity.getLikeCount())
                            .createDateTime(commentEntity.getCreateDateTime())
                            .popUpStoreInfo(popUpStoreInfo) // 팝업스토어 정보 포함
                            .build();
                })
                .toList();

        // 전체 코멘트 수
        long totalElements = commentRepository.countCommenterComments(commenterId, commentType);

        // 전체 페이지 수
        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());

        return GetCommenterCommentListResponse.builder()
                .commentList(commenterCommentList)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }



    /**
     * 찜한 팝업스토어 목록 조회
     *
     * @param userId
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public GetBookMarkPopUpStoreListResponse getBookMarkedPopUpStoreList(String userId, Pageable pageable) {
        UserEntity user = this.findUserByUserId(userId);

        // 찜한 팝업스토어 목록 조회
        Page<BookMarkPopUpStoreEntity> bookMarkPopUpStorePage = bookMarkPopUpStoreRepository
                .findBookMarkPopUpStoresByUser(user, pageable);

        List<PopUpStoreEntity> bookMarkPopUpStores = bookMarkPopUpStorePage.stream()
                .map(BookMarkPopUpStoreEntity::getPopUpStore)
                .toList();

        List<GetBookMarkPopUpStoreListResponse.PopUpInfo> bookMarkPopUpInfoList = bookMarkPopUpStores.stream()
                .map(popUpStoreEntity -> GetBookMarkPopUpStoreListResponse.PopUpInfo.builder()
                        .popUpStoreId(popUpStoreEntity.getId())
                        .popUpStoreName(popUpStoreEntity.getName())
                        .desc(popUpStoreEntity.getDesc())
                        .mainImageUrl(popUpStoreEntity.getMainImageUrl())
                        .startDate(popUpStoreEntity.getStartDate())
                        .endDate(popUpStoreEntity.getEndDate())
                        .address(popUpStoreEntity.getAddress())
                        .closeYn(isClosedPopUp(popUpStoreEntity, LocalDateTime.now()))
                        .build())
                .toList();

        return GetBookMarkPopUpStoreListResponse.builder()
                .popUpInfoList(bookMarkPopUpInfoList)
                .totalPages(bookMarkPopUpStorePage.getTotalPages())
                .totalElements(bookMarkPopUpStorePage.getTotalElements())
                .build();

    }

    /**
     * 팝업스토어 찜
     *
     * @param userId
     * @param popUpStoreId
     */
    @Transactional
    public void addPopUpStoreBookmark(String userId, Long popUpStoreId) {
        UserEntity user = findUserByUserId(userId);
        PopUpStoreEntity popUpStore = popUpStoreRepository.findById(popUpStoreId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POPUP_STORE_NOT_FOUND));

        if (bookMarkPopUpStoreRepository.existsByUserAndPopUpStore(user, popUpStore)) {
            throw new BadRequestException(ErrorCode.ALREADY_EXISTS_BOOKMARK);
        }

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

        BookMarkPopUpStoreEntity bookMarkPopUpStore = BookMarkPopUpStoreEntity.builder()
                .user(user)
                .popUpStore(popUpStore)
                .build();

        // 찜 저장
        bookMarkPopUpStoreRepository.save(bookMarkPopUpStore);

        /** 동시성 이슈 방지를 위한 retry 및 예외 처리*/
        int retryCount = 0;
        int maxRetryCount = 3; // 최대 재시도 횟수

        while (retryCount < maxRetryCount) {
            try {
                // 팝업 스토어 찜 수 + 1
                popUpStore.incrementBookmarkCount();
                break;
            } catch (OptimisticLockException e) {
                retryCount++;
                if (retryCount >= maxRetryCount) {
                    throw new ConcurrencyException(ErrorCode.CONCURRENCY_ERROR);
                }
            }
        }
        // 팝업 스토어 뷰 찜 수 + 1
        userPopUpStoreView.incrementBookmarkCount();
    }

    /**
     * 팝업스토어 찜 취소
     *
     * @param userId
     * @param popUpStoreId
     */
    @Transactional
    public void deletePopUpStoreBookmark(String userId, Long popUpStoreId) {
        UserEntity user = findUserByUserId(userId);
        PopUpStoreEntity popUpStore = popUpStoreRepository.findById(popUpStoreId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POPUP_STORE_NOT_FOUND));

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

        BookMarkPopUpStoreEntity bookMarkPopUpStore = bookMarkPopUpStoreRepository.findByUser_UserIdAndPopUpStore_Id(userId, popUpStoreId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOOKMARK_NOT_FOUND));

        // 찜 삭제
        bookMarkPopUpStoreRepository.delete(bookMarkPopUpStore);

        /** 동시성 이슈 방지를 위한 retry 및 예외 처리*/
        int retryCount = 0;
        int maxRetryCount = 3; // 최대 재시도 횟수

        while (retryCount < maxRetryCount) {
            try {
                // 팝업 스토어 찜 수 - 1
                popUpStore.decrementBookmarkCount();
                break;
            } catch (OptimisticLockException e) {
                retryCount++;
                if (retryCount >= maxRetryCount) {
                    throw new ConcurrencyException(ErrorCode.CONCURRENCY_ERROR);
                }
            }
        }
        // 팝업 스토어 뷰 찜 수 - 1
        userPopUpStoreView.decrementBookmarkCount();

    }


    /**
     * 최근 본 팝업스토어 조회
     *
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
                        .mainImageUrl(popUpStoreEntity.getMainImageUrl())
                        .startDate(popUpStoreEntity.getStartDate())
                        .endDate(popUpStoreEntity.getEndDate())
                        .address(popUpStoreEntity.getAddress())
                        .closeYn(isClosedPopUp(popUpStoreEntity, LocalDateTime.now()))
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
     *
     * @param userId
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public GetBlockedUserListResponse getBlockedUserList(String userId, Pageable pageable) {
        UserEntity user = this.findUserByUserId(userId);

        Page<GetBlockedUserListResponse.BlockedUserInfo> blockedUserInfoPage = blockedUserRepository.getBlockedUserList(userId, pageable);

        List<GetBlockedUserListResponse.BlockedUserInfo> blockedUserInfoList = blockedUserInfoPage.stream()
                .map(blockedUserInfo -> GetBlockedUserListResponse.BlockedUserInfo.builder()
                        .userId(blockedUserInfo.getUserId())
                        .profileImageUrl(blockedUserInfo.getProfileImageUrl())
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
     *
     * @param userId        // 차단을 한 사용자
     * @param blockedUserId // 차단된 사용자
     */
    @Transactional
    public void blockUser(String userId, String blockedUserId) {
        UserEntity user = this.findUserByUserId(userId);
        UserEntity blocked = this.findUserByUserId(blockedUserId);

        if (blockedUserRepository.existsByUserAndBlockedUser(user, blocked)) {
            throw new BadRequestException(ErrorCode.ALREADY_BLOCKED_USER);
        }

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
     *
     * @param BlockerUserId
     * @param BlockedUserId
     */
    @Transactional
    public void unblockUser(String BlockerUserId, String BlockedUserId) {
        UserEntity blocker = this.findUserByUserId(BlockerUserId);
        UserEntity blocked = this.findUserByUserId(BlockedUserId);

        BlockedUserEntity blockedUser = blockedUserRepository.findByUserAndBlockedUser(blocker, blocked)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BLOCKED_USER_NOT_FOUND));

        // 차단 해제
        blockedUserRepository.delete(blockedUser);

    }

    /**
     * 회원 탈퇴
     *
     * @param userId
     */
    @Transactional
    public void deleteUser(String userId, CheckedSurveyListRequest request) throws IOException {
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


        // 애플 로그인 유저인 경우, revoke 적용
        if (user.getSocialType() == SocialType.APPLE) {
            AppleRefreshTokenEntity appleRefreshToken = appleRefreshTokenRepository.findByUserId(userId)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.APPLE_REFRESH_TOKEN_NOT_FOUND));
            String clientSecret = appleAuthService.createClientSecret();
            appleAuthFeignClient.revokeToken(appleRefreshToken.getToken(), appleProperties.getClientId(), clientSecret);
            appleRefreshTokenRepository.delete(appleRefreshToken);
        }

        RefreshTokenEntity refreshToken = refreshTokenRepository.findByUserId(userId)
                .orElse(null);

        if (refreshToken != null) {
            refreshTokenRepository.delete(refreshToken);
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
     * @param refreshToken
     */
    @Transactional
    public void logout(String refreshToken) {

        // refreshToken 블랙리스트에 추가 - 일단 사용 X , JWT의 Stateless가 없어지기 때문
        // blackListTokenService.addTokenToBlackList(refreshToken, expiryDateTime);

        // refreshToken DB에서 삭제
        refreshTokenService.deleteRefreshToken(jwtService.getUserId(refreshToken));

    }

    /**
     * 유저 엔티티 조회
     *
     * @param userId
     * @return
     */
    public UserEntity findUserByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }


    /**
     * 관리자 여부 체크
     * @param userRoles
     * @return
     */
    public boolean isAdmin(Set<UserRoleEntity> userRoles) {
        return userRoles.stream()
                .anyMatch(userRole -> userRole.getUserRole().equals(Role.ADMIN));
    }


    /** 종료 여부 */
    private boolean isClosedPopUp(PopUpStoreEntity popUpStore, LocalDateTime now) {
        return popUpStore.getEndDate().isBefore(now) || popUpStore.getEndDate().isEqual(now);
    }

}
