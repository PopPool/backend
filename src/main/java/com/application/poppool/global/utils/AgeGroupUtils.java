package com.application.poppool.global.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AgeGroupUtils {

    public static int getStartAge(int age) {
        return (age / 10) * 10;
    }

    public static int getEndAge(int age) {
        return getStartAge(age) + 9;
    }

}
