package com.application.poppool.domain.user.repository;

import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.user.entity.UserPopUpStoreViewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPopUpStoreViewRepository extends JpaRepository<UserPopUpStoreViewEntity, Long> {

    @Query("SELECT DISTINCT upsv.popUpStore FROM UserPopUpStoreViewEntity upsv WHERE upsv.user.userId = :userId ORDER BY upsv.viewedAt DESC")
    Page<PopUpStoreEntity> findRecentViewPopUpStoresByUserId(@Param("userId") String userId, Pageable pageable);
}
