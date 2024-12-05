package com.application.poppool.domain.home.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAllPopularPopUpStoresResponse {

    private List<GetHomeInfoResponse.PopUpStore> popularPopUpStoreList;
    private int popularPopUpStoreTotalPages; // 인기 팝업 리스트 페이지 수
    private long popularPopUpStoreTotalElements; // 인기 팝업 리스트 전체 개수

}
