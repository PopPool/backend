package com.application.poppool.domain.user.service;

import com.application.poppool.domain.token.service.BlackListTokenService;
import com.application.poppool.domain.token.service.RefreshTokenService;
import com.application.poppool.domain.user.dto.response.GetMyPageResponse;
import com.application.poppool.domain.user.dto.response.GetWithDrawlSurveyResponse;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.WithDrawalSurveyEntity;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.domain.user.repository.WithDrawlRepository;
import com.application.poppool.global.exception.ErrorCode;
import com.application.poppool.global.exception.NotFoundException;
import com.application.poppool.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final WithDrawlRepository withDrawlSurveyRepository;
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

        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_USER));


        List<GetMyPageResponse.PopUpInfo> popUpInfoList = user.getComments().stream()
                .map(comment -> comment.getPopUpStore())
                .map(popUpStore -> GetMyPageResponse.PopUpInfo.builder()
                        .popUpStoreId(popUpStore.getId())
                        .popUpStoreName(popUpStore.getName())
                        .build())
                .toList();

        return GetMyPageResponse.builder()
                .nickName(user.getNickName())
                .instagramId(user.getInstagramId())
                .popUpInfoList(popUpInfoList)
                .build();
    }

    /**
     * 회원 탈퇴
     *
     * @param userId
     */
    @Transactional
    public void deleteUser(String userId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_USER));

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
                        .question(surveyEntity.getQuestion())
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

}
