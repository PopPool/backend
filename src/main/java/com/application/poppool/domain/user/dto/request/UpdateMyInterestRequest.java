package com.application.poppool.domain.user.dto.request;

import com.application.poppool.domain.interest.enums.InterestType;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdateMyInterestRequest {

    private List<InterestType> interestsToAdd;
    private List<InterestType> interestsToDelete;
    private List<InterestType> interestsToKeep;

}
