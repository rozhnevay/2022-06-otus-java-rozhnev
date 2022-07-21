package ru.otus.runner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;
import ru.otus.model.TestResult;

public class TestRunner {
    private final String testClassName;

    public TestRunner(String testClassName) {
        this.testClassName = testClassName;
    }

    public TestResult run() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(testClassName);
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> beforeMethods = filterMethodsByAnnotation(methods, Before.class);
        List<Method> testMethods = filterMethodsByAnnotation(methods, Test.class);
        List<Method> afterMethods = filterMethodsByAnnotation(methods, After.class);

        int cntError = 0;
        int cntTotal = 0;
        List<TestResult.MethodException> exceptionList = new ArrayList<>();

        for (Method testMethod : testMethods) {
            Constructor constructor = clazz.getConstructor();
            Object testClassInstance = constructor.newInstance();
            beforeMethods.forEach(method -> {
                try {
                    method.invoke(testClassInstance);
                } catch (Exception e) {
                    exceptionList.add(new TestResult.MethodException(method, e));
                }
            });

            try {
                testMethod.invoke(testClassInstance);
            } catch (Exception e) {
                exceptionList.add(new TestResult.MethodException(testMethod, e));
                cntError++;
            } finally {
                cntTotal++;
            }

            afterMethods.forEach(method -> {
                try {
                    method.invoke(testClassInstance);
                } catch (Exception e) {
                    exceptionList.add(new TestResult.MethodException(method, e));
                }
            });
        }

        return new TestResult(cntTotal, cntError, exceptionList);
    }

    private List<Method> filterMethodsByAnnotation(Method[] methods, Class annotationClass) {
        return Arrays.stream(methods)
            .filter(
                method ->
                    Arrays.stream(method.getDeclaredAnnotations())
                        .anyMatch(annotation -> annotation.annotationType().equals(annotationClass))
            ).collect(Collectors.toList());
    }
}
