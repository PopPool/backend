package com.application.poppool.domain.sign_up.service;

import com.application.poppool.domain.auth.dto.response.LoginResponse;
import com.application.poppool.domain.auth.enums.SocialType;
import com.application.poppool.domain.auth.service.apple.AppleAuthService;
import com.application.poppool.domain.category.entity.CategoryEntity;
import com.application.poppool.domain.category.repository.CategoryRepository;
import com.application.poppool.domain.sign_up.dto.request.SignUpRequest;
import com.application.poppool.domain.sign_up.dto.response.GetCategoryListResponse;
import com.application.poppool.domain.sign_up.dto.response.GetGenderResponse;
import com.application.poppool.domain.token.entity.AppleRefreshTokenEntity;
import com.application.poppool.domain.token.repository.AppleRefreshTokenRepository;
import com.application.poppool.domain.user.entity.RoleEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.UserInterestCategoryEntity;
import com.application.poppool.domain.user.entity.UserRoleEntity;
import com.application.poppool.domain.user.enums.Gender;
import com.application.poppool.domain.user.enums.Role;
import com.application.poppool.domain.user.repository.RoleRepository;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.global.exception.BadRequestException;
import com.application.poppool.global.exception.ErrorCode;
import com.application.poppool.global.exception.NotFoundException;
import com.application.poppool.global.jwt.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final AppleAuthService appleAuthService;
    private final AppleRefreshTokenRepository appleRefreshTokenRepository;
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;
    private final JwtService jwtService;

    /**
     * 회원가입
     *
     * @param signUpRequest
     */
    @Transactional
    public void signUp(String userId, SignUpRequest signUpRequest, HttpServletResponse response) throws IOException {


        if (userRepository.findByUserId(userId).isPresent()) {
            throw new BadRequestException(ErrorCode.ALREADY_EXISTS_USER_ID);
        }

        UserEntity user = UserEntity.builder()
                .userId(userId)
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


        // 회원가입 완료 토큰 발급
        LoginResponse loginResponse = jwtService.createJwtToken(userId, false);
        // 헤더에 토큰 싣기
        jwtService.setHeaderAccessToken(response, loginResponse.getAccessToken());
        jwtService.setHeaderRefreshToken(response, loginResponse.getRefreshToken());

        // refresh token이 이미 있으면 새로운 것으로 업데이트, 없으면 insert
        jwtService.saveOrReplaceRefreshToken(userId, loginResponse.getRefreshToken(), loginResponse.getRefreshTokenExpiresAt());

        // 애플 회원 가입시에만 애플 리프레쉬 토큰 DB 저장
        if (signUpRequest.getSocialType() == SocialType.APPLE) {
            // 애플 인가 코드로 Refresh Token 발급(추후에 회원 탈퇴 시, Revoke 용도)
            String appleRefreshToken = appleAuthService.getAppleRefreshToken(signUpRequest.getAppleAuthorizationCode());

            AppleRefreshTokenEntity appleRefreshTokenEntity = AppleRefreshTokenEntity.builder()
                    .userId(userId)
                    .token(appleRefreshToken)
                    .build();
            
            appleRefreshTokenRepository.save(appleRefreshTokenEntity);
        }

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
    private void addUserInterestCategory(Set<Integer> userInterestCategories, UserEntity user) {

        for (Integer categoryId : userInterestCategories) {
            CategoryEntity category = categoryRepository.findByCategoryId(categoryId)
                    .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_VALIDATION_ERROR));

            UserInterestCategoryEntity userInterestCategory = UserInterestCategoryEntity.builder()
                    .user(user)
                    .category(category)
                    .categoryName(category.getCategoryName())
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
                        .categoryName(category.getCategoryName())
                        .build())
                .collect(Collectors.toList());

        return GetCategoryListResponse.builder().categoryResponseList(interestResponse).build();
    }

}
