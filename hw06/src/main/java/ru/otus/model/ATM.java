package ru.otus.model;

import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import ru.otus.enums.NominalEnum;

public interface ATM {
    void topUp(List<NominalEnum> banknotes);

    List<Pair<NominalEnum, Integer>> withdraw(int amount);

    int getBalance();
}
