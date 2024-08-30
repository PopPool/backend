package com.application.poppool.domain.sign_up.service;

import com.application.poppool.domain.category.entity.CategoryEntity;
import com.application.poppool.domain.category.repository.CategoryRepository;
import com.application.poppool.domain.sign_up.dto.request.SignUpRequest;
import com.application.poppool.domain.sign_up.dto.response.GetCategoryListResponse;
import com.application.poppool.domain.sign_up.dto.response.GetGenderResponse;
import com.application.poppool.domain.user.entity.RoleEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.UserInterestCategoryEntity;
import com.application.poppool.domain.user.entity.UserRoleEntity;
import com.application.poppool.domain.user.enums.Gender;
import com.application.poppool.domain.user.enums.Role;
import com.application.poppool.domain.user.repository.RoleRepository;
import com.application.poppool.domain.user.repository.UserInterestCategoryRepository;
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
    private final UserInterestCategoryRepository userInterestCategoryRepository;
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;

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
        this.addUserInterestCategory(signUpRequest.getInterestCategories(), user);

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
                .userRole(role.getRole())
                .build();

        user.addUserRole(userRole);
    }

    /**
     * 회원가입 시, 관심사 등록 (회원가입에서만 쓰는 함수) - 책임 분리 원칙
     *
     * @param userInterestCategories
     * @param user
     */
    private void addUserInterestCategory(Set<Long> userInterestCategories, UserEntity user) {

        for (Long categoryId : userInterestCategories) {
            CategoryEntity category = categoryRepository.findByCategoryId(categoryId)
                    .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_VALIDATION_ERROR));

            UserInterestCategoryEntity userInterestCategory = UserInterestCategoryEntity.builder()
                    .user(user)
                    .category(category)
                    .interestCategory(category.getCategory())
                    .build();

            user.addInterestCategory(userInterestCategory);
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
    public GetCategoryListResponse getCategoryList() {

        // 관심사 목록 전체 조회
        List<CategoryEntity> categoryList = categoryRepository.findAllByOrderByCategoryId();


        List<GetCategoryListResponse.CategoryResponse> interestResponse = categoryList.stream()
                .map(category -> GetCategoryListResponse.CategoryResponse.builder()
                        .categoryId(category.getCategoryId())
                        .category(category.getCategory())
                        .build())
                .collect(Collectors.toList());

        return GetCategoryListResponse.builder().categoryResponseList(interestResponse).build();
    }

}
