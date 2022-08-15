package ru.otus.model;

import java.util.List;
import ru.otus.model.impl.BanknoteImpl;

public interface ATM {
    void topUp(List<Banknote> banknotes);

    List<Banknote> withdraw(int amount);

    int getBalance();
}
