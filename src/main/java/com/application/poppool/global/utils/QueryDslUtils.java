package com.application.poppool.global.utils;

import com.application.poppool.global.enums.SortCode;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class QueryDslUtils {

    public static List<OrderSpecifier> getOrderSpecifiers(List<SortCode> sortCodes, Pageable pageable, Path<?> qEntity) {
        List<OrderSpecifier> orders = new ArrayList<>();
        PathBuilder pathBuilder = new PathBuilder<>(qEntity.getType(), qEntity.getMetadata());

        for (SortCode sortCode : sortCodes) {
            orders.add(new OrderSpecifier<>(sortCode.getOrder(), pathBuilder.get(sortCode.getField())));
        }

        // pageable의 sort 파라미터에 해당하는 부분
//        for (Sort.Order order : pageable.getSort()) {
//            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
//            orders.add(new OrderSpecifier<>(direction, pathBuilder.get(order.getProperty())));
//        }
//        return orders;

        return orders;
    }
}
