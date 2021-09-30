package ru.otus.spring.exception;

/**
 * @author Created by ZotovES on 05.03.2021
 * Исключение чтения CSV файла
 */
public class CannotReadFileException extends RuntimeException {

    public CannotReadFileException() {
        super();
    }

    public CannotReadFileException(String message) {
        super(message);
    }

    public CannotReadFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotReadFileException(Throwable cause) {
        super(cause);
    }

    protected CannotReadFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
