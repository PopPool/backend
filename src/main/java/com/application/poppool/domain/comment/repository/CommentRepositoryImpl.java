package com.application.poppool.domain.comment.repository;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.application.poppool.domain.comment.entity.QCommentEntity.commentEntity;
import static com.application.poppool.domain.user.entity.QBlockedUserEntity.blockedUserEntity;
import static com.application.poppool.domain.user.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentEntity> findAllPopUpStoreComments(String userId, Long popUpStoreId) {
        return queryFactory.selectFrom(commentEntity)
                .join(commentEntity.user, userEntity).fetchJoin()
                .leftJoin(blockedUserEntity)
                .on(userIdEq(userId),
                        blockedUserEntity.blockedUser.userId.eq(commentEntity.user.userId))
                .where(popUpStoreIdEq(popUpStoreId),
                        blockedUserEntity.id.isNull()) // 차단된 유저가 아닌 경우(조인 시, 차단조건에 해당하지 않는 조건 = 차단조건에 일치하는 행이 없다.)
                .fetch();
    }

    private BooleanExpression userIdEq(String userId) {
        return userId != null ? blockedUserEntity.user.userId.eq(userId) : null;
    }

    private BooleanExpression popUpStoreIdEq(Long popUpStoreId) {
        return popUpStoreId != null ? commentEntity.popUpStore.id.eq(popUpStoreId) : null;
    }


}
