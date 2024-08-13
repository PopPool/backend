package com.application.poppool.domain.home.service;

import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import com.application.poppool.domain.popup.repository.PopUpStoreRepository;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.service.UserService;
import com.application.poppool.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
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

        /** 추천 팝업 리스트 */
        List<GetHomeInfoResponse.PopUpStore> customPopUpStoreList = popUpStoreRepository.getCustomPopUpStoreList(user, pageable);

        // 전체 맞춤 팝업 데이터 수
        long customPopUpStoreTotalElements = popUpStoreRepository.countCustomPopUpStores(user);

        // 전체 맞춤 팝업 페이지 수
        int customPopUpStoreTotalPages = (int) Math.ceil((double) customPopUpStoreTotalElements / pageable.getPageSize());


        /** 인기 팝업 리스트 */
        List<GetHomeInfoResponse.PopUpStore> popularPopUpStoreList = popUpStoreRepository.getPopularPopUpStoreList(pageable);

        // 전체 인기 팝업 수
        long popularPopUpStoreTotalElements = popUpStoreRepository.countPopularPopUpStores();

        // 전체 인기 팝업 페이지 수
        int popularPopUpStoreTotalPages = (int) Math.ceil((double) popularPopUpStoreTotalElements / pageable.getPageSize());

        /** 현재 시간 */
        LocalDateTime currentDate = LocalDateTime.now();

        /**신규 팝업 리스트 */
        List<GetHomeInfoResponse.PopUpStore> newPopUpStoreList = popUpStoreRepository.getNewPopUpStoreList(currentDate, pageable);
        
        // 전체 신규 팝업 데이터 수
        long newPopUpStoreTotalElements = popUpStoreRepository.countNewPopUpStores(currentDate);

        // 전체 신규 팝업 페이지 수
        int newPopUpStoreTotalPages = (int) Math.ceil((double) newPopUpStoreTotalElements / pageable.getPageSize());

        /** 로그인 여부 */
        boolean isLogin = false;

        if (SecurityUtils.isAuthenticated()) {
            isLogin = true;
        }

        return GetHomeInfoResponse.builder()
                .nickname(user.getNickname())
                .customPopUpStoreList(customPopUpStoreList)
                .customPopUpStoreTotalPages(customPopUpStoreTotalPages)
                .customPopUpStoreTotalElements(customPopUpStoreTotalElements)
                .popularPopUpStoreList(popularPopUpStoreList)
                .popularPopUpStoreTotalPages(popularPopUpStoreTotalPages)
                .popularPopUpStoreTotalElements(popularPopUpStoreTotalElements)
                .newPopUpStoreList(newPopUpStoreList)
                .newPopUpStoreTotalPages(newPopUpStoreTotalPages)
                .newPopUpStoreTotalElements(newPopUpStoreTotalElements)
                .isLogin(isLogin)
                .build();
    }

    @Transactional(readOnly = true)
    public GetHomeInfoResponse getCustomPopUpStoreList(String userId, Pageable pageable) {

        UserEntity user = userService.findUserByUserId(userId);

        /** 추천 팝업 리스트 */
        List<GetHomeInfoResponse.PopUpStore> customPopUpStoreList = popUpStoreRepository.getCustomPopUpStoreList(user, pageable);

        // 전체 맞춤 팝업 데이터 수
        long customPopUpStoreTotalElements = popUpStoreRepository.countCustomPopUpStores(user);

        // 전체 맞춤 팝업 페이지 수
        int customPopUpStoreTotalPages = (int) Math.ceil((double) customPopUpStoreTotalElements / pageable.getPageSize());

        return GetHomeInfoResponse.builder()
                .customPopUpStoreList(customPopUpStoreList)
                .customPopUpStoreTotalPages(customPopUpStoreTotalPages)
                .customPopUpStoreTotalElements(customPopUpStoreTotalElements)
                .build();
    }

    @Transactional(readOnly = true)
    public GetHomeInfoResponse getPopularPopUpStoreList(Pageable pageable) {

        /** 인기 팝업 리스트 */
        List<GetHomeInfoResponse.PopUpStore> popularPopUpStoreList = popUpStoreRepository.getPopularPopUpStoreList(pageable);

        // 전체 인기 팝업 수
        long popularPopUpStoreTotalElements = popUpStoreRepository.countPopularPopUpStores();

        // 전체 인기 팝업 페이지 수
        int popularPopUpStoreTotalPages = (int) Math.ceil((double) popularPopUpStoreTotalElements / pageable.getPageSize());

        return GetHomeInfoResponse.builder()
                .popularPopUpStoreList(popularPopUpStoreList)
                .popularPopUpStoreTotalPages(popularPopUpStoreTotalPages)
                .popularPopUpStoreTotalElements(popularPopUpStoreTotalElements)
                .build();
    }

    @Transactional(readOnly = true)
    public GetHomeInfoResponse getNewPopUpStoreList(Pageable pageable) {

        /** 현재 시간 */
        LocalDateTime currentDate = LocalDateTime.now();

        /**신규 팝업 리스트 */
        List<GetHomeInfoResponse.PopUpStore> newPopUpStoreList = popUpStoreRepository.getNewPopUpStoreList(currentDate, pageable);

        // 전체 신규 팝업 데이터 수
        long newPopUpStoreTotalElements = popUpStoreRepository.countNewPopUpStores(currentDate);

        // 전체 신규 팝업 페이지 수
        int newPopUpStoreTotalPages = (int) Math.ceil((double) newPopUpStoreTotalElements / pageable.getPageSize());

        return GetHomeInfoResponse.builder()
                .newPopUpStoreList(newPopUpStoreList)
                .newPopUpStoreTotalPages(newPopUpStoreTotalPages)
                .newPopUpStoreTotalElements(newPopUpStoreTotalElements)
                .build();

    }

}
