package com.application.poppool.domain.bookmark.repository;

import com.application.poppool.domain.user.dto.UserBookmarkCountByPopUpStore;

import java.util.List;

public interface BookmarkPopUpStoreRepositoryCustom {

    List<UserBookmarkCountByPopUpStore> findBookmarkCountGroupedByPopupStore(String userId);
}
