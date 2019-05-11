package com.mediworld.mwuserapi.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <h1>ResourceNotFoundException</h1>
 * Clase que representa un status http de tipo no encontrado
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends  RuntimeException{
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
