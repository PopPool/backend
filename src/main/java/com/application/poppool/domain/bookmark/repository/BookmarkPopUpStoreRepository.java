package com.application.poppool.domain.bookmark.repository;

import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.user.entity.BookmarkPopUpStoreEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkPopUpStoreRepository extends JpaRepository<BookmarkPopUpStoreEntity, Long>, BookmarkPopUpStoreRepositoryCustom{


    @Query(value = "SELECT bp FROM BookmarkPopUpStoreEntity bp JOIN FETCH bp.popUpStore WHERE bp.user = :user",
            countQuery = "SELECT COUNT(bp) FROM BookmarkPopUpStoreEntity bp WHERE bp.user = :user")
    Page<BookmarkPopUpStoreEntity> findBookmarkPopUpStoresByUser(@Param("user") UserEntity user, Pageable pageable);

    boolean existsByUserAndPopUpStore(UserEntity user, PopUpStoreEntity popUpStore);

    @Query("SELECT CASE WHEN COUNT(bp) > 0 THEN true ELSE false END " +
            "FROM BookmarkPopUpStoreEntity bp " +
            "WHERE bp.user.id = :userId AND bp.popUpStore = :popUpStore")
    boolean existsByUserIdAndPopUpStore(@Param("userId") String userId, @Param("popUpStore") PopUpStoreEntity popUpStore);

    /**
     * FindByFK (외래키로 조회)
     */
    // findBy + "FK가 참조하는 엔티티명" + "_" + "FK가 참조하는 엔티티의 ID 필드명(첫글자 대문자)"
    Optional<BookmarkPopUpStoreEntity> findByUser_UserIdAndPopUpStore_Id(String userId, Long popUpStoreId);

}
