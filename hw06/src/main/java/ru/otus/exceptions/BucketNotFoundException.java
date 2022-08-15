package ru.otus.exceptions;

import ru.otus.enums.NominalEnum;

public class BucketNotFoundException extends RuntimeException {
    public BucketNotFoundException(NominalEnum nominalEnum) {
        super("Bucket for nominal " + nominalEnum.getValue() + " not found");
    }
}
