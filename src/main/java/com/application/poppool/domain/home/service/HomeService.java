package com.application.poppool.domain.home.service;

import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import com.application.poppool.domain.popup.repository.PopUpStoreRepository;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.UserInterestCategoryEntity;
import com.application.poppool.domain.user.service.UserService;
import com.application.poppool.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final PopUpStoreRepository popUpStoreRepository;
    private final UserService userService;

    /**
     * 홈 조회
     * @param userId
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public GetHomeInfoResponse getHomeInfo(String userId, Pageable pageable) {

        UserEntity user = userService.findUserByUserId(userId);

        /** 유저의 관심 카테고리 조회 */
        List<UserInterestCategoryEntity> userInterestCategories = user.getUserInterestCategories();


        if (user.getGender().name().equals("NONE")) {
            Page<GetHomeInfoResponse.CustomPopUpStores> customPopUpStoresPage = popUpStoreRepository.getCustomPopUpStoreList(userId, pageable, userInterestCategories);
        }
        else{
            Page<GetHomeInfoResponse.CustomPopUpStores> customPopUpStoresPage = popUpStoreRepository.getCustomPopUpStoreList(userId, pageable);
        }



        Page<GetHomeInfoResponse.PopularPopUpStores> popularPopUpStoresPage = popUpStoreRepository.getPopularPopUpStoreList(pageable);

        /** 인기 팝업 리스트 */
        List<GetHomeInfoResponse.PopularPopUpStores> popularPopUpStoresList = popularPopUpStoresPage.stream()
                .map(popularPopUpStores -> GetHomeInfoResponse.PopularPopUpStores.builder()
                        .category(popularPopUpStores.getCategory())
                        .name(popularPopUpStores.getName())
                        .address(popularPopUpStores.getAddress())
                        .image(popularPopUpStores.getImage())
                        .startDate(popularPopUpStores.getStartDate())
                        .endDate(popularPopUpStores.getEndDate())
                        .totalPages(popularPopUpStores.getTotalPages())
                        .totalElements(popularPopUpStores.getTotalElements())
                        .build())
                .toList();

        // 현재 시간
        LocalDateTime currentDate = LocalDateTime.now();
        Page<GetHomeInfoResponse.NewPopUpStores> newPopUpStoresPage = popUpStoreRepository.getNewPopUpStoreList(currentDate, pageable);

        /** 신규 팝업 리스트 */
        List<GetHomeInfoResponse.NewPopUpStores> newPopUpStoresList = newPopUpStoresPage.stream()
                .map(newPopUpStores -> GetHomeInfoResponse.NewPopUpStores.builder()
                        .category(newPopUpStores.getCategory())
                        .name(newPopUpStores.getName())
                        .address(newPopUpStores.getAddress())
                        .image(newPopUpStores.getImage())
                        .startDate(newPopUpStores.getStartDate())
                        .endDate(newPopUpStores.getEndDate())
                        .totalPages(newPopUpStores.getTotalPages())
                        .totalElements(newPopUpStores.getTotalElements())
                        .build())
                .toList();

        /** 로그인 여부 */
        boolean isLogin = false;

        if (SecurityUtils.isAuthenticated()) {
            isLogin = true;
        }

        return GetHomeInfoResponse.builder()
                .nickname(user.getNickname())
                .popularPopUpStoresList(popularPopUpStoresList)
                .newPopUpStoresList(newPopUpStoresList)
                .isLogin(isLogin)
                .build();
    }
}
