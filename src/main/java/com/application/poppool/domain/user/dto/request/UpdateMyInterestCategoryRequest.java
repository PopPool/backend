package com.application.poppool.domain.user.dto.request;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class UpdateMyInterestCategoryRequest {

    private List<Integer> interestCategoriesToAdd = new ArrayList<>();
    private List<Integer> interestCategoriesToDelete = new ArrayList<>();
    private List<Integer> interestCategoriesToKeep = new ArrayList<>();

}
