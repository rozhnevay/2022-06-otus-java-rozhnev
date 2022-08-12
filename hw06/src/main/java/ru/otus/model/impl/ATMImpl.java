package ru.otus.model.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import ru.otus.enums.NominalEnum;
import ru.otus.exceptions.NotEnoughMoneyException;
import ru.otus.exceptions.NotEnoughNominalException;
import ru.otus.model.ATM;
import ru.otus.model.Banknote;
import ru.otus.model.MoneyStorage;

public class ATMImpl implements ATM {
    private final MoneyStorage moneyStorage;

    public ATMImpl(List<NominalEnum> supportedNominalEnums) {
        this.moneyStorage = new MoneyStorageImpl(supportedNominalEnums);
    }

    @Override
    public void topUp(List<Banknote> banknotes) {
        banknotes.forEach(moneyStorage::topUp);
    }

    @Override
    public List<Banknote> withdraw(int amount) {
        return moneyStorage.withdraw(amount);
    }

    @Override
    public int getBalance() {
        return moneyStorage.getBalance();
    }
}
