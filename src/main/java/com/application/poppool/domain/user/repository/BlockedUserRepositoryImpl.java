package com.application.poppool.domain.user.repository;

import com.application.poppool.domain.user.dto.response.GetBlockedUserListResponse;
import com.application.poppool.domain.user.entity.QUserEntity;
import com.application.poppool.global.utils.QueryDslUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.application.poppool.domain.user.entity.QBlockedUserEntity.blockedUserEntity;
import static com.application.poppool.domain.user.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class BlockedUserRepositoryImpl implements BlockedUserRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<GetBlockedUserListResponse.BlockedUserInfo> getBlockedUserList(String userId, Pageable pageable) {
        QUserEntity blockedUser = new QUserEntity("blockedUser");
        List<GetBlockedUserListResponse.BlockedUserInfo> blockedUserInfoList = queryFactory.select(Projections.bean(GetBlockedUserListResponse.BlockedUserInfo.class,
                        blockedUser.userId.as("userId"),
                        blockedUser.profileImageUrl.as("profileImageUrl"),
                        blockedUser.nickname.as("nickname"),
                        blockedUser.instagramId.as("instagramId")
                ))
                .from(blockedUserEntity)
                .join(blockedUserEntity.user, userEntity)
                .join(blockedUserEntity.blockedUser, blockedUser)
                .where(eqUserId(userId))
                .orderBy(blockedUserEntity.blockedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Fetch the total count of blocked users
        JPAQuery<Long> countQuery = queryFactory
                .select(blockedUserEntity.count())
                .from(blockedUserEntity)
                .join(blockedUserEntity.user, userEntity)
                .join(blockedUserEntity.blockedUser, blockedUser)
                .where(eqUserId(userId));

        // Page 객체 생성
        return PageableExecutionUtils.getPage(blockedUserInfoList, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqUserId(String userId) {
        return userId != null ? blockedUserEntity.user.userId.eq(userId) : null;
    }

}
