package com.mediworld.mwuserapi.payload.response;

import lombok.Data;

/**
 * <h1>ApiResponse</h1>
 * Clase que representa el payload de una respuesta de la api,
 * en esta se incluye un mensaje y un estado de respuesta a cierta peticion
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
public class ApiResponse {
    private Boolean success;
    private String message;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
