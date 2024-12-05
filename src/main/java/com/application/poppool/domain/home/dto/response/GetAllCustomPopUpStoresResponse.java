package com.application.poppool.domain.home.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAllCustomPopUpStoresResponse {

    private List<GetHomeInfoResponse.PopUpStore> customPopUpStoreList;
    private int customPopUpStoreTotalPages; // 맞춤 팝업 리스트 페이지 수
    private long customPopUpStoreTotalElements; // 맞춤 팝업 리스트 전체 개수

}
