package com.application.poppool.domain.popup.repository;

import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.UserInterestCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface PopUpStoreRepositoryCustom {

    Page<GetHomeInfoResponse.CustomPopUpStores> getCustomPopUpStoreListByCategory(String userId, Pageable pageable, List<UserInterestCategoryEntity> userInterestCategoryEntityList);
    Page<GetHomeInfoResponse.CustomPopUpStores> getCustomPopUpStoreListByGenderAndAge(String userId, Pageable pageable);
    Page<GetHomeInfoResponse.CustomPopUpStores> getCustomPopUpStoreListByAge(String userId, Pageable pageable);
    Page<GetHomeInfoResponse.PopularPopUpStores> getPopularPopUpStoreList(Pageable pageable);
    Page<GetHomeInfoResponse.NewPopUpStores> getNewPopUpStoreList(LocalDateTime currentDate, Pageable pageable);

}
