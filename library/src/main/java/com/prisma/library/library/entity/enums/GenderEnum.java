package com.prisma.library.library.entity.enums;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum GenderEnum {
    MALE("m"), FEMALE("f");

    private final String value;

    GenderEnum(String value) {
        this.value = value;
    }

    public static GenderEnum getByValue(String value) {
        return Arrays.stream(GenderEnum.values())
            .filter(genderEnum -> genderEnum.value.equals(value))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
