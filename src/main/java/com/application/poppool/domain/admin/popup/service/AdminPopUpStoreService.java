package com.application.poppool.domain.admin.popup.service;

import com.application.poppool.domain.admin.popup.dto.request.CreatePopUpStoreRequest;
import com.application.poppool.domain.admin.popup.dto.request.UpdatePopUpStoreRequest;
import com.application.poppool.domain.admin.popup.dto.response.GetAdminPopUpStoreDetailResponse;
import com.application.poppool.domain.admin.popup.dto.response.GetAdminPopUpStoreListResponse;
import com.application.poppool.domain.category.entity.CategoryEntity;
import com.application.poppool.domain.category.repository.CategoryRepository;
import com.application.poppool.domain.image.entity.PopUpStoreImageEntity;
import com.application.poppool.domain.location.entity.LocationEntity;
import com.application.poppool.domain.location.repository.LocationRepository;
import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.popup.repository.PopUpStoreRepository;
import com.application.poppool.global.exception.ErrorCode;
import com.application.poppool.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminPopUpStoreService {

    private final PopUpStoreRepository popUpStoreRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    /**
     * 관리자 페이지의 팝업 스토어 리스트 조회
     *
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public GetAdminPopUpStoreListResponse getAdminPopUpStoreList(String query, Pageable pageable) {
        List<GetAdminPopUpStoreListResponse.PopUpStore> popUpStoreList = popUpStoreRepository
                .getAdminPopUpStoreList(query, pageable);

        // 전체 맞춤 팝업 데이터 수
        long totalElements = popUpStoreRepository.countAdminPopUpStores(query);

        // 전체 맞춤 팝업 페이지 수
        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());

        return GetAdminPopUpStoreListResponse.builder()
                .popUpStoreList(popUpStoreList)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }


    /**
     * 관리자 페이지의 팝업 스토어 상세 조회
     *
     * @param popUpStoreId
     * @return
     */
    @Transactional(readOnly = true)
    public GetAdminPopUpStoreDetailResponse getAdminPopUpStoreDetail(Long popUpStoreId) {
        PopUpStoreEntity popUpStore = popUpStoreRepository.findById(popUpStoreId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POPUP_STORE_NOT_FOUND));

        LocationEntity location = popUpStore.getLocation();

        List<GetAdminPopUpStoreDetailResponse.Image> popUpStoreImageList = popUpStore.getImages().stream()
                .map(popUpStoreImageEntity -> GetAdminPopUpStoreDetailResponse.Image.builder()
                        .id(popUpStoreImageEntity.getId())
                        .imageUrl(popUpStoreImageEntity.getUrl())
                        .build())
                .toList();


        return GetAdminPopUpStoreDetailResponse.builder()
                .id(popUpStore.getId())
                .name(popUpStore.getName())
                .categoryId(popUpStore.getCategory().getCategoryId())
                .categoryName(popUpStore.getCategory().getCategoryName())
                .desc(popUpStore.getDesc())
                .address(popUpStore.getAddress())
                .startDate(popUpStore.getStartDate())
                .endDate(popUpStore.getEndDate())
                .createUserId(popUpStore.getCreator())
                .createDateTime(popUpStore.getCreateDateTime())
                .mainImageUrl(popUpStore.getMainImageUrl())
                .bannerYn(popUpStore.isBannerYn())
                .imageList(popUpStoreImageList)
                .latitude((location != null) ? location.getLatitude() : 0.0)
                .longitude((location != null) ? location.getLongitude() : 0.0)
                .markerTitle((location != null) ? location.getMarkerTitle() : null)
                .markerSnippet((location != null) ? location.getMarkerSnippet() : null)
                .build();
    }


    /**
     * 팝업 스토어 등록
     *
     * @param request
     */
    @Transactional
    public void createPopUpStore(CreatePopUpStoreRequest request) {
        LocationEntity location = LocationEntity.builder()
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .markerTitle(request.getMarkerTitle())
                .markerSnippet(request.getMarkerSnippet())
                .build();

        CategoryEntity category = categoryRepository.findByCategoryId(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND));

        PopUpStoreEntity popUpStore = PopUpStoreEntity.builder()
                .name(request.getName())
                .category(category)
                .desc(request.getDesc())
                .address(request.getAddress())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .mainImageUrl(request.getMainImageUrl())
                .bannerYn(request.isBannerYn())
                .location(location)
                .build();


        /** 코멘트 이미지 엔티티 생성 및 저장 */
        for (String url : request.getImageUrlList()) {
            PopUpStoreImageEntity popUpStoreImage = createPopUpStoreImage(popUpStore, url);
            // 연관관계 편의 메소드(양방향으로 값 셋팅)
            popUpStore.addImage(popUpStoreImage);
        }

        popUpStoreRepository.save(popUpStore);
        locationRepository.save(location);
    }

    /**
     * 팝업 스토어 수정
     *
     * @param request
     */
    @Transactional
    public void updatePopUpStore(UpdatePopUpStoreRequest request) {
        PopUpStoreEntity popUpStore = popUpStoreRepository.findById(request.getPopUpStore().getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.POPUP_STORE_NOT_FOUND));

        LocationEntity location = popUpStore.getLocation();

        CategoryEntity toBeUpdateCategory = categoryRepository.findByCategoryId(request.getPopUpStore().getCategoryId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND));

        popUpStore.updatePopUpStore(request.getPopUpStore(), toBeUpdateCategory);
        location.updateLocation(request.getLocation());


        // 삭제할 이미지 처리
        List<PopUpStoreImageEntity> imagesToDelete = popUpStore.getImages().stream()
                .filter(image -> request.getImagesToDelete().contains(image.getId()))
                .toList();

        popUpStore.getImages().removeAll(imagesToDelete);

        // 추가할 이미지 처리
        List<PopUpStoreImageEntity> imagesToAdd = request.getImagesToAdd().stream()
                .map(url -> createPopUpStoreImage(popUpStore, url))
                .toList();

        popUpStore.getImages().addAll(imagesToAdd);

    }

    /**
     * 팝업 스토어 삭제
     *
     * @param popUpStoreId
     */
    @Transactional
    public void deletePopUpStore(Long popUpStoreId) {
        PopUpStoreEntity popUpStore = popUpStoreRepository.findById(popUpStoreId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POPUP_STORE_NOT_FOUND));

        popUpStoreRepository.delete(popUpStore);

    }

    /**
     * 팝업 스토어 이미지 생성
     *
     * @param popUpStore
     * @param url
     * @return
     */
    private PopUpStoreImageEntity createPopUpStoreImage(PopUpStoreEntity popUpStore, String url) {
        return PopUpStoreImageEntity.builder()
                .popUpStore(popUpStore)
                .url(url)
                .build();
    }

}
