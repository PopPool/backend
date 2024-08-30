package com.application.poppool.domain.popup.repository;

import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PopUpStoreRepository extends JpaRepository<PopUpStoreEntity, Long>, PopUpStoreRepositoryCustom {
    @NonNull
    Optional<PopUpStoreEntity> findById(@NonNull Long popUpStoreId);
}
