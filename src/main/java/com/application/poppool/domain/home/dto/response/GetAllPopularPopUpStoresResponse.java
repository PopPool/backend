package com.application.poppool.domain.home.dto.response;

import com.application.poppool.domain.category.enums.Category;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetAllPopularPopUpStoresResponse {

    private List<GetHomeInfoResponse.PopUpStore> popularPopUpStoreList;
    private int popularPopUpStoreTotalPages; // 인기 팝업 리스트 페이지 수
    private long popularPopUpStoreTotalElements; // 인기 팝업 리스트 전체 개수

}
