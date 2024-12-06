package com.application.poppool.domain.search.service;

import com.application.poppool.domain.popup.repository.PopUpStoreRepository;
import com.application.poppool.domain.search.dto.SearchPopUpStoreResponse;
import com.application.poppool.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final PopUpStoreRepository popUpStoreRepository;

    /**
     * 통합 검색
     *
     * @param query
     * @return
     */
    @Transactional(readOnly = true)
    public SearchPopUpStoreResponse searchPopUpStore(String userId, String query) {
        /** 로그인 여부 체크 */
        boolean loginYn = false;
        if (SecurityUtils.isAuthenticated()) {
            loginYn = true;
        }

        List<SearchPopUpStoreResponse.PopUpStore> popUpStoreList = popUpStoreRepository.searchPopUpStore(userId, query);

        return SearchPopUpStoreResponse.builder()
                .popUpStoreList(popUpStoreList)
                .loginYn(loginYn)
                .build();
    }

}
