package com.application.poppool.domain.popup.repository;

import com.application.poppool.domain.admin.popup.dto.response.GetAdminPopUpStoreListResponse;
import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import com.application.poppool.domain.popup.dto.resonse.GetClosedPopUpStoreListResponse;
import com.application.poppool.domain.popup.dto.resonse.GetOpenPopUpStoreListResponse;
import com.application.poppool.domain.popup.dto.resonse.GetPopUpStoreDetailResponse;
import com.application.poppool.domain.popup.dto.resonse.GetPopUpStoreDirectionResponse;
import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.popup.entity.QPopUpStoreEntity;
import com.application.poppool.domain.search.dto.SearchPopUpStoreResponse;
import com.application.poppool.domain.user.dto.response.GetMyCommentedPopUpStoreResponse;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.enums.Gender;
import com.application.poppool.global.enums.PopUpSortCode;
import com.application.poppool.global.utils.AgeGroupUtils;
import com.application.poppool.global.utils.QueryDslUtils;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.DateTimeTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.application.poppool.domain.category.entity.QCategoryEntity.categoryEntity;
import static com.application.poppool.domain.comment.entity.QCommentEntity.commentEntity;
import static com.application.poppool.domain.location.entity.QLocationEntity.locationEntity;
import static com.application.poppool.domain.popup.entity.QPopUpStoreEntity.popUpStoreEntity;
import static com.application.poppool.domain.user.entity.QBookMarkPopUpStoreEntity.bookMarkPopUpStoreEntity;
import static com.application.poppool.domain.user.entity.QUserEntity.userEntity;
import static com.application.poppool.domain.user.entity.QUserInterestCategoryEntity.userInterestCategoryEntity;
import static com.application.poppool.domain.user.entity.QUserPopUpStoreViewEntity.userPopUpStoreViewEntity;

