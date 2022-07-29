package ru.otus;

import java.lang.reflect.InvocationTargetException;
import ru.otus.annotation.Before;
import ru.otus.model.TestResult;
import ru.otus.runner.TestRunner;

public class TestFrameworkApplication {
    private static final String EXCEPTION_ERR_MSG = "%s: %s";

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestRunner testRunner = new TestRunner("ru.otus.test.TestClass");
        TestResult testResult = testRunner.run();

        testResult.getExceptionList().forEach(exception -> {
            System.out.println(String.format(EXCEPTION_ERR_MSG, exception.getMethod().getName(), exception.getException().getCause()));
        });
        System.out.println("Total number of tests: " + testResult.getCntTotal());
        System.out.println("Number of failed tests: " + testResult.getCntError());

    }
}
