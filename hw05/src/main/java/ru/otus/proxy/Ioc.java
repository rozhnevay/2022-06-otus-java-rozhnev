package ru.otus.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import ru.otus.annotation.Log;
import ru.otus.calculator.Calculator;
import ru.otus.calculator.CalculatorImpl;

public class Ioc {

    private Ioc() {
    }

    public static Calculator createCalculator() {
        InvocationHandler handler = new CalculatorInvocationHandler(new CalculatorImpl());
        return (Calculator) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
            new Class<?>[]{Calculator.class}, handler);
    }

    static class CalculatorInvocationHandler implements InvocationHandler {
        private final Calculator calculator;
        private final List<Method> loggedMethods;

        CalculatorInvocationHandler(Calculator calculator) {
            this.calculator = calculator;
            this.loggedMethods = Arrays.stream(calculator.getClass().getMethods())
                .filter(method -> checkAnnotation(method, Log.class))
                .map(method -> getAppropriateMethodFromInterface(method, Calculator.class))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (loggedMethods.contains(method)) {
                System.out.println("executed method:" + method.getName() + "params: " + joinArgs(args));
            }
            return method.invoke(calculator, args);
        }

        @Override
        public String toString() {
            return "CalculatorInvocationHandler{" +
                "myClass=" + calculator +
                '}';
        }

        private boolean checkAnnotation(Method method, Class annotationClass) {
            return Arrays
                .stream(method.getDeclaredAnnotations())
                .anyMatch(annotation -> annotation.annotationType().equals(annotationClass));
        }

        private String joinArgs(Object[] args) {
            return Arrays.stream(args).map(Object::toString).reduce((x, y) -> String.join(",", x, y)).orElse("");
        }

        private Method getAppropriateMethodFromInterface(Method method, Class interfaceClass) {
            try {
                return interfaceClass.getMethod(method.getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                return null;
            }
        }
    }
}
