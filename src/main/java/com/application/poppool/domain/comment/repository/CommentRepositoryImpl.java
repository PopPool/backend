package com.application.poppool.domain.comment.repository;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.user.dto.response.GetMyCommentResponse;
import com.application.poppool.domain.user.dto.response.GetMyPageResponse;
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

    @Override
    public List<CommentEntity> findAllPopUpStoreComments(String userId, boolean isInstagram, Long popUpStoreId) {
        return queryFactory.selectFrom(commentEntity)
                .join(commentEntity.user, userEntity).fetchJoin()
                .leftJoin(blockedUserEntity)
                .on(blockUserIdEq(userId),
                        blockedUserEntity.blockedUser.userId.eq(commentEntity.user.userId))
                .where(popUpStoreIdEq(popUpStoreId),
                        blockedUserEntity.id.isNull(),
                        isInstagramEq(isInstagram)) // 차단된 유저가 아닌 경우(조인 시, 차단조건에 해당하지 않는 조건 = 차단조건에 일치하는 행이 없다.)
                .fetch();
    }

    @Override
    public List<GetMyPageResponse.MyCommentedPopUpInfo> findMyCommentedPopUpInfo(String userId) {
        return queryFactory
                .select(Projections.bean(
                        GetMyPageResponse.MyCommentedPopUpInfo.class,
                        popUpStoreEntity.id.as("popUpStoreId"),
                        popUpStoreEntity.name.as("popUpStoreName"),
                        popUpStoreEntity.mainImageUrl.as("mainImageUrl")
                ))
                .from(commentEntity)
                .join(commentEntity.popUpStore, popUpStoreEntity).fetchJoin()
                .where(commentEntity.user.userId.eq(userId))
                .orderBy(commentEntity.createDateTime.desc())
                .fetch();
    }

    @Override
    public List<GetMyCommentResponse.MyCommentInfo> findByMyCommentsWithPopUpStore(String userId, boolean isInstagram, Pageable pageable) {
        return queryFactory.select(Projections.bean(GetMyCommentResponse.MyCommentInfo.class,
                        commentEntity.id.as("commentId"),
                        commentEntity.content.as("content"),
                        commentEntity.likeCount.as("likeCount"),
                        commentEntity.createDateTime.as("createDateTime"),
                        Projections.bean(
                                GetMyCommentResponse.MyCommentedPopUpInfo.class,
                                popUpStoreEntity.id.as("popUpStoreId"),
                                popUpStoreEntity.name.as("popUpStoreName"),
                                popUpStoreEntity.mainImageUrl.as("mainImageUrl"),
                                popUpStoreEntity.isClosed.as("isClosed")
                        ).as("popUpStoreInfo")
                ))
                .from(commentEntity)
                .join(commentEntity.popUpStore, popUpStoreEntity).fetchJoin()
                .where(commentUserIdEq(userId),
                        isInstagramEq(isInstagram))
                .orderBy(QueryDslUtils.getOrderSpecifiers(pageable, commentEntity).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public long countMyComments(String userId) {
        Long count = queryFactory
                .select(commentEntity.count())
                .from(commentEntity)
                .where(commentEntity.user.userId.eq(userId))
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

    private BooleanExpression isInstagramEq(boolean isInstagram) {
        return commentEntity.isInstagram.eq(isInstagram);
    }


}
