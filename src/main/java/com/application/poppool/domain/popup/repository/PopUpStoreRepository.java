package com.application.poppool.domain.popup.repository;

import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopUpStoreRepository extends JpaRepository<PopUpStoreEntity, Long> {

}
