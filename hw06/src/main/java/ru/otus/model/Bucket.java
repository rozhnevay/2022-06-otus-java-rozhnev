package ru.otus.model;

import ru.otus.enums.NominalEnum;

public interface Bucket {
    void add(Banknote banknote);

    void remove(Banknote banknote);

    int getBalance();

    NominalEnum getNominal();
}
