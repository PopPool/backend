package com.application.poppool.domain.popup.repository;

import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PopUpStoreRepository extends JpaRepository<PopUpStoreEntity,Long> {
    @Query("SELECT DISTINCT p FROM PopUpStoreEntity p JOIN p.comments c WHERE c.user.userId = :userId")
    Page<PopUpStoreEntity> findAllByUserComment(@Param("userId") String userId, Pageable pageable);
}
