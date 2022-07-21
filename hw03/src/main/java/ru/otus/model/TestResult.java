package ru.otus.model;

import java.lang.reflect.Method;
import java.util.List;
import javafx.util.Pair;

public class TestResult {
    private final int cntTotal;
    private final int cntError;
    private final List<MethodException> exceptionList;

    public TestResult(int cntTotal, int cntError, List<MethodException> exceptionList) {
        this.cntTotal = cntTotal;
        this.cntError = cntError;
        this.exceptionList = exceptionList;
    }

    public int getCntTotal() {
        return cntTotal;
    }

    public int getCntError() {
        return cntError;
    }

    public List<MethodException> getExceptionList() {
        return exceptionList;
    }

    public static final class MethodException {
        Method method;
        Exception exception;

        public MethodException(Method method, Exception exception) {
            this.method = method;
            this.exception = exception;
        }

        public Method getMethod() {
            return method;
        }

        public Exception getException() {
            return exception;
        }
    }
}
