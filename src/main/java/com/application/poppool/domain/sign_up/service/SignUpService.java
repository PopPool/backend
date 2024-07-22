package com.application.poppool.domain.sign_up.service;

import com.application.poppool.domain.interest.entity.InterestEntity;
import com.application.poppool.domain.interest.repository.InterestRepository;
import com.application.poppool.domain.sign_up.dto.request.SignUpRequest;
import com.application.poppool.domain.sign_up.dto.response.GetGenderResponse;
import com.application.poppool.domain.sign_up.dto.response.GetInterestListResponse;
import com.application.poppool.domain.user.entity.RoleEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.UserInterestEntity;
import com.application.poppool.domain.user.entity.UserRoleEntity;
import com.application.poppool.domain.user.enums.Gender;
import com.application.poppool.domain.user.enums.Role;
import com.application.poppool.domain.user.repository.RoleRepository;
import com.application.poppool.domain.user.repository.UserInterestRepository;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.global.exception.BadRequestException;
import com.application.poppool.global.exception.ErrorCode;
import com.application.poppool.global.exception.NotFoundException;
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
    private final RoleRepository roleRepository;
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
                .nickname(signUpRequest.getNickname())
                .email(signUpRequest.getSocialEmail())
                .gender(signUpRequest.getGender())
                .age(signUpRequest.getAge())
                .socialType(signUpRequest.getSocialType())
                .build();

        // 회원 엔티티 저장(회원가입) user 변수로 받아서 명시(변수로 받지 않으면 user 참조 못하는 에러남)
        user = userRepository.save(user);


        // 회원 권한 부여
        this.addUserRole(user);
        // 회원 관심 카테고리 추가
        this.addUserInterest(signUpRequest.getInterests(), user);

    }


    /**
     * 회원 권한 부여 (일반 사용자)
     *
     * @param user
     */
    private void addUserRole(UserEntity user) {

        // 일반 사용자 권한 부여
        RoleEntity role = roleRepository.findByRole(Role.USER)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DATA_NOT_FOUND));

        // 회원 권한 엔티티 생성
        UserRoleEntity userRole = UserRoleEntity.builder()
                .user(user)
                .role(role)
                .build();

        user.addUserRole(userRole);
    }

    /**
     * 회원가입 시, 관심사 등록 (회원가입에서만 쓰는 함수) - 책임 분리 원칙
     *
     * @param userInterests
     * @param user
     */
    private void addUserInterest(Set<Long> userInterests, UserEntity user) {

        for (Long interestId : userInterests) {
            InterestEntity interest = interestRepository.findByInterestId(interestId)
                    .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_VALIDATION_ERROR));

            UserInterestEntity userInterestEntity = UserInterestEntity.builder()
                    .user(user)
                    .interest(interest)
                    .interestCategory(interest.getInterestCategory())
                    .build();

            // 회원 관심 카테고리 저장
            //userInterestRepository.save(userInterestEntity);

            user.addInterest(userInterestEntity);
            interest.addUser(userInterestEntity);
        }


    }

    /**
     * 닉네임 중복확인
     */
    @Transactional(readOnly = true)
    public boolean isNicknameDuplicate(String nickName) {
        return userRepository.findByNickname(nickName).isPresent();
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
                        .value(gender.getValue())
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
        List<InterestEntity> interestList = interestRepository.findAllByOrderByInterestId();


        List<GetInterestListResponse.InterestResponse> interestResponse = interestList.stream()
                .map(interest -> GetInterestListResponse.InterestResponse.builder()
                        .interestId(interest.getInterestId())
                        .interestCategory(interest.getInterestCategory())
                        .build())
                .collect(Collectors.toList());

        return GetInterestListResponse.builder().interestResponseList(interestResponse).build();
    }

}
