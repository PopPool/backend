package com.application.poppool.domain.user.dto;

import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentCountByPopUpStore {

    private PopUpStoreEntity popUpStore;
    private long commentCount;

}
