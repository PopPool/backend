package com.application.poppool.domain.user.service;

import com.application.poppool.domain.category.entity.CategoryEntity;
import com.application.poppool.domain.category.repository.CategoryRepository;
import com.application.poppool.domain.user.dto.request.UpdateMyInterestCategoryRequest;
import com.application.poppool.domain.user.dto.request.UpdateMyProfileRequest;
import com.application.poppool.domain.user.dto.request.UpdateMyTailoredInfoRequest;
import com.application.poppool.domain.user.dto.response.GetProfileResponse;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.UserInterestCategoryEntity;
import com.application.poppool.domain.user.repository.UserInterestCategoryRepository;
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
    private final UserInterestCategoryRepository userInterestCategoryRepository;
    private final CategoryRepository categoryRepository;


    /**
     * 프로필 조회
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public GetProfileResponse getMyProfile(String userId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));

        // 회원의 관심 카테고리 목록 가져오기
        List<GetProfileResponse.MyInterestCategoryInfo> myInterestCategoryInfoList = this.getMyInterestCategoryList(user);

        return GetProfileResponse.builder()
                .profileImageUrl(user.getProfileImageUrl())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .instagramId(user.getInstagramId())
                .intro(user.getIntro())
                .gender(user.getGender())
                .age(user.getAge())
                .interestCategoryList(myInterestCategoryInfoList)
                .build();
    }


    /**
     * 회원 관심 카테고리 리스트
     *
     * @param user
     * @return
     */
    private List<GetProfileResponse.MyInterestCategoryInfo> getMyInterestCategoryList(UserEntity user) {
        return user.getUserInterestCategories().stream()
                .map(userInterestCategory -> GetProfileResponse.MyInterestCategoryInfo.builder()
                        .categoryId(userInterestCategory.getCategory().getCategoryId())
                        .interestCategory(userInterestCategory.getCategory().getCategory())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 회원 프로필 수정
     *
     * @param userId
     * @param updateMyProfileRequest
     */
    @Transactional
    public void updateMyProfile(String userId, UpdateMyProfileRequest updateMyProfileRequest) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));

        // 회원 프로필 항목 수정
        user.updateMyProfile(updateMyProfileRequest);

        // 회원 저장
        userRepository.save(user);
    }

    /**
     * 회원 관심 카테고리 수정
     *
     * @param userId
     * @param request
     */
    @Transactional
    public void updateMyInterestCategory(String userId, UpdateMyInterestCategoryRequest request) {

        // 유저 엔티티 조회
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));

        // 삭제할 관심 카테고리 삭제
        List<UserInterestCategoryEntity> interestCategoriesToDelete = user.getUserInterestCategories().stream()
                .filter(myInterest -> request.getInterestCategoriesToDelete().contains(myInterest.getCategory().getCategoryId()))
                .collect(Collectors.toList());

        userInterestCategoryRepository.deleteAll(interestCategoriesToDelete);

        // 추가할 관심 카테고리 추가
        List<UserInterestCategoryEntity> interestCategoriesToAdd = request.getInterestCategoriesToAdd().stream()
                .map(categoryToAdd -> createUserInterestCategoryEntity(user, categoryToAdd))
                .collect(Collectors.toList());

        // 관심 카테고리 저장
        userInterestCategoryRepository.saveAll(interestCategoriesToAdd);
    }

    /**
     * 회원 관심 카테고리 추가를 위한 엔티티 생성
     *
     * @param user
     * @param interestCategoryToAdd
     * @return
     */
    private UserInterestCategoryEntity createUserInterestCategoryEntity(UserEntity user, Long interestCategoryToAdd) {
        CategoryEntity category = categoryRepository.findByCategoryId(interestCategoryToAdd)
                .orElseThrow(() -> new BadRequestException(ErrorCode.CATEGORY_NOT_FOUND));
        return UserInterestCategoryEntity.builder()
                .user(user)
                .category(category)
                .build();
    }

    /**
     * 회원 맞춤 정보(성별,나이) 수정
     *
     * @param userId
     * @param updateMyTailoredInfoRequest
     */
    @Transactional
    public void updateMyTailoredInfo(String userId, UpdateMyTailoredInfoRequest updateMyTailoredInfoRequest) throws BadRequestException {
        // 회원 엔티티 조회
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));

        // 회원 맞춤 정보 수정
        user.updateMyTailoredInfo(updateMyTailoredInfoRequest);

        userRepository.save(user);

    }

}
    
