package com.mediworld.mwuserapi.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <h1>LoginRequest</h1>
 *
 * Payload de tipo Request
 * Clase que representa un payload para la peticion a la api, para el login
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
public class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
