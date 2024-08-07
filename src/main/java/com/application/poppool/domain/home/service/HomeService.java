package com.application.poppool.domain.home.service;

import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import com.application.poppool.domain.popup.repository.PopUpStoreRepository;
import com.application.poppool.domain.user.entity.UserEntity;
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

        /** 추천 팝업 페이지 */
        Page<GetHomeInfoResponse.CustomPopUpStore> customPopUpStorePage = popUpStoreRepository.getCustomPopUpStoreList(user, pageable);

        /** 추천 팝업 리스트 (Page -> List 변환) */
        List<GetHomeInfoResponse.CustomPopUpStore> customPopUpStoreList = customPopUpStorePage.stream()
                .map(customPopUpStore -> GetHomeInfoResponse.CustomPopUpStore.builder()
                        .id(customPopUpStore.getId())
                        .category(customPopUpStore.getCategory())
                        .name(customPopUpStore.getName())
                        .address(customPopUpStore.getAddress())
                        .mainImageUrl(customPopUpStore.getMainImageUrl())
                        .startDate(customPopUpStore.getStartDate())
                        .endDate(customPopUpStore.getEndDate())
                        .totalPages(customPopUpStore.getTotalPages())
                        .totalElements(customPopUpStore.getTotalElements())
                        .build())
                .toList();


        /** 인기 팝업 페이지 */
        Page<GetHomeInfoResponse.PopularPopUpStore> popularPopUpStorePage = popUpStoreRepository.getPopularPopUpStoreList(pageable);

        /** 인기 팝업 리스트 (Page -> List 변환) */
        List<GetHomeInfoResponse.PopularPopUpStore> popularPopUpStoreList = popularPopUpStorePage.stream()
                .map(popularPopUpStore -> GetHomeInfoResponse.PopularPopUpStore.builder()
                        .id(popularPopUpStore.getId())
                        .category(popularPopUpStore.getCategory())
                        .name(popularPopUpStore.getName())
                        .address(popularPopUpStore.getAddress())
                        .mainImageUrl(popularPopUpStore.getMainImageUrl())
                        .startDate(popularPopUpStore.getStartDate())
                        .endDate(popularPopUpStore.getEndDate())
                        .totalPages(popularPopUpStore.getTotalPages())
                        .totalElements(popularPopUpStore.getTotalElements())
                        .build())
                .toList();

        /** 현재 시간 */
        LocalDateTime currentDate = LocalDateTime.now();

        /**신규 팝업 페이지 */
        Page<GetHomeInfoResponse.NewPopUpStore> newPopUpStorePage = popUpStoreRepository.getNewPopUpStoreList(currentDate, pageable);

        /** 신규 팝업 리스트 (Page -> List 변환) */
        List<GetHomeInfoResponse.NewPopUpStore> newPopUpStoreList = newPopUpStorePage.stream()
                .map(newPopUpStore -> GetHomeInfoResponse.NewPopUpStore.builder()
                        .id(newPopUpStore.getId())
                        .category(newPopUpStore.getCategory())
                        .name(newPopUpStore.getName())
                        .address(newPopUpStore.getAddress())
                        .mainImageUrl(newPopUpStore.getMainImageUrl())
                        .startDate(newPopUpStore.getStartDate())
                        .endDate(newPopUpStore.getEndDate())
                        .totalPages(newPopUpStore.getTotalPages())
                        .totalElements(newPopUpStore.getTotalElements())
                        .build())
                .toList();

        /** 로그인 여부 */
        boolean isLogin = false;

        if (SecurityUtils.isAuthenticated()) {
            isLogin = true;
        }

        return GetHomeInfoResponse.builder()
                .nickname(user.getNickname())
                .customPopUpStoreList(customPopUpStoreList)
                .popularPopUpStoreList(popularPopUpStoreList)
                .newPopUpStoreList(newPopUpStoreList)
                .isLogin(isLogin)
                .build();
    }
}
