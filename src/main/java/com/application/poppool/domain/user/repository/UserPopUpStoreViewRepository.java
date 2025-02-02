package com.application.poppool.domain.user.repository;

import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.UserPopUpStoreViewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPopUpStoreViewRepository extends JpaRepository<UserPopUpStoreViewEntity, Long> {

    @Query("SELECT DISTINCT upsv.popUpStore FROM UserPopUpStoreViewEntity upsv WHERE upsv.user.userId = :userId")
    Page<PopUpStoreEntity> findRecentViewPopUpStoresByUserId(@Param("userId") String userId, Pageable pageable);

    Optional<UserPopUpStoreViewEntity> findByUserAndPopUpStore(UserEntity user, PopUpStoreEntity popUpStore);
    
    /** 팝업 스토어 삭제 시, 일괄적으로 관련 팝업스토어의 뷰 데이터 삭제하기 위한 조회 메서드 */
    List<UserPopUpStoreViewEntity> findByPopUpStore(PopUpStoreEntity popUpStore);

    /** 회원 탈퇴 시, 일괄적으로 해당 회원 관련 팝업스토어 뷰 데이터 삭제를 위한 조회 메서드 */
    List<UserPopUpStoreViewEntity> findByUser(UserEntity user);


}
