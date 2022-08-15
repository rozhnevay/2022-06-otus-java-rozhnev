package ru.otus.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum NominalEnum {
    ONE(1),
    TWO(2),
    FIVE(5);

    private final int value;
}
