package com.mediworld.mwuserapi.controller;

import com.mediworld.mwuserapi.exception.AppException;
import com.mediworld.mwuserapi.model.Genero;
import com.mediworld.mwuserapi.model.Paciente;
import com.mediworld.mwuserapi.model.Perfil;
import com.mediworld.mwuserapi.model.PerfilName;
import com.mediworld.mwuserapi.payload.ApiResponse;
import com.mediworld.mwuserapi.payload.JwtAuthenticationResponse;
import com.mediworld.mwuserapi.payload.LoginRequest;
import com.mediworld.mwuserapi.payload.SignUpRequest;
import com.mediworld.mwuserapi.security.JwtTokenProvider;
import com.mediworld.mwuserapi.services.IPacienteService;
import com.mediworld.mwuserapi.services.IPerfilService;
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
    @PostMapping("/login")
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

    /**
     * Metodo para manejar una peticion de registro de un nuevo paciente desde el lado del
     * frontend.
     * @param signUpRequest los datos del nuevo paciente
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerPaciente(
            @Valid @RequestBody SignUpRequest signUpRequest) {

        // validando disponibilidad de el username
        if(pacienteService.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(
                    false, "El nombre de usuario ya está en uso"
            ), HttpStatus.BAD_REQUEST);
        }

        // validando disponibilidad de el correo
        if(pacienteService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(
                    false, "El correo electrónico ya está registrado"
            ), HttpStatus.BAD_REQUEST);
        }

        // Creando la nueva cuenta
        Paciente paciente = new Paciente();
        paciente = this.mappingPaciente(paciente, signUpRequest);

        Perfil perfil = perfilService.findByName(PerfilName.PACIENTE);
        if (perfil == null) {
            throw new AppException("Perfil de usuario no asignado");
        }
        // asignando el perfil al paciente
        paciente.setPerfiles(Collections.singleton(perfil));

        // guardando el paciente en la db
        Paciente result = this.pacienteService.create(paciente);

        // donde ir despues de la creacion del paciente
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/paciente/{username}")
                .buildAndExpand(result.getUsername())
                .toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(
                        true, "Usuario registrado exitosamente"
                ));
    }

    public Paciente mappingPaciente(Paciente paciente, SignUpRequest pacienteVO) {
        paciente.setUsername(pacienteVO.getUsername());
        paciente.setEmail(pacienteVO.getEmail());
        paciente.setPassword(passwordEncoder.encode(pacienteVO.getPassword()));
        paciente.setNombre(pacienteVO.getNombre());
        paciente.setApellidos(pacienteVO.getApellidos());
        paciente.setFechaNacimiento(pacienteVO.getFechaNacimiento());
        String genero = pacienteVO.getGenero();
        if(!genero.isEmpty()) {
            if(genero.equals(Genero.HOMBRE) ||
                    genero.equals(Genero.MUJER)){
                if(genero.equals(Genero.HOMBRE)){
                    paciente.setGenero(Genero.HOMBRE);
                } else {
                    paciente.setGenero(Genero.MUJER);
                }
            }
        }
        return paciente;
    }

}
