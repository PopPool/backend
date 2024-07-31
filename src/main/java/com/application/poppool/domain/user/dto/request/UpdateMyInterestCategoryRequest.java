package com.application.poppool.domain.user.dto.request;

import lombok.Getter;

import java.util.List;


@Getter
public class UpdateMyInterestCategoryRequest {

    private List<Long> interestCategoriesToAdd;
    private List<Long> interestCategoriesToDelete;
    private List<Long> interestCategoriesToKeep;

}
