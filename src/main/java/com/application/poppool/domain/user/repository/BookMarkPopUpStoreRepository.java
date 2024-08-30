package com.application.poppool.domain.user.repository;

import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.user.entity.BookMarkPopUpStoreEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookMarkPopUpStoreRepository extends JpaRepository<BookMarkPopUpStoreEntity, Long> {

    @Query(value = "SELECT bmps FROM BookMarkPopUpStoreEntity bmps JOIN FETCH bmps.popUpStore WHERE bmps.user = :user ORDER BY bmps.createDateTime DESC",
            countQuery = "SELECT COUNT(bmps) FROM BookMarkPopUpStoreEntity bmps WHERE bmps.user = :user")
    Page<BookMarkPopUpStoreEntity> findBookMarkPopUpStoresByUser(@Param("user") UserEntity user, Pageable pageable);

    boolean existsByUserAndPopUpStore(UserEntity user, PopUpStoreEntity popUpStore);

    /**
     * FindByFK (외래키로 조회)
     */
    // findBy + "FK가 참조하는 엔티티명" + "_" + "FK가 참조하는 엔티티의 ID 필드명(첫글자 대문자)"
    Optional<BookMarkPopUpStoreEntity> findByUser_UserIdAndPopUpStore_Id(String userId, Long popUpStoreId);

}
