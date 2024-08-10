package com.application.poppool.domain.popup.repository;

import com.application.poppool.domain.category.enums.Category;
import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import com.application.poppool.domain.search.dto.SearchPopUpStoreByMapResponse;
import com.application.poppool.domain.search.dto.SearchPopUpStoreResponse;
import com.application.poppool.domain.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface PopUpStoreRepositoryCustom {

    List<Category> getUserInterestCategoryList(String userId);
    List<GetHomeInfoResponse.PopUpStore> getCustomPopUpStoreList(UserEntity user, Pageable pageable);
    long countCustomPopUpStores(UserEntity user);
    List<GetHomeInfoResponse.PopUpStore> getPopularPopUpStoreList(Pageable pageable);
    long countPopularPopUpStores();
    List<GetHomeInfoResponse.PopUpStore> getNewPopUpStoreList(LocalDateTime currentDate, Pageable pageable);
    long countNewPopUpStores();


    // 팝업스토어 검색
    List<SearchPopUpStoreResponse.PopUpStore> searchPopUpStore(String query);
    List<SearchPopUpStoreByMapResponse.PopUpStore> searchPopUpStoreByMap(Category category, String query);

}
