package com.application.poppool.domain.image.repository;

import com.application.poppool.domain.image.entity.PopUpStoreImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopUpStoreImageRepository extends JpaRepository<PopUpStoreImageEntity, Long> {
}
