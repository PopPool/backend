package com.application.poppool.domain.category.enums;

import com.application.poppool.global.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Category implements BaseEnum {
    FASHION("패션"),               // 패션
    LIFESTYLE("라이프스타일"),      // 라이프스타일
    BEAUTY("뷰티"),               // 뷰티
    FOOD_COOKING("음식/요리"),     // 음식/요리
    ART("예술"),                  // 예술
    PETS("반려동물"),              // 반려동물
    TRAVEL("여행"),               // 여행
    ENTERTAINMENT("엔터테인먼트"),  // 엔터테인먼트
    ANIMATION("애니메이션"),       // 애니메이션
    KIDS("키즈"),                 // 키즈
    SPORTS("스포츠"),             // 스포츠
    GAMES("게임");                 // 게임

    @JsonValue
    private final String value;

    Category(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Category fromValueToEnum(String value) {
        for (Category category : Category.values()) {
            if (category.getValue().equals(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 값입니다.: " + value);
    }

}
