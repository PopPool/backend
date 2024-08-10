package com.application.poppool.domain.popup.repository;

import com.application.poppool.domain.category.enums.Category;
import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import com.application.poppool.domain.popup.entity.QPopUpStoreEntity;
import com.application.poppool.domain.search.dto.SearchPopUpStoreByMapResponse;
import com.application.poppool.domain.search.dto.SearchPopUpStoreResponse;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.enums.Gender;
import com.application.poppool.global.utils.AgeGroupUtils;
import com.application.poppool.global.utils.QueryDslUtils;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public List<GetHomeInfoResponse.PopUpStore> getCustomPopUpStoreList(UserEntity user, Pageable pageable) {
        QPopUpStoreEntity popUpStoreEntitySub = new QPopUpStoreEntity("popUpStoreEntitySub");
        List<Category> userInterestCategoryList = getUserInterestCategoryList(user.getUserId());

        return queryFactory.select(Projections.bean(GetHomeInfoResponse.PopUpStore.class,
                        popUpStoreEntity.id.as("id"),
                        ExpressionUtils.as(JPAExpressions.select(popUpStoreEntitySub.category)
                                .from(popUpStoreEntitySub)
                                .where(popUpStoreEntitySub.id.eq(popUpStoreEntity.id))
                                ,"category"),
                        ExpressionUtils.as(JPAExpressions.select(popUpStoreEntitySub.name)
                                .from(popUpStoreEntitySub)
                                .where(popUpStoreEntitySub.id.eq(popUpStoreEntity.id))
                                ,"name"),
                        ExpressionUtils.as(JPAExpressions.select(popUpStoreEntitySub.address)
                                .from(popUpStoreEntitySub)
                                .where(popUpStoreEntitySub.id.eq(popUpStoreEntity.id))
                                ,"address"),
                        ExpressionUtils.as(JPAExpressions.select(popUpStoreEntitySub.mainImageUrl)
                                .from(popUpStoreEntitySub)
                                .where(popUpStoreEntitySub.id.eq(popUpStoreEntity.id))
                                ,"mainImageUrl")
                ))
                .from(userPopUpStoreViewEntity)
                .innerJoin(userPopUpStoreViewEntity.popUpStore, popUpStoreEntity)
                .innerJoin(userPopUpStoreViewEntity.user, userEntity)
                .where(categoryIn(userInterestCategoryList),
                        ageGroupEq(user.getAge()),
                        genderEq(user.getGender()))
                .groupBy(popUpStoreEntity.id)
                .orderBy(popUpStoreEntity.viewCount.desc(),
                        popUpStoreEntity.commentCount.desc(),
                        popUpStoreEntity.bookmarkCount.desc(),
                        userPopUpStoreViewEntity.viewCount.sum().desc(),
                        userPopUpStoreViewEntity.commentCount.sum().desc(),
                        userPopUpStoreViewEntity.bookmarkCount.sum().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public long countCustomPopUpStores(UserEntity user) {
        List<Category> userInterestCategoryList = getUserInterestCategoryList(user.getUserId());
        Long count = queryFactory.select(popUpStoreEntity.count())
                .from(popUpStoreEntity)
                .innerJoin(userPopUpStoreViewEntity.popUpStore, popUpStoreEntity)
                .innerJoin(userPopUpStoreViewEntity.user, userEntity)
                .where(categoryIn(userInterestCategoryList),
                        ageGroupEq(user.getAge()),
                        genderEq(user.getGender()))
                .fetchOne();
        return count != null ? count : 0L;
    }

    @Override
    public List<GetHomeInfoResponse.PopUpStore> getPopularPopUpStoreList(Pageable pageable) {
        return queryFactory.select(Projections.bean(GetHomeInfoResponse.PopUpStore.class,
                    popUpStoreEntity.id.as("id"),
                    popUpStoreEntity.category.as("category"),
                    popUpStoreEntity.name.as("name"),
                    popUpStoreEntity.address.as("address"),
                    popUpStoreEntity.mainImageUrl.as("mailImageUrl"),
                    popUpStoreEntity.startDate.as("startDate"),
                    popUpStoreEntity.endDate.as("endDate")
                ))
                .from(popUpStoreEntity)
                .orderBy(popUpStoreEntity.viewCount.desc(), popUpStoreEntity.commentCount.desc(), popUpStoreEntity.bookmarkCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public long countPopularPopUpStores() {
        Long count = queryFactory.select(popUpStoreEntity.count())
                .from(popUpStoreEntity)
                .orderBy(popUpStoreEntity.viewCount.desc(), popUpStoreEntity.commentCount.desc(), popUpStoreEntity.bookmarkCount.desc())
                .fetchOne();
        return count != null ? count : 0L;
    }

    @Override
    public List<GetHomeInfoResponse.PopUpStore> getNewPopUpStoreList(LocalDateTime currentDate, Pageable pageable) {

        int newPopUpStorePeriod = 14;

        /**
        // DATE_ADD SQL 함수를 사용하여 14일을 더한 날짜를 계산
        DateTimeExpression<LocalDateTime> newPopUpDueDate = Expressions.dateTimeTemplate(
                LocalDateTime.class,
                "DATE_ADD({0}, INTERVAL {1} DAY)",
                popUpStoreEntity.startDate,
                Expressions.constant(newPopUpStorePeriod)
        );*/


        return queryFactory.select(Projections.bean(GetHomeInfoResponse.PopUpStore.class,
                    popUpStoreEntity.id.as("id"),
                    popUpStoreEntity.category.as("category"),
                    popUpStoreEntity.name.as("name"),
                    popUpStoreEntity.address.as("address"),
                    popUpStoreEntity.mainImageUrl.as("mailImageUrl"),
                    popUpStoreEntity.startDate.as("startDate"),
                    popUpStoreEntity.endDate.as("endDate")
                ))
                .from(popUpStoreEntity)
                //.where(isNewPopUpStore(newPopUpDueDate, currentDate))
                .orderBy(QueryDslUtils.getOrderSpecifiers(pageable, popUpStoreEntity).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public long countNewPopUpStores() {
        Long count = queryFactory.select(popUpStoreEntity.count())
                .from(popUpStoreEntity)
        //        .where(isNewPopUpStore(newPopUpDueDate, currentDate));
                .fetchOne();
        return count != null ? count : 0L;
    }

    @Override
    public List<SearchPopUpStoreResponse.PopUpStore> searchPopUpStore(String query) {
        return queryFactory.select(Projections.bean(SearchPopUpStoreResponse.PopUpStore.class,
                        popUpStoreEntity.id.as("id"),
                        popUpStoreEntity.name.as("name"),
                        popUpStoreEntity.address.as("address")
                ))
                .from(popUpStoreEntity)
                .where(popUpStoreEntity.name.containsIgnoreCase(query)
                        .or(popUpStoreEntity.address.containsIgnoreCase(query)))
                .orderBy(popUpStoreEntity.createDateTime.desc())
                .fetch();
    }

    @Override
    public List<SearchPopUpStoreByMapResponse.PopUpStore> searchPopUpStoreByMap(Category category, String query) {
        return queryFactory.select(Projections.bean(SearchPopUpStoreByMapResponse.PopUpStore.class,
                        popUpStoreEntity.id,
                        popUpStoreEntity.category,
                        popUpStoreEntity.name,
                        popUpStoreEntity.address,
                        popUpStoreEntity.startDate,
                        popUpStoreEntity.endDate))
                .from(popUpStoreEntity)
                .where(categoryEq(category),
                        popUpStoreEntity.name.containsIgnoreCase(query))
                .orderBy(popUpStoreEntity.createDateTime.desc())
                .fetch();

    }


    private BooleanExpression isNewPopUpStore(DateTimeExpression<LocalDateTime> newPopUpDueDate, LocalDateTime currentDate) {
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

    private BooleanExpression categoryEq(Category category) {
        if (category == null) {
            return null;
        }
        return popUpStoreEntity.category.eq(category);
    }
}
