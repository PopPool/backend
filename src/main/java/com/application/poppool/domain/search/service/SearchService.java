package com.application.poppool.domain.search.service;

import com.application.poppool.domain.popup.repository.PopUpStoreRepository;
import com.application.poppool.domain.search.dto.SearchPopUpStoreResponse;
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
    public SearchPopUpStoreResponse searchPopUpStore(String query) {
        if (query.length() < 2) { // 검색어가 두 글자 이상인 경우에만 검색 진행
            return null;
        }

        List<SearchPopUpStoreResponse.PopUpStore> popUpStoreList = popUpStoreRepository.searchPopUpStore(query);

        return SearchPopUpStoreResponse.builder().popUpStoreList(popUpStoreList).build();
    }

}
