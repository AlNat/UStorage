package dev.alnat.ustorage.exception;

/**
 * Внутрениий checked Exception для ошибок внтури приложения
 *
 * Created by @author AlNat on 12.01.2020.
 * Licensed by Apache License, Version 2.0
 */
public class UStorageException extends Exception {

    private static final long serialVersionUID = 82324325734783L;

    public UStorageException(String message) {
        super(message);
    }
    public UStorageException(Throwable cause) {
        super(cause);
    }

    public UStorageException(String message, Throwable cause) {
        super(message, cause);
    }

}
