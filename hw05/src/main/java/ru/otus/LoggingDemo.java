package ru.otus;

import ru.otus.calculator.CalculatorImpl;
import ru.otus.proxy.Ioc;

public class LoggingDemo {
    public static void main(String[] args) {
        var calculator = Ioc.createCalculator();
        calculator.add(1, 2);
        calculator.add(1, 2, 3);
        calculator.add(1, 2, 3, 4);
    }
}
