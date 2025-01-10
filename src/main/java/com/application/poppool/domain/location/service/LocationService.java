package com.application.poppool.domain.location.service;

import com.application.poppool.domain.location.dto.response.GetViewBoundPopUpStoreListResponse;
import com.application.poppool.domain.location.dto.response.SearchPopUpStoreByMapResponse;
import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.popup.repository.PopUpStoreRepository;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.repository.BookMarkPopUpStoreRepository;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.global.exception.ErrorCode;
import com.application.poppool.global.exception.NotFoundException;
import com.application.poppool.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final PopUpStoreRepository popUpStoreRepository;
    private final UserRepository userRepository;
    private final BookMarkPopUpStoreRepository bookMarkPopUpStoreRepository;

    /**
     * 지도로 팝업스토어 검색
     *
     * @param categories
     * @param query
     * @return
     */
    @Transactional(readOnly = true)
    public SearchPopUpStoreByMapResponse searchPopUpStoreByMap(String userId, List<Integer> categories, String query) {
        /** 로그인 여부 체크 */
        boolean loginYn = false;

        if (SecurityUtils.isAuthenticated()) {
            loginYn = true;
        }

        List<PopUpStoreEntity> popUpStoreEntityList = popUpStoreRepository.searchPopUpStoreByMap(categories, query);

        List<SearchPopUpStoreByMapResponse.PopUpStore> popUpStoreList = popUpStoreEntityList.stream()
                .map(popUpStoreEntity -> {
                    boolean bookmarkYn = bookMarkPopUpStoreRepository.existsByUserIdAndPopUpStore(userId, popUpStoreEntity);

                    return SearchPopUpStoreByMapResponse.PopUpStore.builder()
                            .id(popUpStoreEntity.getId())
                            .categoryName(popUpStoreEntity.getCategory().getCategoryName())
                            .name(popUpStoreEntity.getName())
                            .address(popUpStoreEntity.getAddress())
                            .mainImageUrl(popUpStoreEntity.getMainImageUrl())
                            .startDate(popUpStoreEntity.getStartDate())
                            .endDate(popUpStoreEntity.getEndDate())
                            .latitude((popUpStoreEntity.getLocation() != null) ? popUpStoreEntity.getLocation().getLatitude() : 0.0)
                            .longitude((popUpStoreEntity.getLocation() != null) ? popUpStoreEntity.getLocation().getLongitude() : 0.0)
                            .markerId((popUpStoreEntity.getLocation() != null) ? popUpStoreEntity.getLocation().getId() : null)
                            .markerTitle((popUpStoreEntity.getLocation() != null) ? popUpStoreEntity.getLocation().getMarkerTitle() : null)
                            .markerSnippet((popUpStoreEntity.getLocation() != null) ? popUpStoreEntity.getLocation().getMarkerSnippet() : null)
                            .bookmarkYn(bookmarkYn)
                            .build();
                })
                .toList();


        return SearchPopUpStoreByMapResponse.builder()
                .popUpStoreList(popUpStoreList)
                .loginYn(loginYn)
                .build();
    }

    /**
     * 뷰 바운즈 내 팝업 스토어 정보 조회
     *
     * @param categories
     * @param northEastLat
     * @param northEastLon
     * @param southWestLat
     * @param southWestLon
     * @return
     */
    @Transactional(readOnly = true)
    public GetViewBoundPopUpStoreListResponse getViewBoundPopUpStoreList(List<Integer> categories, double northEastLat, double northEastLon, double southWestLat, double southWestLon) {
        List<PopUpStoreEntity> popUpStoreEntityList = popUpStoreRepository.getViewBoundPopUpStoreList(categories, northEastLat, northEastLon, southWestLat, southWestLon);

        List<GetViewBoundPopUpStoreListResponse.PopUpStore> popUpStoreList = popUpStoreEntityList.stream()
                .map(popUpStoreEntity -> GetViewBoundPopUpStoreListResponse.PopUpStore.builder()
                        .id(popUpStoreEntity.getId())
                        .categoryName(popUpStoreEntity.getCategory().getCategoryName())
                        .name(popUpStoreEntity.getName())
                        .address(popUpStoreEntity.getAddress())
                        .mainImageUrl(popUpStoreEntity.getMainImageUrl())
                        .startDate(popUpStoreEntity.getStartDate())
                        .endDate(popUpStoreEntity.getEndDate())
                        .latitude((popUpStoreEntity.getLocation() != null) ? popUpStoreEntity.getLocation().getLatitude() : 0.0)
                        .longitude((popUpStoreEntity.getLocation() != null) ? popUpStoreEntity.getLocation().getLongitude() : 0.0)
                        .markerId((popUpStoreEntity.getLocation() != null) ? popUpStoreEntity.getLocation().getId() : null)
                        .markerTitle((popUpStoreEntity.getLocation() != null) ? popUpStoreEntity.getLocation().getMarkerTitle() : null)
                        .markerSnippet((popUpStoreEntity.getLocation() != null) ? popUpStoreEntity.getLocation().getMarkerSnippet() : null)
                        .build())
                .toList();

        return GetViewBoundPopUpStoreListResponse.builder().popUpStoreList(popUpStoreList).build();
    }

}
