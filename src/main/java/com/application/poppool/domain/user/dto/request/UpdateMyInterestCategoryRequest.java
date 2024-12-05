package com.application.poppool.domain.user.dto.request;

import lombok.Getter;

import java.util.List;


@Getter
public class UpdateMyInterestCategoryRequest {

    private List<Integer> interestCategoriesToAdd;
    private List<Integer> interestCategoriesToDelete;
    private List<Integer> interestCategoriesToKeep;

}
