package com.application.poppool.domain.user.service;

import com.application.poppool.domain.token.service.BlackListTokenService;
import com.application.poppool.domain.token.service.RefreshTokenService;
import com.application.poppool.domain.user.dto.request.CheckedSurveyListRequest;
import com.application.poppool.domain.user.dto.response.GetMyCommentResponse;
import com.application.poppool.domain.user.dto.response.GetMyPageResponse;
import com.application.poppool.domain.user.dto.response.GetWithDrawlSurveyResponse;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.WithDrawalSurveyEntity;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.domain.user.repository.WithDrawlRepository;
import com.application.poppool.global.exception.BadRequestException;
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
     * 내가 쓴 일반 코멘트 조회
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public GetMyCommentResponse getMyCommentList(String userId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_USER));

        List<GetMyCommentResponse.MyCommentInfo> myCommentList = user.getComments().stream()
                .map(commentEntity -> GetMyCommentResponse.MyCommentInfo.builder()
                        .commentId(commentEntity.getId())
                        .content(commentEntity.getContent())
                        .image(commentEntity.getImage())
                        .build())
                .toList();

        return GetMyCommentResponse.builder().myCommentList(myCommentList).build();
    }




    /**
     * 회원 탈퇴
     *
     * @param userId
     */
    @Transactional
    public void deleteUser(String userId, CheckedSurveyListRequest request) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_USER));

        // 회원 탈퇴 설문 항목 수 증가
        for (CheckedSurveyListRequest.CheckedSurvey checkedSurvey : request.getCheckedSurveyList()) {
            WithDrawalSurveyEntity surveyEntity = withDrawlSurveyRepository.findById(checkedSurvey.getId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_DATA));

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

}
