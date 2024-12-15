package com.application.poppool.domain.popup.dto.resonse;

import com.application.poppool.domain.comment.dto.response.GetCommentsResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAllPopUpStoreCommentsResponse {

    List<GetCommentsResponse.Comment> commentList;

}
