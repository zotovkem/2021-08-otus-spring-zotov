package ru.zotov.hw14.exception;

/**
 * @author Created by ZotovES on 05.03.2021
 * Исключение удаления связанной сущности
 */
public class ConstrainDeleteException extends Exception {

    public ConstrainDeleteException() {
        super();
    }

    public ConstrainDeleteException(String message) {
        super(message);
    }

    public ConstrainDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConstrainDeleteException(Throwable cause) {
        super(cause);
    }

    protected ConstrainDeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
