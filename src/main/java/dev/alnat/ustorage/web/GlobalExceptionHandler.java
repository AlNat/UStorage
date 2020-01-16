package dev.alnat.ustorage.web;

import dev.alnat.ustorage.exception.UStorageException;
import dev.alnat.ustorage.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Глобальный перехватчик ошибок
 *
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(UStorageException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void catchUStorageException(UStorageException e) {
        log.error("Ошибка: {}", e.getMessage());
        log.error("Stack: {}", Util.getStackTrace(e));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void catchException(Exception e) {
        log.error("Ошибка: {}", e.getMessage());
        log.error("Stack: {}", Util.getStackTrace(e));
    }

}
