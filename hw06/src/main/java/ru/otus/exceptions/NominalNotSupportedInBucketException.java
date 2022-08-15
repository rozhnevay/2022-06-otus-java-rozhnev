package ru.otus.exceptions;

import ru.otus.enums.NominalEnum;

public class NominalNotSupportedInBucketException extends RuntimeException {
    public NominalNotSupportedInBucketException(NominalEnum nominalEnum) {
        super("Banknote nominal " + nominalEnum.getValue() + " not supported for this bucket");
    }
}
