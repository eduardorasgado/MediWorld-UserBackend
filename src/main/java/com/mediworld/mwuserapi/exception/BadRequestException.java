package com.mediworld.mwuserapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <h1>BadRequestException</h1>
 * Clase de manejo de instancia a enviar de error http status 500
 *
 * @author Eduardo Rasgado Ruiz
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
