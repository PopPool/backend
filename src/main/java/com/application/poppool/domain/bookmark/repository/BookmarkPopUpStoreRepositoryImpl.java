package com.application.poppool.domain.bookmark.repository;

import com.application.poppool.domain.user.dto.UserBookmarkCountByPopUpStore;
import com.application.poppool.domain.user.dto.UserCommentCountByPopUpStore;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
;import static com.application.poppool.domain.comment.entity.QCommentEntity.commentEntity;
import static com.application.poppool.domain.popup.entity.QPopUpStoreEntity.popUpStoreEntity;
import static com.application.poppool.domain.user.entity.QBookmarkPopUpStoreEntity.bookmarkPopUpStoreEntity;

@Repository
@RequiredArgsConstructor
public class BookmarkPopUpStoreRepositoryImpl implements BookmarkPopUpStoreRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserBookmarkCountByPopUpStore> findBookmarkCountGroupedByPopupStore(String userId) {
        return queryFactory.select(Projections.bean(UserBookmarkCountByPopUpStore.class,
                bookmarkPopUpStoreEntity.popUpStore.as("popUpStore"),
                bookmarkPopUpStoreEntity.count().as("bookmarkCount")
                ))
                .from(bookmarkPopUpStoreEntity)
                .join(bookmarkPopUpStoreEntity.popUpStore, popUpStoreEntity)
                .where(bookmarkUserIdEq(userId))
                .groupBy(bookmarkPopUpStoreEntity.popUpStore)
                .fetch();
    }

    private BooleanExpression bookmarkUserIdEq(String userId) {
        return userId != null ? bookmarkPopUpStoreEntity.user.userId.eq(userId) : null;
    }

}
