package ru.otus.exceptions;

public class NotEnoughMoneyException extends RuntimeException {
    private static final String MSG = "Not enough money to withdraw amount = ";

    public NotEnoughMoneyException(int amount) {
        super(MSG + amount);
    }
}
