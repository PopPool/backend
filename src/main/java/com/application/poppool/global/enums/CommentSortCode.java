package com.application.poppool.global.enums;

import com.querydsl.core.types.Order;
import lombok.Getter;

@Getter
public enum CommentSortCode implements SortCode {
    MOST_LIKED("likeCount", Order.DESC),  // 좋아요 많은 순
    NEWEST("createDateTime", Order.DESC); // 최근 작성 순
    private final String field;
    private final Order order;

    CommentSortCode(String field, Order order) {
        this.field = field;
        this.order = order;
    }
}
