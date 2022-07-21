package ru.otus;

import ru.otus.runner.TestRunner;

public class TestFrameworkApplication {
    public static void main(String[] args) throws ClassNotFoundException {
        TestRunner testRunner = new TestRunner("ru.otus.test.TestClass");
        testRunner.run();
    }
}
