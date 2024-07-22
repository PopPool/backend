package com.application.poppool.global.converter;

import com.application.poppool.global.enums.BaseEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class EnumToStringConverter<E extends Enum<E>& BaseEnum> implements AttributeConverter<E,String> {

    private final Class<E> enumClass;

    @Override
    public String convertToDatabaseColumn(E attribute) {
        if (attribute == null){
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return this.dbDataToEnumConstant(dbData);
    }

    /**
     * db에 저장된 enum 문자열 -> enum 상수 변환
     * @param dbData
     * @return
     */
    private E dbDataToEnumConstant(String dbData) {
        for (E enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.getValue().equals(dbData)){
                return enumConstant;
            }
        }
        throw new IllegalArgumentException("해당 데이터에 매핑되는 Enum 상수가 없습니다. " + dbData);
    }

}
