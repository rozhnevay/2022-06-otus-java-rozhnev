package ru.otus.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import ru.otus.annotation.Log;
import ru.otus.calculator.Calculator;
import ru.otus.calculator.CalculatorImpl;

public class Ioc {

    private Ioc() {
    }

    public static Calculator createMyClass() {
        InvocationHandler handler = new CalculatorInvocationHandler(new CalculatorImpl());
        return (Calculator) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
            new Class<?>[]{Calculator.class}, handler);
    }

    static class CalculatorInvocationHandler implements InvocationHandler {
        private final Calculator myClass;

        CalculatorInvocationHandler(Calculator myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (checkAnnotation(method, Log.class)) {
                System.out.println("executed method:" + method.getName() + "params: " + joinArgs(args));
            }
            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "CalculatorInvocationHandler{" +
                "myClass=" + myClass +
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
    }
}
