package com.application.poppool.global.enums;

import com.querydsl.core.types.Order;
import lombok.Getter;

@Getter
public enum SortCode {
    MOST_VIEWED("viewCount", Order.DESC),         // 조회 많은 순
    MOST_COMMENTED("commentCount", Order.DESC),   // 댓글 많은 순
    MOST_BOOKMARKED("bookmarkCount", Order.DESC), // 찜 많은 순
    MOST_LIKED("likeCount", Order.DESC),          // 좋아요 많은 순
    NEWEST("startDate", Order.DESC);             // 신규 오픈 순

    private final String field;
    private final Order order;

    SortCode(String field, Order order) {
        this.field = field;
        this.order = order;
    }
}
