package com.application.poppool.domain.location.service;

import com.application.poppool.domain.category.enums.Category;
import com.application.poppool.domain.location.dto.response.GetViewBoundPopUpStoreListResponse;
import com.application.poppool.domain.location.dto.response.SearchPopUpStoreByMapResponse;
import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.popup.repository.PopUpStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final PopUpStoreRepository popUpStoreRepository;

    /**
     * 지도로 팝업스토어 검색
     * @param category
     * @param query
     * @return
     */
    @Transactional(readOnly = true)
    public SearchPopUpStoreByMapResponse searchPopUpStoreByMap(Category category, String query) {
        List<PopUpStoreEntity> popUpStoreEntityList = popUpStoreRepository.searchPopUpStoreByMap(category, query);


        List<SearchPopUpStoreByMapResponse.PopUpStore> popUpStoreList = popUpStoreEntityList.stream()
                .map(popUpStoreEntity -> SearchPopUpStoreByMapResponse.PopUpStore.builder()
                        .id(popUpStoreEntity.getId())
                        .category(popUpStoreEntity.getCategory())
                        .name(popUpStoreEntity.getName())
                        .address(popUpStoreEntity.getAddress())
                        .startDate(popUpStoreEntity.getStartDate())
                        .endDate(popUpStoreEntity.getEndDate())
                        .latitude(popUpStoreEntity.getLocation().getLatitude())
                        .longitude(popUpStoreEntity.getLocation().getLongitude())
                        .markerId(popUpStoreEntity.getLocation().getId())
                        .markerTitle(popUpStoreEntity.getLocation().getMarkerTitle())
                        .markerSnippet(popUpStoreEntity.getLocation().getMarkerSnippet())
                        .build())
                .toList();


        return SearchPopUpStoreByMapResponse.builder().popUpStoreList(popUpStoreList).build();
    }

    /**
     * 뷰 바운즈 내 팝업 스토어 정보 조회
     * @param category
     * @param northEastLat
     * @param northEastLon
     * @param southWestLat
     * @param southWestLon
     * @return
     */
    @Transactional(readOnly = true)
    public GetViewBoundPopUpStoreListResponse getViewBoundPopUpStoreList(Category category, double northEastLat, double northEastLon, double southWestLat, double southWestLon) {
        List<PopUpStoreEntity> popUpStoreEntityList = popUpStoreRepository.getViewBoundPopUpStoreList(category, northEastLat, northEastLon, southWestLat, southWestLon);

        List<GetViewBoundPopUpStoreListResponse.PopUpStore> popUpStoreList = popUpStoreEntityList.stream()
                .map(popUpStoreEntity -> GetViewBoundPopUpStoreListResponse.PopUpStore.builder()
                        .id(popUpStoreEntity.getId())
                        .category(popUpStoreEntity.getCategory())
                        .name(popUpStoreEntity.getName())
                        .address(popUpStoreEntity.getAddress())
                        .startDate(popUpStoreEntity.getStartDate())
                        .endDate(popUpStoreEntity.getEndDate())
                        .latitude(popUpStoreEntity.getLocation().getLatitude())
                        .longitude(popUpStoreEntity.getLocation().getLongitude())
                        .markerId(popUpStoreEntity.getLocation().getId())
                        .markerTitle(popUpStoreEntity.getLocation().getMarkerTitle())
                        .markerSnippet(popUpStoreEntity.getLocation().getMarkerSnippet())
                        .build())
                .toList();

        return GetViewBoundPopUpStoreListResponse.builder().popUpStoreList(popUpStoreList).build();
    }

}
