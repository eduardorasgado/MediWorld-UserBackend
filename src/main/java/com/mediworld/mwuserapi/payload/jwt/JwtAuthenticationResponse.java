package com.mediworld.mwuserapi.payload.jwt;

import lombok.Data;

/**
 * <h1>JwtAuthenticationResponse</h1>
 * Payload de tipo Response
 * Clase que representa un payload para devolver una respuesta a la autenticacion
 */
@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
