package com.application.poppool.global.enums;

import com.querydsl.core.types.Order;
import lombok.Getter;

public interface SortCode {
    String getField();
    Order getOrder();
}
