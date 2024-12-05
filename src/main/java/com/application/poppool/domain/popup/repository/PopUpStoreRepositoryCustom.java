package com.application.poppool.domain.popup.repository;

import com.application.poppool.domain.admin.popup.dto.response.GetAdminPopUpStoreListResponse;
import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import com.application.poppool.domain.popup.dto.resonse.GetClosedPopUpStoreListResponse;
import com.application.poppool.domain.popup.dto.resonse.GetOpenPopUpStoreListResponse;
import com.application.poppool.domain.popup.dto.resonse.GetPopUpStoreDetailResponse;
import com.application.poppool.domain.popup.dto.resonse.GetPopUpStoreDirectionResponse;
import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.search.dto.SearchPopUpStoreResponse;
import com.application.poppool.domain.user.entity.UserEntity;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface PopUpStoreRepositoryCustom {

    List<GetHomeInfoResponse.BannerPopUpStore> getBannerPopUpStoreList();

    List<Integer> getUserInterestCategoryList(String userId);

    /**
     * 맞춤 팝업 리스트
     */
    List<GetHomeInfoResponse.PopUpStore> getCustomPopUpStoreList(UserEntity user, Pageable pageable);

    List<GetHomeInfoResponse.PopUpStore> getCategoryPopularPopUpStoreList(UserEntity user, Pageable pageable);

    long countCustomPopUpStores(UserEntity user);

    /**
     * 인기 팝업 리스트
     */
    List<GetHomeInfoResponse.PopUpStore> getPopularPopUpStoreList(String userId, Pageable pageable);

    long countPopularPopUpStores();

    /**
     * 신규 팝업 리스트
     */
    List<GetHomeInfoResponse.PopUpStore> getNewPopUpStoreList(String userId, LocalDateTime currentDate, Pageable pageable);

    long countNewPopUpStores(LocalDateTime currentDate);

    /**
     * 팝업 스토어 상세 - 비슷한 팝업 리스트 조회
     */
    List<GetPopUpStoreDetailResponse.PopUpStore> getSimilarPopUpStoreList(Long popUpStoreId, Integer categoryId);

    /**
     * 검색창 하단 팝업 스토어 전체 조회
     */
    List<GetOpenPopUpStoreListResponse.PopUpStore> getOpenPopUpStoreList(List<Integer> categories, Pageable pageable);

    long countOpenPopUpStores(List<Integer> categories);

    List<GetClosedPopUpStoreListResponse.PopUpStore> getClosedPopUpStoreList(List<Integer> categories, Pageable pageable);

    long countClosedPopUpStores(List<Integer> categories);

    // 팝업스토어 검색
    List<SearchPopUpStoreResponse.PopUpStore> searchPopUpStore(String query);

    /**
     * 지도
     */
    List<PopUpStoreEntity> searchPopUpStoreByMap(List<Integer> categories, String query);

    List<PopUpStoreEntity> getViewBoundPopUpStoreList(List<Integer> categories, double northEastLat, double northEastLon, double southWestLat, double southWestLon);


    /**
     * 팝업 스토어 찾아가는 길
     */
    GetPopUpStoreDirectionResponse getPopUpStoreDirection(Long popUpStoreId);

    /**
     * 관리자 페이지 팝업 스토어 리스트 조회
     */
    List<GetAdminPopUpStoreListResponse.PopUpStore> getAdminPopUpStoreList(String query, Pageable pageable);

    long countAdminPopUpStores(String query);
}
