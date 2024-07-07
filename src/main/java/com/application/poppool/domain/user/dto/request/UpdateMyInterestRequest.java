package com.application.poppool.domain.user.dto.request;

import lombok.Getter;

import java.util.List;


@Getter
public class UpdateMyInterestRequest {

    private List<Long> interestsToAdd;
    private List<Long> interestsToDelete;
    private List<Long> interestsToKeep;

}
