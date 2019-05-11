package com.mediworld.mwuserapi.controller;

import com.mediworld.mwuserapi.payload.JwtAuthenticationResponse;
import com.mediworld.mwuserapi.payload.LoginRequest;
import com.mediworld.mwuserapi.security.JwtTokenProvider;
import com.mediworld.mwuserapi.services.IPacienteService;
import com.mediworld.mwuserapi.services.IPerfilService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**<h1>AuthController</h1>
 * Controlador de autenticacion que hace conexion con el frontend
 *
 * @author Eduardo Rasgado Ruiz
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private IPacienteService pacienteService;
    private IPerfilService perfilService;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager,
                          IPacienteService pacienteService,
                          IPerfilService perfilService,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.pacienteService = pacienteService;
        this.perfilService = perfilService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Metodo para manejar la peticion de logueo de un paciente
     * @param loginRequest
     * @return
     */
    @PostMapping("login")
    public ResponseEntity<?> authenticatePaciente(
            @Valid @RequestBody LoginRequest loginRequest) {

        // autenticando al usuario en cuestion
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // asignando la autenticacion del paciente
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // asignando un token al paciente logueado
        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

}
