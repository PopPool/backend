package com.application.poppool.domain.user.service;

import com.application.poppool.domain.interest.entity.InterestEntity;
import com.application.poppool.domain.interest.enums.InterestType;
import com.application.poppool.domain.interest.repository.InterestRepository;
import com.application.poppool.domain.user.dto.request.UpdateMyInterestRequest;
import com.application.poppool.domain.user.dto.response.GetProfileResponse;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.UserInterestEntity;
import com.application.poppool.domain.user.repository.UserInterestRepository;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.global.exception.BadRequestException;
import com.application.poppool.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;
    private final UserInterestRepository userInterestRepository;
    private final InterestRepository interestRepository;


    /**
     * 프로필 조회
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public GetProfileResponse getMyProfile(String userId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTS_USER));

        // 회원의 관심 카테고리 목록 가져오기
        List<GetProfileResponse.MyInterestInfo> myInterestInfoList = this.getMyInterestList(user);

        return GetProfileResponse.builder()
                .profileImage(user.getProfileImage())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .instagramId(user.getInstagramId())
                .intro(user.getIntro())
                .gender(user.getGender())
                .age(user.getAge())
                .interestList(myInterestInfoList)
                .build();
    }


    /**
     * 회원 관심 카테고리 리스트
     * @param user
     * @return
     */
    @Transactional(readOnly = true)
    private List<GetProfileResponse.MyInterestInfo> getMyInterestList(UserEntity user) {
        return user.getUserInterestEntities().stream()
                .map(userInterestEntity -> GetProfileResponse.MyInterestInfo.builder()
                        .interestId(userInterestEntity.getInterest().getInterestId())
                        .interestName(userInterestEntity.getInterest().getInterestName())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateMyInterests(String userId, UpdateMyInterestRequest updateMyInterestRequest) {


        // 유저 엔티티 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTS_USER));

        // 삭제할 관심 카테고리 삭제
        List<UserInterestEntity> interestsToDelete = user.getUserInterestEntities().stream()
                .filter(myInterest -> updateMyInterestRequest.getInterestsToDelete().contains(myInterest.getInterest().getInterestId()))
            .collect(Collectors.toList());

        userInterestRepository.deleteAll(interestsToDelete);

        // 추가할 관심 카테고리 추가
        List<UserInterestEntity> interestsToAdd = updateMyInterestRequest.getInterestsToAdd().stream()
                .map(interestToAdd -> createUserInterestEntity(user,interestToAdd))
            .collect(Collectors.toList());

        // 관심 카테고리 저장
        userInterestRepository.saveAll(interestsToAdd);
    }

    /**
     * 회원 관심 카테고리 추가를 위한 엔티티 생성
     * @param user
     * @param interestToAdd
     * @return
     */
    private UserInterestEntity createUserInterestEntity(UserEntity user, InterestType interestToAdd) {
        InterestEntity interest = interestRepository.findById(interestToAdd)
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTS_INTEREST));
        return UserInterestEntity.builder()
                .user(user)
                .interest(interest)
                .build();
    }

}
    
