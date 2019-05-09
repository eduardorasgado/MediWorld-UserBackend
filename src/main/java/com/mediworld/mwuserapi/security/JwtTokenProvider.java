package com.mediworld.mwuserapi.security;

import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * <h1>JwtTokenProvider</h1>
 * Proveedor de los tokens que se van a usar para la transferencia de datos en la aplicacion
 *
 * @author Eduardo Rasgado Ruiz
 */
@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecretKey}")
    private String jwtSecretKey;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    /**
     * Metodo para gneerar un token
     * @param authentication
     * @return
     */
    public String generateToken(Authentication authentication) {
        PacientePrincipal pacienteDetails = (PacientePrincipal) authentication.getPrincipal();
        return null;
    }

    /**
     * Metodo que devuelve el id de un paciente dado un token recibido
     * @param token
     * @return
     */
    public String getPacienteIdFromJWT(String token) {
        return null;
    }

    /**
     * metodo que valida la autenticidad de un token asi como su expiracion
     * @param authToken
     * @return
     */
    public boolean validateToken(String authToken) {
        return false;
    }
}
