package ru.otus.model;

import java.util.List;
import ru.otus.enums.NominalEnum;

public interface MoneyStorage {
    void topUp(Banknote banknote);

    void topUp(Banknote banknote, Integer countBanknotes);

    List<Banknote> withdraw(Integer amount);

    int getBalance();
}
