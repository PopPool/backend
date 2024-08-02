package com.application.poppool.domain.home.service;

import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.popup.repository.PopUpStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final PopUpStoreRepository popUpStoreRepository;

    @Transactional(readOnly = true)
    public GetHomeInfoResponse getHomeInfo(String userId) {
        LocalDateTime currentTime = LocalDateTime.now();


    }
}
