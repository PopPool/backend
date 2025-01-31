package com.application.poppool.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class UpdateMyInterestCategoryRequest {

    @Schema(description = "새로 추가할 관심 카테고리")
    private List<Integer> interestCategoriesToAdd = new ArrayList<>();
    @Schema(description = "삭제할 관심 카테고리")
    private List<Integer> interestCategoriesToDelete = new ArrayList<>();
    private List<Integer> interestCategoriesToKeep = new ArrayList<>();

}
