package com.application.poppool.domain.popup.repository;

import com.application.poppool.domain.category.enums.Category;
import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.enums.Gender;
import com.application.poppool.global.utils.AgeGroupUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.application.poppool.domain.category.entity.QCategoryEntity.categoryEntity;
import static com.application.poppool.domain.popup.entity.QPopUpStoreEntity.popUpStoreEntity;
import static com.application.poppool.domain.user.entity.QUserEntity.userEntity;
import static com.application.poppool.domain.user.entity.QUserInterestCategoryEntity.userInterestCategoryEntity;
import static com.application.poppool.domain.user.entity.QUserPopUpStoreViewEntity.userPopUpStoreViewEntity;

@Repository
@RequiredArgsConstructor
public class PopUpStoreRepositoryImpl implements PopUpStoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Category> getUserInterestCategoryList(String userId) {
        return queryFactory.select(categoryEntity.category)
                .from(userInterestCategoryEntity)
                .join(userInterestCategoryEntity.category, categoryEntity)
                .where(userIdEq(userId))
                .fetch();
    }

    @Override
    public Page<GetHomeInfoResponse.CustomPopUpStore> getCustomPopUpStoreList(UserEntity user, Pageable pageable) {

        List<Category> userInterestCategoryList = getUserInterestCategoryList(user.getUserId());


        List<GetHomeInfoResponse.CustomPopUpStore> customPopUpStoreList = queryFactory.select(Projections.bean(GetHomeInfoResponse.CustomPopUpStore.class,
                        popUpStoreEntity.category.as("category"),
                        popUpStoreEntity.name.as("name"),
                        popUpStoreEntity.address.as("address"),
                        popUpStoreEntity.image.as("image"),
                        popUpStoreEntity.startDate.as("startDate"),
                        popUpStoreEntity.endDate.as("endDate")
                ))
                .from(userPopUpStoreViewEntity)
                .innerJoin(userPopUpStoreViewEntity.popUpStore, popUpStoreEntity)
                .innerJoin(userPopUpStoreViewEntity.user, userEntity)
                .where(categoryIn(userInterestCategoryList),
                        ageGroupEq(user.getAge()),
                        genderEq(user.getGender()))
                .orderBy(popUpStoreEntity.viewCount.desc(),
                        popUpStoreEntity.commentCount.desc(),
                        popUpStoreEntity.bookmarkCount.desc(),
                        userPopUpStoreViewEntity.viewCount.desc(),
                        userPopUpStoreViewEntity.commentCount.desc(),
                        userPopUpStoreViewEntity.bookmarkCount.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(popUpStoreEntity.count())
                .from(popUpStoreEntity)
                .orderBy(popUpStoreEntity.viewCount.desc(),
                        popUpStoreEntity.commentCount.desc(),
                        popUpStoreEntity.bookmarkCount.desc(),
                        userPopUpStoreViewEntity.popUpStore.viewCount.desc(),
                        userPopUpStoreViewEntity.popUpStore.commentCount.desc(),
                        userPopUpStoreViewEntity.popUpStore.bookmarkCount.desc());

        return PageableExecutionUtils.getPage(customPopUpStoreList, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<GetHomeInfoResponse.PopularPopUpStore> getPopularPopUpStoreList(Pageable pageable) {
        List<GetHomeInfoResponse.PopularPopUpStore> popularPopUpStoreList = queryFactory.select(Projections.bean(GetHomeInfoResponse.PopularPopUpStore.class,
                    popUpStoreEntity.category.as("category"),
                    popUpStoreEntity.name.as("name"),
                    popUpStoreEntity.address.as("address"),
                    popUpStoreEntity.image.as("image"),
                    popUpStoreEntity.startDate.as("startDate"),
                    popUpStoreEntity.endDate.as("endDate")
                ))
                .from(popUpStoreEntity)
                .orderBy(popUpStoreEntity.viewCount.desc(), popUpStoreEntity.commentCount.desc(), popUpStoreEntity.bookmarkCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(popUpStoreEntity.count())
                .from(popUpStoreEntity)
                .orderBy(popUpStoreEntity.viewCount.desc(), popUpStoreEntity.commentCount.desc(), popUpStoreEntity.bookmarkCount.desc());

        return PageableExecutionUtils.getPage(popularPopUpStoreList, pageable, countQuery::fetchOne);

    }

    @Override
    public Page<GetHomeInfoResponse.NewPopUpStore> getNewPopUpStoreList(LocalDateTime currentDate, Pageable pageable) {

        int newPopUpStorePeriod = 14;

        // DATE_ADD SQL 함수를 사용하여 14일을 더한 날짜를 계산
        DateTimeExpression<LocalDateTime> newPopUpDueDate = Expressions.dateTimeTemplate(
                LocalDateTime.class,
                "DATE_ADD({0}, INTERVAL {1} DAY)",
                popUpStoreEntity.startDate,
                newPopUpStorePeriod
        );


        List<GetHomeInfoResponse.NewPopUpStore> newPopUpStoreList = queryFactory.select(Projections.bean(GetHomeInfoResponse.NewPopUpStore.class,
                    popUpStoreEntity.category.as("category"),
                    popUpStoreEntity.name.as("name"),
                    popUpStoreEntity.address.as("address"),
                    popUpStoreEntity.image.as("image"),
                    popUpStoreEntity.startDate.as("startDate"),
                    popUpStoreEntity.endDate.as("endDate")
                ))
                .from(popUpStoreEntity)
                .where(isNewPopUpStore(popUpStoreEntity.startDate, newPopUpDueDate, currentDate))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(popUpStoreEntity.count())
                .from(popUpStoreEntity)
                .where(isNewPopUpStore(popUpStoreEntity.startDate, newPopUpDueDate, currentDate));

        return PageableExecutionUtils.getPage(newPopUpStoreList, pageable, countQuery::fetchOne);

    }


    private BooleanExpression isNewPopUpStore(DateTimeExpression<LocalDateTime> startDate, DateTimeExpression<LocalDateTime> newPopUpDueDate, LocalDateTime currentDate) {
        return popUpStoreEntity.startDate.loe(currentDate)
                .and(newPopUpDueDate.goe(currentDate));
    }

    private BooleanExpression userIdEq(String userId) {
        if (userId == null ) {
            return null;
        }
        return userInterestCategoryEntity.user.userId.eq(userId);
    }

    private BooleanExpression categoryIn(List<Category> userInterestCategoryList) {
        if (userInterestCategoryList.isEmpty()) { /** 만약 유저의 관심 카테고리가 등록되어 있지 않다면 모든 카테고리를 대상으로 함 */
            return null;
        }
        return popUpStoreEntity.category.in(userInterestCategoryList);
    }

    private BooleanExpression ageGroupEq(int age) {
        return userEntity.age.between(AgeGroupUtils.getStartAge(age), AgeGroupUtils.getEndAge(age));
    }

    private BooleanExpression genderEq(Gender gender) {
        if (gender == Gender.NONE) {
            return null;
        }
        return userEntity.gender.eq(gender);
    }
}
