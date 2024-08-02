package com.application.poppool.domain.popup.repository;

import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface PopUpStoreRepositoryCustom {

    Page<GetHomeInfoResponse.NewPopUpStores> getNewPopUpStoreList(LocalDateTime currentDate, Pageable pageable);

}
