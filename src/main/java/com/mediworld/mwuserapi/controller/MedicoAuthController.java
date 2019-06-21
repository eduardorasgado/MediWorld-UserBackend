package com.mediworld.mwuserapi.controller;

import com.mediworld.mwuserapi.exception.AppException;
import com.mediworld.mwuserapi.model.Genero;
import com.mediworld.mwuserapi.model.Medico;
import com.mediworld.mwuserapi.model.Perfil;
import com.mediworld.mwuserapi.model.PerfilName;
import com.mediworld.mwuserapi.payload.response.ApiResponse;
import com.mediworld.mwuserapi.payload.jwt.JwtAuthenticationResponse;
import com.mediworld.mwuserapi.payload.user.request.MedicoLoginRequest;
import com.mediworld.mwuserapi.payload.user.request.MedicoSignUpRequest;
import com.mediworld.mwuserapi.security.JwtTokenProvider;
import com.mediworld.mwuserapi.services.IMedicoService;
import com.mediworld.mwuserapi.services.IPerfilService;
import com.mediworld.mwuserapi.util.AppConstants;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

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
    private IPerfilService perfilService;

    public MedicoAuthController(AuthenticationManager authenticationManager,
                            IMedicoService medicoService,
                            PasswordEncoder passwordEncoder,
                            JwtTokenProvider tokenProvider,
                            IPerfilService perfilService) {
        this.authenticationManager = authenticationManager;
        this.medicoService = medicoService;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.perfilService = perfilService;
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

        System.out.println("email is: "+loginRequest.getEmail());
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
        // validando disponibilidad de email
        if(this.medicoService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(
                    false, "El email ya se encuentra registrado"
            ),
                    HttpStatus.BAD_REQUEST);
        }

        // creando la cuenta nueva
        Medico medico = new Medico();
        medico = this.mappingMedico(medico, signUpRequest);

        // asignar un perfil de medico simple
        Perfil perfil = this.perfilService.findByName(PerfilName.MEDICO);
        if(perfil == null) {
            throw new AppException("Perfil de usuario no asignado");
        }
        medico.setPerfiles(Collections.singleton(perfil));

        Medico result = this.medicoService.create(medico);

        // respuesta para redireccion embebida
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/medico/{id}")
                .buildAndExpand(result.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(new ApiResponse(
                        true, "Usuario registrado exitosamente"
                ));
    }

    private Medico mappingMedico(Medico medico, MedicoSignUpRequest medicoVO) {
        medico.setEmail(medicoVO.getEmail());
        medico.setNombre(medicoVO.getNombre());
        medico.setApellidos(medicoVO.getApellidos());
        medico.setPassword(passwordEncoder.encode(medicoVO.getPassword()));

        if(medicoVO.getGenero() != null) {
            if(medicoVO.getGenero().equals(AppConstants.HOMBRE)
                || medicoVO.getGenero().equals(AppConstants.MUJER)) {
                medico.setGenero(Genero.valueOf(medicoVO.getGenero()));
            }
        }

        // TODO: Crear CRUD para especialidades medicas, crear controller
        // con un getAllByLanguage, para ser listado y seleccionado en el frontend
        // del medico
        return medico;
    }
}
