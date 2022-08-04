package ru.otus.calculator;

import ru.otus.annotation.Log;

public class CalculatorImpl implements Calculator {

    public void add(int x, int y) {
        System.out.println("add(int x, int y)");
    }

    @Override
    public void add(int x, int y, int z) {
        System.out.println("add(int x, int y, int z)");
    }

    @Override
    public void add(int x, int y, int z, int t) {
        System.out.println("add(int x, int y, int z, int t)");
    }
}
