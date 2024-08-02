package com.application.poppool.domain.popup.repository;

import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.DateTimeTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.application.poppool.domain.popup.entity.QPopUpStoreEntity.popUpStoreEntity;
import static com.querydsl.core.types.ExpressionUtils.orderBy;

@Repository
@RequiredArgsConstructor
public class PopUpStoreRepositoryImpl implements PopUpStoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<GetHomeInfoResponse.NewPopUpStores> getNewPopUpStoreList(LocalDateTime currentDate, Pageable pageable) {

        int newPopUpStorePeriod = 14;

        String periodExpression = String.format("INTERVAL %d DAY", newPopUpStorePeriod);

        DateTimePath<LocalDateTime> startDate = popUpStoreEntity.startDate;

        DateTimeTemplate<LocalDateTime> newPopUpDueDate = Expressions.dateTimeTemplate(
                LocalDateTime.class,
                "DATE_ADD({0}, " + newPopUpStorePeriod + ")",
                startDate
        );

        return queryFactory.selectFrom(popUpStoreEntity)
                .where(currentDate.b)
    }
}
