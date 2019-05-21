package com.mediworld.mwuserapi.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *<h1>JwtAuthenticationFilter</h1>
 *
 * Clase que representa un filtro de autenticacion para el paciente haciendo uso de
 * los jason web tokens
 *
 * @author Eduardo Rasgado Ruiz
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private PacienteDetailsService pacienteDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwtToken = this.getJwtFromRequest(request);

            // si existe el token y si el token es valido
            if(StringUtils.hasText(jwtToken) && this.tokenProvider.validateToken(jwtToken)) {
                String pacienteId = this.tokenProvider.getPacienteIdFromJWT(jwtToken);

                UserDetails pacienteDetails = this.pacienteDetailsService.loadUserById(pacienteId);

                // se hace una presentacion estandarizada de los datos de paciente
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(pacienteDetails, null,
                                pacienteDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                                    .buildDetails(request));
            }
        } catch (Exception e) {
            logger.error("No se pudo configurar la autenticacion del usuario en un contexto de seguridad", e);
        }

        // a Filter hides a number of details
        /**
         * The FilterChain#doFilter() call just continues the HTTP request to the destination,
         * following exactly the same path as if you didn't use a filter in first place.
         */
        filterChain.doFilter(request, response);
    }

    /**
     * Meotod para obtener el token de una peticion del cliente
     * @param request peticion
     * @return el token extraido del request
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        System.out.println("Token de seguridad: ");
        System.out.println(bearerToken);

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
