package com.application.poppool.domain.sign_up.service;

import com.application.poppool.domain.interest.entity.InterestEntity;
import com.application.poppool.domain.interest.enums.InterestType;
import com.application.poppool.domain.interest.repository.InterestRepository;
import com.application.poppool.domain.sign_up.dto.request.SignUpRequest;
import com.application.poppool.domain.sign_up.dto.response.GetGenderResponse;
import com.application.poppool.domain.sign_up.dto.response.GetInterestListResponse;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.UserInterestEntity;
import com.application.poppool.domain.user.enums.Gender;
import com.application.poppool.domain.user.repository.UserInterestRepository;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.global.exception.BadRequestException;
import com.application.poppool.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final UserInterestRepository userInterestRepository;
    private final InterestRepository interestRepository;

    /**
     * 회원가입
     *
     * @param signUpRequest
     */
    @Transactional
    public void signUp(SignUpRequest signUpRequest) {

        if (userRepository.findByUserId(signUpRequest.getUserId()).isPresent()) {
            throw new BadRequestException(ErrorCode.ALREADY_EXISTS_USER_ID);
        }

        UserEntity user = UserEntity.builder()
                .userId(signUpRequest.getUserId())
                .nickName(signUpRequest.getNickName())
                .email(signUpRequest.getSocialEmail())
                .gender(signUpRequest.getGender())
                .age(signUpRequest.getAge())
                .socialType(signUpRequest.getSocialType())
                .build();


        // 회원 관심 카테고리 추가
        this.addUserInterest(signUpRequest.getInterests(), user);

        // 회원 엔티티 저장(회원가입)
        userRepository.save(user);
    }


    /**
     * 회원가입 시, 관심사 등록 (회원가입에서만 쓰는 함수) - 책임 분리 원칙
     *
     * @param userInterests
     * @param user
     */
    private void addUserInterest(Set<InterestType> userInterests, UserEntity user) {

        for (InterestType interestType : userInterests) {
            InterestEntity interest = interestRepository.findByInterestId(interestType)
                    .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_VALIDATION_ERROR));

            UserInterestEntity userInterestEntity = UserInterestEntity.builder()
                    .user(user)
                    .interest(interest)
                    .build();

            // 회원 관심 카테고리 저장
            userInterestRepository.save(userInterestEntity);

            user.addInterest(userInterestEntity);
            interest.addUser(userInterestEntity);
        }


    }

    /**
     * 닉네임 중복확인
     */
    @Transactional(readOnly = true)
    public boolean isNickNameDuplicate(String nickName) {
        return userRepository.findByNickName(nickName).isPresent();
    }

    /**
     * 성별 후보군 목록 조회
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<GetGenderResponse> getGenderList() {
        return Arrays.stream(Gender.values())
                .map(gender -> GetGenderResponse.builder()
                        .gender(gender)
                        .label(gender.getLabel())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 관심사 후보군(목록) 전체 조회
     *
     * @return
     */
    @Transactional(readOnly = true)
    public GetInterestListResponse getInterestList() {

        // 관심사 목록 전체 조회
        List<InterestEntity> interestList = interestRepository.findAll();


        List<GetInterestListResponse.InterestResponse> interestResponse = interestList.stream()
                .map(interest -> GetInterestListResponse.InterestResponse.builder()
                        .interestId(interest.getInterestId())
                        .interestName(interest.getInterestName())
                        .build())
                .collect(Collectors.toList());

        return GetInterestListResponse.builder().interestResponseList(interestResponse).build();
    }

}
