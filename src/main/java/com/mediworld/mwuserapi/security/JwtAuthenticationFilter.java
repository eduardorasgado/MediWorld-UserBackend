package com.mediworld.mwuserapi.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private MedicoDetailsService medicoDetailsService;


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
            if(jwtToken != null){
                // si existe el token y si el token es valido
                if(StringUtils.hasText(jwtToken) && this.tokenProvider.validateToken(jwtToken)) {
                    //String userId = this.tokenProvider.getUserIdFromJWT(jwtToken);
                    String username = this.tokenProvider.getUsernameFromJWT(jwtToken);

                /*
                    Note that you could also encode the user's username and roles inside JWT claims
                    and create the UserDetails object by parsing those claims from the JWT.
                    That would avoid the following database hit. It's completely up to you.
                 */
                    UsernamePasswordAuthenticationToken authenticationToken = null;
                    System.out.println("PACIENTE IS IN TOKEN: "+this.tokenProvider.getProfileFromJWT(jwtToken)
                            .equals(JwtTokenProvider.CLAIM_KEY_PACIENTE));

                    if(this.tokenProvider.getProfileFromJWT(jwtToken)
                            .equals(JwtTokenProvider.CLAIM_KEY_PACIENTE)){

                        UserDetails pacienteDetails = this.pacienteDetailsService.loadUserByUsername(
                                username
                        );

                        System.out.println("paciente data: "+pacienteDetails);
                        // se hace una presentacion estandarizada de los datos de paciente
                        authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        pacienteDetails,
                                        null,
                                        pacienteDetails.getAuthorities());
                    }

                    System.out.println("MEDICO IS IN: TOKEN "+this.tokenProvider.getProfileFromJWT(jwtToken)
                            .equals(JwtTokenProvider.CLAIM_KEY_MEDICO));

                    if(this.tokenProvider.getProfileFromJWT(jwtToken)
                            .equals(JwtTokenProvider.CLAIM_KEY_MEDICO)) {
                        UserDetails medicoDetails = this.medicoDetailsService.loadUserByUsername(
                                username
                        );

                        System.out.println("medico data: "+medicoDetails);
                        authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        medicoDetails,
                                        null,
                                        medicoDetails.getAuthorities()
                                );

                    }

                    if(authenticationToken != null){
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                        // cargar los datos de auntentificacion
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
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
            System.out.println(bearerToken.substring(7, bearerToken.length()));
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
