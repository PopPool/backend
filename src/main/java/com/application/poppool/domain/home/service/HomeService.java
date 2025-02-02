package com.application.poppool.domain.home.service;

import com.application.poppool.domain.home.dto.response.GetAllCustomPopUpStoresResponse;
import com.application.poppool.domain.home.dto.response.GetAllNewPopUpStoresResponse;
import com.application.poppool.domain.home.dto.response.GetAllPopularPopUpStoresResponse;
import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import com.application.poppool.domain.popup.repository.PopUpStoreRepository;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.service.UserService;
import com.application.poppool.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeService {

    private final PopUpStoreRepository popUpStoreRepository;
    private final UserService userService;

    /**
     * 홈 조회
     *
     * @param userId
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public GetHomeInfoResponse getHomeInfo(String userId, Pageable pageable) {

        /** 로그인 여부 */
        boolean loginYn = false;
        String nickname = null;

        List<GetHomeInfoResponse.PopUpStore> customPopUpStoreList = new ArrayList<>();
        long customPopUpStoreTotalElements = 0L;
        int customPopUpStoreTotalPages = 0;

        // 배너 팝업 리스트
        List<GetHomeInfoResponse.BannerPopUpStore> bannerPopUpStoreList = popUpStoreRepository.getBannerPopUpStoreList();
        
        if (SecurityUtils.isAuthenticated()) {
            loginYn = true;
            UserEntity user = userService.findUserByUserId(userId);
            nickname = user.getNickname();

            /** 추천 팝업 리스트 */
            customPopUpStoreList = popUpStoreRepository.getCustomPopUpStoreList(user, pageable);
            System.out.println("Size" + customPopUpStoreList.size());
            // 추천 팝업이 없으면, 카테고리 인기팝업에서 최대 6개 노출
            if (customPopUpStoreList.size() == 0) {
                log.info("카테고리 인기");
                System.out.println("카테고리 인기");
                customPopUpStoreList = popUpStoreRepository.getCategoryPopularPopUpStoreList(user, pageable);
            }

            // 카테고리에서도 없다면, 전체 인기 팝업에서 최대 6개 노출
            if (customPopUpStoreList.size() == 0) {
                log.info("전체 인기");
                System.out.println("전체 인기 ");
                customPopUpStoreList = popUpStoreRepository.getPopularPopUpStoreList(user.getUserId(), pageable);
            }

            // 전체 맞춤 팝업 데이터 수
            customPopUpStoreTotalElements = popUpStoreRepository.countCustomPopUpStores(user);

            // 전체 맞춤 팝업 페이지 수
            customPopUpStoreTotalPages = (int) Math.ceil((double) customPopUpStoreTotalElements / pageable.getPageSize());

        }

        /** 인기 팝업 리스트 */
        List<GetHomeInfoResponse.PopUpStore> popularPopUpStoreList = popUpStoreRepository.getPopularPopUpStoreList(userId, pageable);


        // 전체 인기 팝업 수
        long popularPopUpStoreTotalElements = popUpStoreRepository.countPopularPopUpStores();

        // 전체 인기 팝업 페이지 수
        int popularPopUpStoreTotalPages = (int) Math.ceil((double) popularPopUpStoreTotalElements / pageable.getPageSize());

        /** 현재 시간 */
        LocalDateTime currentDate = LocalDateTime.now();

        /**신규 팝업 리스트 */
        List<GetHomeInfoResponse.PopUpStore> newPopUpStoreList = popUpStoreRepository.getNewPopUpStoreList(userId, currentDate, pageable);

        // 전체 신규 팝업 데이터 수
        long newPopUpStoreTotalElements = popUpStoreRepository.countNewPopUpStores(currentDate);

        // 전체 신규 팝업 페이지 수
        int newPopUpStoreTotalPages = (int) Math.ceil((double) newPopUpStoreTotalElements / pageable.getPageSize());

        return GetHomeInfoResponse.builder()
                .bannerPopUpStoreList(bannerPopUpStoreList)
                .nickname(nickname)
                .customPopUpStoreList(customPopUpStoreList)
                .customPopUpStoreTotalPages(customPopUpStoreTotalPages)
                .customPopUpStoreTotalElements(customPopUpStoreTotalElements)
                .popularPopUpStoreList(popularPopUpStoreList)
                .popularPopUpStoreTotalPages(popularPopUpStoreTotalPages)
                .popularPopUpStoreTotalElements(popularPopUpStoreTotalElements)
                .newPopUpStoreList(newPopUpStoreList)
                .newPopUpStoreTotalPages(newPopUpStoreTotalPages)
                .newPopUpStoreTotalElements(newPopUpStoreTotalElements)
                .loginYn(loginYn)
                .build();
    }

    @Transactional(readOnly = true)
    public GetAllCustomPopUpStoresResponse getCustomPopUpStoreList(String userId, Pageable pageable) {

        UserEntity user = userService.findUserByUserId(userId);

        /** 추천 팝업 리스트 */
        List<GetHomeInfoResponse.PopUpStore> customPopUpStoreList = popUpStoreRepository.getCustomPopUpStoreList(user, pageable);

        // 추천 팝업이 없으면, 카테고리 인기팝업에서 최대 6개 노출
        if (customPopUpStoreList.size() == 0) {
            customPopUpStoreList = popUpStoreRepository.getCategoryPopularPopUpStoreList(user, pageable);
        }

        // 카테고리에서도 없다면, 전체 인기 팝업에서 최대 6개 노출
        if (customPopUpStoreList.size() == 0) {
            customPopUpStoreList = popUpStoreRepository.getPopularPopUpStoreList(user.getUserId(), pageable);
        }

        // 전체 맞춤 팝업 데이터 수
        long customPopUpStoreTotalElements = popUpStoreRepository.countCustomPopUpStores(user);

        // 전체 맞춤 팝업 페이지 수
        int customPopUpStoreTotalPages = (int) Math.ceil((double) customPopUpStoreTotalElements / pageable.getPageSize());

        return GetAllCustomPopUpStoresResponse.builder()
                .customPopUpStoreList(customPopUpStoreList)
                .customPopUpStoreTotalPages(customPopUpStoreTotalPages)
                .customPopUpStoreTotalElements(customPopUpStoreTotalElements)
                .build();
    }

    @Transactional(readOnly = true)
    public GetAllPopularPopUpStoresResponse getPopularPopUpStoreList(String userId, Pageable pageable) {

        /** 인기 팝업 리스트 */
        List<GetHomeInfoResponse.PopUpStore> popularPopUpStoreList = popUpStoreRepository.getPopularPopUpStoreList(userId, pageable);

        // 전체 인기 팝업 수
        long popularPopUpStoreTotalElements = popUpStoreRepository.countPopularPopUpStores();

        // 전체 인기 팝업 페이지 수
        int popularPopUpStoreTotalPages = (int) Math.ceil((double) popularPopUpStoreTotalElements / pageable.getPageSize());

        return GetAllPopularPopUpStoresResponse.builder()
                .popularPopUpStoreList(popularPopUpStoreList)
                .popularPopUpStoreTotalPages(popularPopUpStoreTotalPages)
                .popularPopUpStoreTotalElements(popularPopUpStoreTotalElements)
                .build();
    }

    @Transactional(readOnly = true)
    public GetAllNewPopUpStoresResponse getNewPopUpStoreList(String userId, Pageable pageable) {

        /** 현재 시간 */
        LocalDateTime currentDate = LocalDateTime.now();

        /**신규 팝업 리스트 */
        List<GetHomeInfoResponse.PopUpStore> newPopUpStoreList = popUpStoreRepository.getNewPopUpStoreList(userId, currentDate, pageable);

        // 전체 신규 팝업 데이터 수
        long newPopUpStoreTotalElements = popUpStoreRepository.countNewPopUpStores(currentDate);

        // 전체 신규 팝업 페이지 수
        int newPopUpStoreTotalPages = (int) Math.ceil((double) newPopUpStoreTotalElements / pageable.getPageSize());

        return GetAllNewPopUpStoresResponse.builder()
                .newPopUpStoreList(newPopUpStoreList)
                .newPopUpStoreTotalPages(newPopUpStoreTotalPages)
                .newPopUpStoreTotalElements(newPopUpStoreTotalElements)
                .build();

    }

}
