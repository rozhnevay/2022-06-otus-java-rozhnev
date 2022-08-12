package ru.otus.model;

import ru.otus.enums.NominalEnum;

public interface Banknote {
    NominalEnum getNominal();

    int getNominalValue();
}
