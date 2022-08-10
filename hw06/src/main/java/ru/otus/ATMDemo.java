package ru.otus;

import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import ru.otus.enums.NominalEnum;
import ru.otus.model.ATM;
import ru.otus.model.ATMImpl;

public class ATMDemo {

    public static void main(String[] args) {
        ATM atm = new ATMImpl();
        atm.topUp(List.of(
            NominalEnum.ONE,
            NominalEnum.ONE,
            NominalEnum.ONE,
            NominalEnum.TWO,
            NominalEnum.FIVE
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
            var withdrawnPairs = atm.withdraw(amount);
            printWithdrawnPairs(withdrawnPairs);
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("ATM balance after withdraw = " + atm.getBalance());
        System.out.println("================================================");
    }

    private static void printWithdrawnPairs(List<Pair<NominalEnum, Integer>> withdrawnPairs) {
        withdrawnPairs.forEach(withdrawnPair ->
            System.out.println(
                "Nominal " +
                    withdrawnPair.getKey().getValue() +
                    " count = " +
                    withdrawnPair.getValue()));
    }

}
