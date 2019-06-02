package com.mediworld.mwuserapi.controller;

import com.mediworld.mwuserapi.model.PerfilName;
import com.mediworld.mwuserapi.payload.JwtAuthenticationResponse;
import com.mediworld.mwuserapi.payload.MedicoLoginRequest;
import com.mediworld.mwuserapi.payload.MedicoSignUpRequest;
import com.mediworld.mwuserapi.security.JwtTokenProvider;
import com.mediworld.mwuserapi.services.IMedicoService;
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

/**
 * <h1>MedicoAuthController</h1>
 *
 * Controlador rest que se encarga de las transacciones del login y register del frontend
 * y el backend de la aplicacion para el medico.
 *
 * @author Eduardo Rasgado Ruiz
 * @see com.mediworld.mwuserapi.services.MedicoServiceImpl
 */
@RestController
@RequestMapping("/api/medico/auth")
public class MedicoAuthController {

    private AuthenticationManager authenticationManager;
    private IMedicoService medicoService;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;

    public MedicoAuthController(AuthenticationManager authenticationManager,
                            IMedicoService medicoService,
                            PasswordEncoder passwordEncoder,
                            JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.medicoService = medicoService;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    /**
     * Metodo que permite el logueo de un usuario de tipo medico
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateMedico(
            @Valid @RequestBody MedicoLoginRequest loginRequest
            ) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication, PerfilName.MEDICO);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerMedico(
            @Valid @RequestBody MedicoSignUpRequest signUpRequest
            ) {
        System.out.println(signUpRequest);
        return null;
    }
}
