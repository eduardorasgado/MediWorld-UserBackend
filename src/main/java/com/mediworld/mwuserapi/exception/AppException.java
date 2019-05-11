package com.mediworld.mwuserapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <h1>AppException</h1>
 * Si las peticiones a la actual API no son validas entonces se lanzan excepciones para
 * situaclines inesperadas que pueden ocurrir.
 * Esta clase responde con diferentes estatus Http para diferentes tipos de excepciones
 *
 * @author Eduardo Rasgado Ruiz
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException{

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
