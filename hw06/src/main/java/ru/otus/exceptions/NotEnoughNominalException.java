package ru.otus.exceptions;

public class NotEnoughNominalException extends RuntimeException {
    private static final String MSG = "Not enough banknotes with suitable nominal to withdraw amount = ";

    public NotEnoughNominalException(int amount) {
        super(MSG + amount);
    }
}
