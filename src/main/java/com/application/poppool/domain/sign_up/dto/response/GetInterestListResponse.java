package com.application.poppool.domain.sign_up.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetInterestListResponse {

    private List<InterestResponse> interestResponseList;

    @Getter
    @Builder
    public static class InterestResponse {
        private String interestName;
    }

}
