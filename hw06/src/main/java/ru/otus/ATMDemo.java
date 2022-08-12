package ru.otus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import ru.otus.enums.NominalEnum;
import ru.otus.model.ATM;
import ru.otus.model.Banknote;
import ru.otus.model.impl.ATMImpl;
import ru.otus.model.impl.BanknoteImpl;

public class ATMDemo {

    public static void main(String[] args) {
        ATM atm = new ATMImpl(
            Arrays.stream(NominalEnum.values()).toList()
        );

        atm.topUp(List.of(
            new BanknoteImpl(NominalEnum.ONE),
            new BanknoteImpl(NominalEnum.ONE),
            new BanknoteImpl(NominalEnum.ONE),
            new BanknoteImpl(NominalEnum.TWO),
            new BanknoteImpl(NominalEnum.FIVE)
        ));

        withdrawWithInfo(atm, 2);
        withdrawWithInfo(atm, 4);
        withdrawWithInfo(atm, 5);
        withdrawWithInfo(atm, 1);
        withdrawWithInfo(atm, 3);
    }

    private static void withdrawWithInfo(ATM atm, int amount) {
        System.out.println("Trying to withdraw: " + amount);

        try {
            var withdrawnBanknotes = atm.withdraw(amount);
            printWithdrawnBanknotesAsPairs(withdrawnBanknotes);
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("ATM balance after withdraw = " + atm.getBalance());
        System.out.println("================================================");
    }

    private static void printWithdrawnBanknotesAsPairs(List<Banknote> withdrawnBanknotes) {
        var banknotesGrouped = withdrawnBanknotes
            .stream()
            .collect(Collectors.groupingBy(Banknote::getNominalValue));

        banknotesGrouped.forEach((key, value) -> System.out.println(
            "Nominal " +
                key +
                " count = " +
                value.size()));
    }

}
