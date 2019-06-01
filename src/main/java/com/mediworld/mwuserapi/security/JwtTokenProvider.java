package com.mediworld.mwuserapi.security;

import com.mediworld.mwuserapi.model.PerfilName;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>JwtTokenProvider</h1>
 * Proveedor de los tokens que se van a usar para la transferencia de datos en la aplicacion
 *
 * @author Eduardo Rasgado Ruiz
 */
@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtPacienteSecretKey}")
    private String jwtSecretKey;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    // TODO: incorporar los campos de claims para medico, paciente perfil
    // TODO: Incorporar setters y getters para los claims

    public static final String CLAIM_KEY_USER_ID = "id";
    public static final String CLAIM_KEY_USERNAME = "username";
    public static final String CLAIM_KEY_PROFILE = "profile";
    public static final String CLAIM_KEY_MEDICO = "medico";
    public static final String CLAIM_KEY_PACIENTE = "paciente";

    /**
     * Metodo para generar un token especifico por cada tipo de usuario con fecha de expiracion, en ms,
     * Se crea un claims con dos llaves: id, profile
     * @param authentication
     * @return
     */
    public String generateToken(Authentication authentication, PerfilName perfilName) {
        // integridad del token respecto al tiempo
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + this.jwtExpirationInMs);
        Map<String, Object> claims = new HashMap<>();

        if(perfilName == PerfilName.PACIENTE){
            PacientePrincipal pacientePrincipal = (PacientePrincipal) authentication.getPrincipal();
            claims.put(CLAIM_KEY_USER_ID, pacientePrincipal.getId());
            claims.put(CLAIM_KEY_USERNAME, pacientePrincipal.getUsername());
            claims.put(CLAIM_KEY_PROFILE, CLAIM_KEY_PACIENTE);

            System.out.println("paciente del provider: "+pacientePrincipal.getId());

        } else if(perfilName == PerfilName.MEDICO) {
            MedicoPrincipal medicoPrincipal = (MedicoPrincipal) authentication.getPrincipal();
            claims.put(CLAIM_KEY_USER_ID, medicoPrincipal.getId());
            claims.put(CLAIM_KEY_PROFILE, CLAIM_KEY_MEDICO);
            // username para el medico es el email
            claims.put(CLAIM_KEY_USERNAME, medicoPrincipal.getUsername());

            System.out.println("medico del provider: "+medicoPrincipal.getId());
        } else {
            return null;
        }
        return Jwts.builder()
                // mandando el id del paciente como body
                //.setSubject(pacientePrincipal.getId())
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact();
    }

    /**
     * Metodo para obtener el Map claim con los grupos de llave, valor
     * @param token
     * @return
     */
    private Claims getClaimsFromJWT(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(token)
                    // obteniendo el id del paciente
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * Metodo que devuelve el id de un paciente dado un token recibido
     * @param token
     * @return
     */
    public String getUserIdFromJWT(String token) {
        String userId;
        try {
            Claims claims = this.getClaimsFromJWT(token);
            userId = (String) claims.get(CLAIM_KEY_USER_ID);
        } catch (Exception e) {
            userId = null;
        }
        return userId;
    }

    /**
     * Metodo para obtener la llave username dentro de los claims del token
     * @param token
     * @return
     */
    public String getUsernameFromJWT(String token) {
        String username;
        try {
            Claims claims = this.getClaimsFromJWT(token);
            username = (String) claims.get(CLAIM_KEY_USERNAME);
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * Metodo para obtener el tipo de usuario o perfil de usuario del que se trata
     * @param token
     * @return
     */
    public String getProfileFromJWT(String token) {
        String profile;
        try {
            Claims claims = this.getClaimsFromJWT(token);
            profile = (String) claims.get(CLAIM_KEY_PROFILE);
        } catch (Exception e) {
            profile = null;
        }
        return profile;
    }

    //TODO: funciones Refresh token, can token be refresh
    /**
     * metodo que valida la autenticidad de un token asi como su expiracion
     * en caso de errores escribe al logger.
     * Valida:
     *  Si la clave secreta es la misma que maneja la aplicacion en esta misma clase
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
