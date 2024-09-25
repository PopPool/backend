package com.application.poppool.domain.popup.repository;

import com.application.poppool.domain.admin.popup.dto.response.GetAdminPopUpStoreListResponse;
import com.application.poppool.domain.category.enums.Category;
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

    List<Category> getUserInterestCategoryList(String userId);

    /**
     * 맞춤 팝업 리스트
     */
    List<GetHomeInfoResponse.PopUpStore> getCustomPopUpStoreList(UserEntity user, Pageable pageable);

    long countCustomPopUpStores(UserEntity user);

    /**
     * 인기 팝업 리스트
     */
    List<GetHomeInfoResponse.PopUpStore> getPopularPopUpStoreList(Pageable pageable);

    long countPopularPopUpStores();

    /**
     * 신규 팝업 리스트
     */
    List<GetHomeInfoResponse.PopUpStore> getNewPopUpStoreList(LocalDateTime currentDate, Pageable pageable);

    long countNewPopUpStores(LocalDateTime currentDate);

    /**
     * 팝업 스토어 상세 - 비슷한 팝업 리스트 조회
     */
    List<GetPopUpStoreDetailResponse.PopUpStore> getSimilarPopUpStoreList(Long popUpStoreId, Category category);

    /**
     * 검색창 하단 팝업 스토어 전체 조회
     */
    List<GetOpenPopUpStoreListResponse.PopUpStore> getOpenPopUpStoreList(List<Category> categories, Pageable pageable);

    long countOpenPopUpStores(List<Category> categories);

    List<GetClosedPopUpStoreListResponse.PopUpStore> getClosedPopUpStoreList(List<Category> categories, Pageable pageable);

    long countClosedPopUpStores(List<Category> categories);

    // 팝업스토어 검색
    List<SearchPopUpStoreResponse.PopUpStore> searchPopUpStore(String query);

    /**
     * 지도
     */
    List<PopUpStoreEntity> searchPopUpStoreByMap(List<Category> categories, String query);

    List<PopUpStoreEntity> getViewBoundPopUpStoreList(List<Category> categories, double northEastLat, double northEastLon, double southWestLat, double southWestLon);


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
