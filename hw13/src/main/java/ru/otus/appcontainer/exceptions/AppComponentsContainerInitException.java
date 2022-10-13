package ru.otus.appcontainer.exceptions;

public class AppComponentsContainerInitException extends RuntimeException {

    public AppComponentsContainerInitException() {
        super();
    }

    public AppComponentsContainerInitException(String message) {
        super(message);
    }

    public AppComponentsContainerInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppComponentsContainerInitException(Throwable cause) {
        super(cause);
    }

    protected AppComponentsContainerInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}