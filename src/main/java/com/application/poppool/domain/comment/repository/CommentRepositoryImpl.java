package com.application.poppool.domain.comment.repository;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.comment.enums.CommentType;
import com.application.poppool.domain.user.dto.response.GetMyCommentResponse;
import com.application.poppool.domain.user.dto.response.GetMyPageResponse;
import com.application.poppool.global.enums.CommentSortCode;
import com.application.poppool.global.enums.PopUpSortCode;
import com.application.poppool.global.enums.SortCode;
import com.application.poppool.global.utils.QueryDslUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.application.poppool.domain.comment.entity.QCommentEntity.commentEntity;
import static com.application.poppool.domain.image.entity.QCommentImageEntity.commentImageEntity;
import static com.application.poppool.domain.popup.entity.QPopUpStoreEntity.popUpStoreEntity;
import static com.application.poppool.domain.user.entity.QBlockedUserEntity.blockedUserEntity;
import static com.application.poppool.domain.user.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

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
                .leftJoin(commentEntity.images, commentImageEntity)
                .where(popUpStoreIdEq(popUpStoreId),
                        blockedUserEntity.id.isNull(),
                        commentTypeEq(commentType)) // 차단된 유저가 아닌 경우(조인 시, 차단조건에 해당하지 않는 조건 = 차단조건에 일치하는 행이 없다.)
                .limit(commentCount) // 최대 3개
                .fetch();
    }

    @Override
    public List<CommentEntity> getAllPopUpStoreComments(String userId, CommentType commentType, Long popUpStoreId, Pageable pageable) {
        return queryFactory.selectFrom(commentEntity)
                .join(commentEntity.user, userEntity).fetchJoin()
                .leftJoin(blockedUserEntity)
                .on(blockUserIdEq(userId),
                        blockedUserEntity.blockedUser.userId.eq(commentEntity.user.userId))
                .leftJoin(commentEntity.images, commentImageEntity)
                .where(popUpStoreIdEq(popUpStoreId),
                        blockedUserEntity.id.isNull(),
                        commentTypeEq(commentType)) // 차단된 유저가 아닌 경우(조인 시, 차단조건에 해당하지 않는 조건 = 차단조건에 일치하는 행이 없다.)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
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
                .join(commentEntity.popUpStore, popUpStoreEntity)
                .where(commentEntity.user.userId.eq(userId))
                .orderBy(commentEntity.createDateTime.desc())
                .fetch();
    }

    @Override
    public List<GetMyCommentResponse.MyCommentInfo> findByMyCommentsWithPopUpStore(String userId, CommentType commentType, List<CommentSortCode> sortCodes, Pageable pageable) {

        LocalDateTime now = LocalDateTime.now();

        BooleanExpression isClosedPopUp = popUpStoreEntity.endDate.loe(now);

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
                                isClosedPopUp.as("closeYn")
                        ).as("popUpStoreInfo")
                ))
                .from(commentEntity)
                .join(commentEntity.popUpStore, popUpStoreEntity)
                .where(commentUserIdEq(userId),
                        commentTypeEq(commentType))
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
