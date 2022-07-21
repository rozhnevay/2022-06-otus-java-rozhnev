package ru.otus.runner;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;
import ru.otus.test.TestClass;

public class TestRunner {
    private static final String EXCEPTION_ERR_MSG = "%s %s: %s";

    private final String testClassName;

    public TestRunner(String testClassName) {
        this.testClassName = testClassName;
    }

    public void run() throws ClassNotFoundException {
        Class<?> clazz = Class.forName(testClassName);
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> beforeMethods = filterMethodsByAnnotation(methods, Before.class.getName());
        List<Method> testMethods = filterMethodsByAnnotation(methods, Test.class.getName());
        List<Method> afterMethods = filterMethodsByAnnotation(methods, After.class.getName());

        int cntError = 0;
        int cntTotal = 0;

        for (Method testMethod : testMethods) {
            final TestClass testClass = new TestClass();
            beforeMethods.forEach(method -> {
                try {
                    method.invoke(testClass);
                } catch (Exception e) {
                    System.out.println(String.format(EXCEPTION_ERR_MSG, Before.class.getName(), method.getName(), e.getMessage()));
                }
            });

            try {
                testMethod.invoke(testClass);
            } catch (Exception e) {
                System.out.println(String.format(EXCEPTION_ERR_MSG, Test.class.getName(), testMethod.getName(), e.getCause()));
                cntError++;
            } finally {
                cntTotal++;
            }

            afterMethods.forEach(method -> {
                try {
                    method.invoke(testClass);
                } catch (Exception e) {
                    System.out.println(String.format(EXCEPTION_ERR_MSG, After.class.getName(), method.getName(), e.getMessage()));
                }
            });
        }
        System.out.println("Total number of tests: " + cntTotal);
        System.out.println("Number of failed tests: " + cntError);

    }

    private List<Method> filterMethodsByAnnotation(Method[] methods, String annotationName) {
        return Arrays.stream(methods)
            .filter(
                method ->
                    Arrays.stream(method.getDeclaredAnnotations())
                        .anyMatch(annotation -> annotation.annotationType().getName().equals(annotationName))
            ).collect(Collectors.toList());
    }
}
