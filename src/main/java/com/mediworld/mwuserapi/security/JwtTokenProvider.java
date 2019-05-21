package com.mediworld.mwuserapi.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

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
     * Metodo para generar un token con fecha de expiracion, en ms
     * @param authentication
     * @return
     */
    public String generateToken(Authentication authentication) {
        PacientePrincipal pacientePrincipal = (PacientePrincipal) authentication.getPrincipal();
        // integridad del token respecto al tiempo
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + this.jwtExpirationInMs);

        return Jwts.builder()
                // mandando el id del paciente como body
                .setSubject(pacientePrincipal.getId())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact();
    }

    /**
     * Metodo que devuelve el id de un paciente dado un token recibido
     * @param token
     * @return
     */
    public String getPacienteIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                // obteniendo el id del paciente
                .getBody();

        return claims.getSubject();
    }

    /**
     * metodo que valida la autenticidad de un token asi como su expiracion
     * en caso de errores escribe al logger
     * @param authToken
     * @return
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