@Repository
@RequiredArgsConstructor
public class PopUpStoreRepositoryImpl implements PopUpStoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<GetHomeInfoResponse.BannerPopUpStore> getBannerPopUpStoreList() {
        return queryFactory.select(Projections.bean(GetHomeInfoResponse.BannerPopUpStore.class,
                popUpStoreEntity.id.as("id"),
                popUpStoreEntity.name.as("name"),
                popUpStoreEntity.mainImageUrl.as("mainImageUrl")
                ))
                .from(popUpStoreEntity)
                .where(isOpenPopUp(),
                        isBannerPopUp())
                .orderBy(popUpStoreEntity.startDate.desc())
                .limit(3)
                .fetch();
    }

    @Override
    public List<Integer> getUserInterestCategoryList(String userId) {
        return queryFactory.select(categoryEntity.categoryId)
                .from(userInterestCategoryEntity)
                .join(userInterestCategoryEntity.category, categoryEntity)
                .where(userIdEq(userId))
                .fetch();
    }

    @Override
    public List<GetHomeInfoResponse.PopUpStore> getCustomPopUpStoreList(UserEntity user, Pageable pageable) {
        QPopUpStoreEntity popUpStoreEntitySub = new QPopUpStoreEntity("popUpStoreEntitySub");
        List<Integer> userInterestCategoryList = getUserInterestCategoryList(user.getUserId());

        return queryFactory.select(Projections.bean(GetHomeInfoResponse.PopUpStore.class,
                        popUpStoreEntity.id.as("id"),
                        ExpressionUtils.as(JPAExpressions.select(popUpStoreEntitySub.category.categoryName)
                                        .from(popUpStoreEntitySub)
                                        .leftJoin(popUpStoreEntitySub.category, categoryEntity)
                                        .where(popUpStoreEntitySub.id.eq(popUpStoreEntity.id))
                                , "categoryName"),
                        ExpressionUtils.as(JPAExpressions.select(popUpStoreEntitySub.name)
                                        .from(popUpStoreEntitySub)
                                        .where(popUpStoreEntitySub.id.eq(popUpStoreEntity.id))
                                , "name"),
                        ExpressionUtils.as(JPAExpressions.select(popUpStoreEntitySub.address)
                                        .from(popUpStoreEntitySub)
                                        .where(popUpStoreEntitySub.id.eq(popUpStoreEntity.id))
                                , "address"),
                        ExpressionUtils.as(JPAExpressions.select(popUpStoreEntitySub.mainImageUrl)
                                        .from(popUpStoreEntitySub)
                                        .where(popUpStoreEntitySub.id.eq(popUpStoreEntity.id))
                                , "mainImageUrl"),
                        ExpressionUtils.as(JPAExpressions.select(popUpStoreEntitySub.startDate)
                                        .from(popUpStoreEntitySub)
                                        .where(popUpStoreEntitySub.id.eq(popUpStoreEntity.id))
                                , "startDate"),
                        ExpressionUtils.as(JPAExpressions.select(popUpStoreEntitySub.endDate)
                                        .from(popUpStoreEntitySub)
                                        .where(popUpStoreEntitySub.id.eq(popUpStoreEntity.id))
                                , "endDate"),
                        ExpressionUtils.as(JPAExpressions.select(bookMarkPopUpStoreEntity.id.isNotNull())
                                        .from(bookMarkPopUpStoreEntity)
                                        .where(bookMarkPopUpStoreEntity.user.userId.eq(user.getUserId()),
                                                bookMarkPopUpStoreEntity.popUpStore.id.eq(popUpStoreEntity.id))
                                , "bookmarkYn")
                ))
                .from(popUpStoreEntity)
                .innerJoin(userPopUpStoreViewEntity).on(userPopUpStoreViewEntity.popUpStore.eq(popUpStoreEntity))
                .innerJoin(userPopUpStoreViewEntity.user, userEntity)
                .on(ageGroupEq(user.getAge()),
                        genderEq(user.getGender()))
                .where(categoryIn(userInterestCategoryList),
                        isOpenPopUp())
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
    public List<GetHomeInfoResponse.PopUpStore> getCategoryPopularPopUpStoreList(UserEntity user, Pageable pageable) {
        QPopUpStoreEntity popUpStoreEntitySub = new QPopUpStoreEntity("popUpStoreEntitySub");
        List<Integer> userInterestCategoryList = getUserInterestCategoryList(user.getUserId());

        return queryFactory.select(Projections.bean(GetHomeInfoResponse.PopUpStore.class,
                        popUpStoreEntity.id.as("id"),
                        popUpStoreEntity.category.categoryName.as("categoryName"),
                        popUpStoreEntity.name.as("name"),
                        popUpStoreEntity.address.as("address"),
                        popUpStoreEntity.mainImageUrl.as("mainImageUrl"),
                        popUpStoreEntity.startDate.as("startDate"),
                        popUpStoreEntity.endDate.as("endDate"),
                        ExpressionUtils.as(JPAExpressions.select(bookMarkPopUpStoreEntity.id.isNotNull())
                                        .from(bookMarkPopUpStoreEntity)
                                        .where(bookMarkPopUpStoreEntity.user.userId.eq(user.getUserId()),
                                                bookMarkPopUpStoreEntity.popUpStore.id.eq(popUpStoreEntity.id))
                                , "bookmarkYn")
                ))
                .from(popUpStoreEntity)
                .leftJoin(popUpStoreEntity.category, categoryEntity)
                .where(categoryIn(userInterestCategoryList),
                        isOpenPopUp())
                .orderBy(popUpStoreEntity.viewCount.desc(),
                        popUpStoreEntity.commentCount.desc(),
                        popUpStoreEntity.bookmarkCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }


    @Override
    public long countCustomPopUpStores(UserEntity user) {
        List<Integer> userInterestCategoryList = getUserInterestCategoryList(user.getUserId());
        Long count = queryFactory.select(popUpStoreEntity.countDistinct())
                .from(popUpStoreEntity)
                .leftJoin(userPopUpStoreViewEntity).on(userPopUpStoreViewEntity.popUpStore.eq(popUpStoreEntity))
                .leftJoin(userPopUpStoreViewEntity.user, userEntity)
                .on(ageGroupEq(user.getAge()),
                        genderEq(user.getGender()))
                .where(categoryIn(userInterestCategoryList),
                        isOpenPopUp())
                .fetchOne();
        return count != null ? count : 0L;
    }

    @Override
    public List<GetHomeInfoResponse.PopUpStore> getPopularPopUpStoreList(String userId, Pageable pageable) {
        return queryFactory.select(Projections.bean(GetHomeInfoResponse.PopUpStore.class,
                        popUpStoreEntity.id.as("id"),
                        popUpStoreEntity.category.categoryName.as("categoryName"),
                        popUpStoreEntity.name.as("name"),
                        popUpStoreEntity.address.as("address"),
                        popUpStoreEntity.mainImageUrl.as("mainImageUrl"),
                        popUpStoreEntity.startDate.as("startDate"),
                        popUpStoreEntity.endDate.as("endDate"),
                        ExpressionUtils.as(JPAExpressions.select(bookMarkPopUpStoreEntity.id.isNotNull())
                                        .from(bookMarkPopUpStoreEntity)
                                        .where(bookMarkPopUpStoreEntity.user.userId.eq(userId),
                                                bookMarkPopUpStoreEntity.popUpStore.id.eq(popUpStoreEntity.id))
                                , "bookmarkYn")
                ))
                .from(popUpStoreEntity)
                .leftJoin(popUpStoreEntity.category, categoryEntity)
                .where(isOpenPopUp())
                .orderBy(popUpStoreEntity.viewCount.desc(), popUpStoreEntity.commentCount.desc(), popUpStoreEntity.bookmarkCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public long countPopularPopUpStores() {
        Long count = queryFactory.select(popUpStoreEntity.count())
                .from(popUpStoreEntity)
                .where(isOpenPopUp())
                .orderBy(popUpStoreEntity.viewCount.desc(), popUpStoreEntity.commentCount.desc(), popUpStoreEntity.bookmarkCount.desc())
                .fetchOne();
        return count != null ? count : 0L;
    }

    @Override
    public List<GetHomeInfoResponse.PopUpStore> getNewPopUpStoreList(String userId, LocalDateTime currentDate, Pageable pageable) {

        // DATE_ADD SQL 함수를 사용하여 14일을 더한 날짜를 계산
        DateTimeExpression<LocalDateTime> newPopUpDueDate = getNewPopUpDueDate();

        return queryFactory.select(Projections.bean(GetHomeInfoResponse.PopUpStore.class,
                        popUpStoreEntity.id.as("id"),
                        popUpStoreEntity.category.categoryName.as("categoryName"),
                        popUpStoreEntity.name.as("name"),
                        popUpStoreEntity.address.as("address"),
                        popUpStoreEntity.mainImageUrl.as("mainImageUrl"),
                        popUpStoreEntity.startDate.as("startDate"),
                        popUpStoreEntity.endDate.as("endDate"),
                        ExpressionUtils.as(JPAExpressions.select(bookMarkPopUpStoreEntity.id.isNotNull())
                                        .from(bookMarkPopUpStoreEntity)
                                        .where(bookMarkPopUpStoreEntity.user.userId.eq(userId),
                                                bookMarkPopUpStoreEntity.popUpStore.id.eq(popUpStoreEntity.id))
                                , "bookmarkYn")
                ))
                .from(popUpStoreEntity)
                .leftJoin(popUpStoreEntity.category, categoryEntity)
                .where(isNewPopUpStore(newPopUpDueDate, currentDate),
                        isOpenPopUp())
                .orderBy(popUpStoreEntity.startDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public long countNewPopUpStores(LocalDateTime currentDate) {

        // DATE_ADD SQL 함수를 사용하여 14일을 더한 날짜를 계산
        DateTimeExpression<LocalDateTime> newPopUpDueDate = getNewPopUpDueDate();

        Long count = queryFactory.select(popUpStoreEntity.count())
                .from(popUpStoreEntity)
                .where(isNewPopUpStore(newPopUpDueDate, currentDate))
                .fetchOne();
        return count != null ? count : 0L;
    }

    @Override
    public List<GetPopUpStoreDetailResponse.PopUpStore> getSimilarPopUpStoreList(Long popUpStoreId, Integer categoryId) {
        return queryFactory.select(Projections.bean(GetPopUpStoreDetailResponse.PopUpStore.class,
                        popUpStoreEntity.id.as("id"),
                        popUpStoreEntity.name.as("name"),
                        popUpStoreEntity.mainImageUrl.as("mainImageUrl"),
                        popUpStoreEntity.endDate.as("endDate")
                ))
                .from(popUpStoreEntity)
                .where(categoryEq(categoryId), // 같은 카테고리
                        isOpenPopUp(),  // 현재 진행 중인 팝업
                        popUpStoreIdNe(popUpStoreId)) // 현재 조회한 팝업은 제외
                .orderBy(popUpStoreEntity.viewCount.desc(), popUpStoreEntity.commentCount.desc(), popUpStoreEntity.bookmarkCount.desc())
                .limit(3) // 최대 3개
                .fetch();
    }

    @Override
    public List<GetOpenPopUpStoreListResponse.PopUpStore> getOpenPopUpStoreList(String userId, List<Integer> categories, List<PopUpSortCode> sortCodes, Pageable pageable) {
        return queryFactory.select(Projections.bean(GetOpenPopUpStoreListResponse.PopUpStore.class,
                        popUpStoreEntity.id.as("id"),
                        popUpStoreEntity.category.categoryName.as("categoryName"),
                        popUpStoreEntity.name.as("name"),
                        popUpStoreEntity.address.as("address"),
                        popUpStoreEntity.mainImageUrl.as("mainImageUrl"),
                        popUpStoreEntity.startDate.as("startDate"),
                        popUpStoreEntity.endDate.as("endDate"),
                        ExpressionUtils.as(JPAExpressions.select(bookMarkPopUpStoreEntity.id.isNotNull())
                                        .from(bookMarkPopUpStoreEntity)
                                        .where(bookMarkPopUpStoreEntity.user.userId.eq(userId),
                                                bookMarkPopUpStoreEntity.popUpStore.id.eq(popUpStoreEntity.id))
                                , "bookmarkYn")
                ))
                .from(popUpStoreEntity)
                .leftJoin(popUpStoreEntity.category, categoryEntity)
                .where(categoryIn(categories),
                        isOpenPopUp())
                .orderBy(QueryDslUtils.getOrderSpecifiers(sortCodes, pageable, popUpStoreEntity).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public long countOpenPopUpStores(List<Integer> categories) {
        Long count = queryFactory.select(popUpStoreEntity.count())
                .from(popUpStoreEntity)
                .where(categoryIn(categories),
                        isOpenPopUp())
                .fetchOne();
        return count != null ? count : 0L;
    }

    @Override
    public List<GetClosedPopUpStoreListResponse.PopUpStore> getClosedPopUpStoreList(String userId, List<Integer> categories, List<PopUpSortCode> sortCodes, Pageable pageable) {
        return queryFactory.select(Projections.bean(GetClosedPopUpStoreListResponse.PopUpStore.class,
                        popUpStoreEntity.id.as("id"),
                        popUpStoreEntity.category.categoryName.as("categoryName"),
                        popUpStoreEntity.name.as("name"),
                        popUpStoreEntity.address.as("address"),
                        popUpStoreEntity.mainImageUrl.as("mainImageUrl"),
                        popUpStoreEntity.startDate.as("startDate"),
                        popUpStoreEntity.endDate.as("endDate"),
                        ExpressionUtils.as(JPAExpressions.select(bookMarkPopUpStoreEntity.id.isNotNull())
                                        .from(bookMarkPopUpStoreEntity)
                                        .where(bookMarkPopUpStoreEntity.user.userId.eq(userId),
                                                bookMarkPopUpStoreEntity.popUpStore.id.eq(popUpStoreEntity.id))
                                , "bookmarkYn")
                ))
                .from(popUpStoreEntity)
                .leftJoin(popUpStoreEntity.category, categoryEntity)
                .where(categoryIn(categories),
                        isClosedPopUp())
                .orderBy(QueryDslUtils.getOrderSpecifiers(sortCodes, pageable, popUpStoreEntity).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public long countClosedPopUpStores(List<Integer> categories) {
        Long count = queryFactory.select(popUpStoreEntity.count())
                .from(popUpStoreEntity)
                .where(categoryIn(categories),
                        isClosedPopUp())
                .fetchOne();
        return count != null ? count : 0L;
    }

    private DateTimeTemplate<LocalDateTime> getNewPopUpDueDate() {
        int newPopUpStorePeriod = 14;

        // DATE_ADD SQL 함수를 사용하여 14일을 더한 날짜를 계산
        return Expressions.dateTimeTemplate(
                LocalDateTime.class,
                "ADDDATE({0}, {1})",
                popUpStoreEntity.startDate,
                Expressions.constant(newPopUpStorePeriod));
    }

    @Override
    public List<SearchPopUpStoreResponse.PopUpStore> searchPopUpStore(String userId, String query) {
        return queryFactory.select(Projections.bean(SearchPopUpStoreResponse.PopUpStore.class,
                        popUpStoreEntity.id.as("id"),
                        popUpStoreEntity.name.as("name"),
                        popUpStoreEntity.mainImageUrl.as("mainImageUrl"),
                        popUpStoreEntity.category.categoryName.as("categoryName"),
                        popUpStoreEntity.startDate.as("startDate"),
                        popUpStoreEntity.endDate.as("endDate"),
                        popUpStoreEntity.address.as("address"),
                        ExpressionUtils.as(JPAExpressions.select(bookMarkPopUpStoreEntity.id.isNotNull())
                                        .from(bookMarkPopUpStoreEntity)
                                        .where(bookMarkPopUpStoreEntity.user.userId.eq(userId),
                                                bookMarkPopUpStoreEntity.popUpStore.id.eq(popUpStoreEntity.id))
                                , "bookmarkYn")
                ))
                .from(popUpStoreEntity)
                .leftJoin(popUpStoreEntity.category, categoryEntity)
                .where(nameContains(query)
                                .or(addressContains(query)),
                        isOpenPopUp())
                .orderBy(popUpStoreEntity.startDate.desc())
                .fetch();
    }

    @Override
    public List<PopUpStoreEntity> searchPopUpStoreByMap(List<Integer> categories, String query) {
        return queryFactory.selectFrom(popUpStoreEntity)
                .innerJoin(popUpStoreEntity.location, locationEntity).fetchJoin()
                .leftJoin(popUpStoreEntity.category, categoryEntity).fetchJoin()
                .leftJoin(popUpStoreEntity.bookMarkPopUpStores, bookMarkPopUpStoreEntity)
                .where(categoryIn(categories),
                        nameContains(query),
                        isOpenPopUp())
                .orderBy(popUpStoreEntity.startDate.desc())
                .fetch();

    }

    @Override
    public List<PopUpStoreEntity> getViewBoundPopUpStoreList(List<Integer> categories, double northEastLat, double northEastLon, double southWestLat, double southWestLon) {
        return queryFactory.selectFrom(popUpStoreEntity)
                .innerJoin(popUpStoreEntity.location, locationEntity).fetchJoin()
                .leftJoin(popUpStoreEntity.category, categoryEntity).fetchJoin()
                .where(categoryIn(categories),
                        isOpenPopUp(),
                        latitudeBetween(southWestLat, northEastLat),
                        longitudeBetween(southWestLon, northEastLon))
                .fetch();
    }

    @Override
    public GetPopUpStoreDirectionResponse getPopUpStoreDirection(Long popUpStoreId) {
        return queryFactory.select(Projections.bean(GetPopUpStoreDirectionResponse.class,
                        popUpStoreEntity.id.as("id"),
                        popUpStoreEntity.category.categoryName.as("categoryName"),
                        popUpStoreEntity.name.as("name"),
                        popUpStoreEntity.address.as("address"),
                        popUpStoreEntity.startDate.as("startDate"),
                        popUpStoreEntity.endDate.as("endDate"),
                        locationEntity.latitude.as("latitude"),
                        locationEntity.longitude.as("longitude"),
                        locationEntity.id.as("markerId"),
                        locationEntity.markerTitle.as("markerTitle"),
                        locationEntity.markerSnippet.as("markerSnippet")

                ))
                .from(popUpStoreEntity)
                .leftJoin(popUpStoreEntity.category, categoryEntity)
                .leftJoin(popUpStoreEntity.location, locationEntity)
                .where(popUpStoreIdEq(popUpStoreId),
                        isOpenPopUp())
                .fetchOne();
    }

    public List<PopUpStoreEntity> getMyCommentedPopUpStoreList(String userId, Pageable pageable) {
        return queryFactory.selectFrom(popUpStoreEntity)
                .innerJoin(popUpStoreEntity.comments, commentEntity)
                .where(commentEntity.user.userId.eq(userId))
                .groupBy(popUpStoreEntity.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }


    @Override
    public List<GetAdminPopUpStoreListResponse.PopUpStore> getAdminPopUpStoreList(String query, Pageable pageable) {
        return queryFactory.select(Projections.bean(GetAdminPopUpStoreListResponse.PopUpStore.class,
                        popUpStoreEntity.id.as("id"),
                        popUpStoreEntity.name.as("name"),
                        popUpStoreEntity.category.categoryName.as("categoryName"),
                        popUpStoreEntity.mainImageUrl.as("mainImageUrl")
                ))
                .from(popUpStoreEntity)
                .leftJoin(popUpStoreEntity.category, categoryEntity)
                .where(nameContains(query))
                .orderBy(popUpStoreEntity.startDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public long countAdminPopUpStores(String query) {
        Long count = queryFactory.select(popUpStoreEntity.count())
                .from(popUpStoreEntity)
                .where(nameContains(query))
                .fetchOne();
        return count != null ? count : 0L;
    }


    private BooleanExpression isNewPopUpStore(DateTimeExpression<LocalDateTime> newPopUpDueDate, LocalDateTime currentDate) {
        return popUpStoreEntity.startDate.loe(currentDate)
                .and(newPopUpDueDate.goe(currentDate));
    }

    private BooleanExpression userIdEq(String userId) {
        if (userId == null) {
            return null;
        }
        return userInterestCategoryEntity.user.userId.eq(userId);
    }

    private BooleanExpression categoryIn(List<Integer> categories) {
        if (categories == null || categories.isEmpty()) { /** 카테고리를 지정하지 않으면 모든 카테고리를 대상으로 함 */
            return null;
        }
        return popUpStoreEntity.category.categoryId.in(categories);
    }

    private BooleanExpression ageGroupEq(int age) {
        return userPopUpStoreViewEntity.user.age.between(AgeGroupUtils.getStartAge(age), AgeGroupUtils.getEndAge(age));
    }

    private BooleanExpression genderEq(Gender gender) {
        if (gender == Gender.NONE || gender == null) {
            return Expressions.TRUE;
        }
        return userPopUpStoreViewEntity.user.gender.eq(gender);
    }

    private BooleanExpression categoryEq(Integer categoryId) {
        return popUpStoreEntity.category.categoryId.eq(categoryId);
    }

    private BooleanExpression popUpStoreIdEq(Long popUpStoreId) {
        if (popUpStoreId == null) {
            return null;
        }
        return popUpStoreEntity.id.eq(popUpStoreId);
    }

    private BooleanExpression latitudeBetween(double southWestLat, double northEastLat) {
        return locationEntity.latitude.between(southWestLat, northEastLat);
    }

    private BooleanExpression longitudeBetween(double southWestLon, double northEastLon) {
        return locationEntity.longitude.between(southWestLon, northEastLon);
    }

    private BooleanExpression isOpenPopUp() {
        LocalDateTime now = LocalDateTime.now();
        return popUpStoreEntity.endDate.goe(now);
    }

    private BooleanExpression isClosedPopUp() {
        LocalDateTime now = LocalDateTime.now();
        return popUpStoreEntity.endDate.lt(now);
    }


    private BooleanExpression nameContains(String query) {
        if (query == null) {
            return null;
        }
        return popUpStoreEntity.name.containsIgnoreCase(query);
    }

    private BooleanExpression addressContains(String query) {
        if (query == null) {
            return null;
        }
        return popUpStoreEntity.address.containsIgnoreCase(query);
    }

    private BooleanExpression popUpStoreIdNe(Long popUpStoreId) {
        if (popUpStoreId == null) {
            return null;
        }
        return popUpStoreEntity.id.ne(popUpStoreId);
    }

    private BooleanExpression isBannerPopUp() {
        return popUpStoreEntity.bannerYn.eq(true);
    }
}
