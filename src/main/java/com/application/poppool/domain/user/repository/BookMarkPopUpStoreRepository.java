package com.application.poppool.domain.user.repository;

import com.application.poppool.domain.user.entity.BookMarkPopUpStoreEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMarkPopUpStoreRepository extends JpaRepository<BookMarkPopUpStoreEntity, Long> {

    @Query(value = "SELECT bmps FROM BookMarkPopUpStoreEntity bmps JOIN FETCH bmps.popUpStore WHERE bmps.user = :user ORDER BY bmps.updateDateTime DESC",
            countQuery = "SELECT COUNT(bmps) FROM BookMarkPopUpStoreEntity bmps WHERE bmps.user = :user")
    Page<BookMarkPopUpStoreEntity> findBookMarkPopUpStoresByUser(@Param("user") UserEntity user, Pageable pageable);

}
