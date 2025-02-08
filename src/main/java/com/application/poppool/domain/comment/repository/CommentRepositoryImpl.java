package com.application.poppool.domain.comment.repository;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.comment.enums.CommentType;
import com.application.poppool.domain.user.dto.UserCommentCountByPopUpStore;
import com.application.poppool.global.enums.CommentSortCode;
import com.application.poppool.global.utils.QueryDslUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.application.poppool.domain.comment.entity.QCommentEntity.commentEntity;
import static com.application.poppool.domain.popup.entity.QPopUpStoreEntity.popUpStoreEntity;
import static com.application.poppool.domain.user.entity.QBlockedUserEntity.blockedUserEntity;
import static com.application.poppool.domain.user.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * 처음 상세 페이지에 들어왔을 때, 최대 3개만 조회되도록 함(비로그인 경우는 1개)
     * @param userId
     * @param commentType
     * @param popUpStoreId
     * @return
     */
    @Override
    public List<CommentEntity> getPopUpStoreComments(String userId, CommentType commentType, Long popUpStoreId) {
        long commentCount = 3L;
        if (userId.equals("GUEST")) {
            commentCount = 1L;
        }
        return queryFactory.selectFrom(commentEntity)
                .join(commentEntity.user, userEntity).fetchJoin()
                .leftJoin(blockedUserEntity)
                .on(blockUserIdEq(userId),
                        blockedUserEntity.blockedUser.userId.eq(commentEntity.user.userId))
                .where(popUpStoreIdEq(popUpStoreId),
                        blockedUserEntity.id.isNull(), // 차단된 유저가 아닌 경우(조인 시, 차단조건에 해당하지 않는 조건 = 차단조건에 일치하는 행이 없다.)
                        commentTypeEq(commentType))
                .limit(commentCount) // 최대 3개
                .orderBy(commentEntity.createDateTime.desc())
                .fetch();
    }

    @Override
    public List<CommentEntity> getAllPopUpStoreComments(String userId, CommentType commentType, Long popUpStoreId, Pageable pageable) {
        return queryFactory.selectFrom(commentEntity)
                .join(commentEntity.user, userEntity).fetchJoin()
                .leftJoin(blockedUserEntity)
                .on(blockUserIdEq(userId),
                        blockedUserEntity.blockedUser.userId.eq(commentEntity.user.userId))
                .where(popUpStoreIdEq(popUpStoreId),
                        blockedUserEntity.id.isNull(),
                        commentTypeEq(commentType)) // 차단된 유저가 아닌 경우(조인 시, 차단조건에 해당하지 않는 조건 = 차단조건에 일치하는 행이 없다.)
                .orderBy(commentEntity.createDateTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<CommentEntity> findMyCommentsWithPopUpStore(String userId, CommentType commentType, List<CommentSortCode> sortCodes, Pageable pageable) {
        return queryFactory.selectFrom(commentEntity)
                .join(commentEntity.popUpStore, popUpStoreEntity).fetchJoin()
                .where(commentUserIdEq(userId),
                        commentTypeEq(commentType))
                .groupBy(commentEntity.popUpStore)
                .orderBy(QueryDslUtils.getOrderSpecifiers(sortCodes, pageable, commentEntity).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }


    @Override
    public long countMyComments(String userId, CommentType commentType) {
        Long count = queryFactory
                .select(commentEntity.count())
                .from(commentEntity)
                .where(commentUserIdEq(userId),
                        commentTypeEq(commentType))
                .fetchOne();
        return count != null ? count : 0L;
    }

    public List<UserCommentCountByPopUpStore> findCommentCountGroupedByPopupStore(String userId) {
        return queryFactory.select(Projections.bean(UserCommentCountByPopUpStore.class,
                        commentEntity.popUpStore.as("popUpStore"),
                        commentEntity.count().as("commentCount")
                ))
                .from(commentEntity)
                .join(commentEntity.popUpStore, popUpStoreEntity)
                .where(commentUserIdEq(userId))
                .groupBy(commentEntity.popUpStore)
                .fetch();
    }

    /**
     * 다른 사람의 코멘트와 그 코멘트가 달린 팝업 스토어 정보 조회
     * @param userId
     * @param commentType
     * @param pageable
     * @return
     */
    @Override
    public List<CommentEntity> findCommenterCommentsWithPopUpStore(String userId, CommentType commentType, Pageable pageable) {
        return queryFactory.selectFrom(commentEntity)
                .join(commentEntity.popUpStore, popUpStoreEntity).fetchJoin()
                .where(commentUserIdEq(userId),
                        commentTypeEq(commentType))
                .groupBy(commentEntity.popUpStore)
                //.orderBy(QueryDslUtils.getOrderSpecifiers(sortCodes, pageable, commentEntity).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public long countCommenterComments(String userId, CommentType commentType) {
        Long count = queryFactory
                .select(commentEntity.count())
                .from(commentEntity)
                .where(commentUserIdEq(userId),
                        commentTypeEq(commentType))
                .fetchOne();
        return count != null ? count : 0L;
    }

    private BooleanExpression blockUserIdEq(String userId) {
        return userId != null ? blockedUserEntity.user.userId.eq(userId) : null;
    }

    private BooleanExpression popUpStoreIdEq(Long popUpStoreId) {
        return popUpStoreId != null ? commentEntity.popUpStore.id.eq(popUpStoreId) : null;
    }

    private BooleanExpression commentUserIdEq(String userId) {
        return userId != null ? commentEntity.user.userId.eq(userId) : null;
    }

    public BooleanExpression commentTypeEq(CommentType commentType) {
        return commentType != null ? commentEntity.commentType.eq(commentType) : null;
    }


}
