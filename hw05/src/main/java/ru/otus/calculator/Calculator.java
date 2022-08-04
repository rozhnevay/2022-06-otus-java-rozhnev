package ru.otus.calculator;

import ru.otus.annotation.Log;

public interface Calculator {
    void add(int x, int y);

    @Log
    void add(int x, int y, int z);

    @Log
    void add(int x, int y, int z, int t);
}
