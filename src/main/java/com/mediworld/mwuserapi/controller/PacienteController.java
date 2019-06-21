package com.mediworld.mwuserapi.controller;

import com.mediworld.mwuserapi.model.*;
import com.mediworld.mwuserapi.payload.request.PreferableLanguageRequest;
import com.mediworld.mwuserapi.payload.response.ApiResponse;
import com.mediworld.mwuserapi.payload.response.LanguageResponse;
import com.mediworld.mwuserapi.payload.user.response.PacienteProfile;
import com.mediworld.mwuserapi.payload.user.response.UserAuthDataAvailability;
import com.mediworld.mwuserapi.security.CurrentUsuario;
import com.mediworld.mwuserapi.security.PacientePrincipal;
import com.mediworld.mwuserapi.services.ICountryService;
import com.mediworld.mwuserapi.services.ILanguageService;
import com.mediworld.mwuserapi.services.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>PacienteController</h1>
 * Controlador principal para la transaccion de los recursos entre el backend y frontend relacionados
 * con el modelo Paciente.
 *
 * @author Eduardo Rasgado Ruiz
 */
@RestController
@RequestMapping("/api/paciente")
public class PacienteController {
    private IPacienteService pacienteService;

    private ILanguageService languageService;

    private ICountryService countryService;

    private static final Logger logger = LoggerFactory.getLogger(PacienteController.class);

    public PacienteController(IPacienteService pacienteService,
                              ILanguageService languageService,
                              ICountryService countryService) {
        this.pacienteService = pacienteService;
        this.languageService = languageService;
        this.countryService = countryService;
    }

    /**
     * Metodo para responder al cliente con los datos del usuario actualmente logueado dado
     * su token, también se incluye el lenguaje de preferencia por el paciente asi como su pais
     * de origen
     * @param currentPaciente datos del paciente autenticado con su token
     * @return entidad con los datos del paciente logueado
     */
    @GetMapping("/me")
    @PreAuthorize("hasAuthority('PACIENTE') or hasAuthority('PACIENTE_ACTIVE')")
    public PacienteProfile getCurrentPaciente(@CurrentUsuario PacientePrincipal currentPaciente) {
        PacienteProfile pacienteProfile = new PacienteProfile(
                currentPaciente.getId(),
                currentPaciente.getUsername(),
                currentPaciente.getNombre(),
                currentPaciente.getApellidos(),
                currentPaciente.getGenero(),
                currentPaciente.getEmail()
                );

        if(currentPaciente.getGeneroConviccion() != null) {
            pacienteProfile.setGeneroConviccion(
                    currentPaciente.getGeneroConviccion().name()
            );
        }
        if(!currentPaciente.getPaisNacimiento().isEmpty() ){
            pacienteProfile.setPaisNacimiento(currentPaciente.getPaisNacimiento());
        }
        if(!currentPaciente.getPaisResidencia().isEmpty()) {
            pacienteProfile.setPaisResidencia(currentPaciente.getPaisResidencia());
        }

        if(currentPaciente.getPreferableLanguageCode() != null){
            Language language = this.languageService.findByCode(
                    currentPaciente.getPreferableLanguageCode());

            pacienteProfile.setLanguage(new LanguageResponse(
                    language.getId(), language.getName(), language.getCode().name()
            ));
        }
        return pacienteProfile;
    }

    /**
     * Metodo que devuelve los datos de un determinado paciente, esto en caso de
     * perfiles publicos de pacientes.
     * @param username el nombre de usuario del paciente
     * @return entidad con datos del paciente
     */
    @GetMapping("/{username}")
    @PreAuthorize("hasAuthority('PACIENTE') or hasAuthority('PACIENTE_ACTIVE')")
    public ResponseEntity<PacienteProfile> getPacienteProfile(
            @PathVariable(value="username")  String username) {
        Paciente paciente = this.pacienteService.findByUsername(username);

        if(paciente != null){
            PacienteProfile pacienteProfile = new PacienteProfile(paciente.getId(),
                    paciente.getUsername(),
                    paciente.getNombre(),
                    paciente.getApellidos(),
                    paciente.getGenero(),
                    paciente.getEmail()
            );

            if(paciente.getGeneroConviccion() != null) {
                pacienteProfile.setGeneroConviccion(
                        paciente.getGeneroConviccion().name()
                );
            }


            return ResponseEntity.ok(pacienteProfile);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo que comprueba la existencia o disponibilidad de un username determinado en los registros
     * @param username
     * @return
     */
    @GetMapping("/checkUsernameAvailability")
    public UserAuthDataAvailability getUsernameAvailability(
            @RequestParam("username") String username) {
        Boolean availability = !this.pacienteService.existsByUsername(username);
        return new UserAuthDataAvailability(availability);
    }

    /**
     * Metodo que comprueba la existencia o disponibilidad de un email determinado en los registros
     * de pacientes
     * @param email
     * @return
     */
    @GetMapping("/checkEmailAvailability")
    public UserAuthDataAvailability getEmailAvailability(@RequestParam("email") String email){
        Boolean availability = !this.pacienteService.existsByEmail(email);
        return new UserAuthDataAvailability(availability);
    }

    /**
     * Metodo para devolver todas las entidades de paciente al frontend
     * @return una lista de pacientes
     */
    @GetMapping
    @PreAuthorize("hasAuthority('PACIENTE') or hasAuthority('PACIENTE_ACTIVE')")
    public ResponseEntity<List<PacienteProfile>> getAll() {
        List<PacienteProfile> pacientes = new ArrayList<>();

        for(Paciente p : this.pacienteService.getAll()) {
            PacienteProfile pacienteResponse = new PacienteProfile(
                    p.getId(),
                    p.getUsername(),
                    p.getNombre(),
                    p.getApellidos(),
                    p.getGenero(),
                    p.getEmail()
            );
            pacientes.add(pacienteResponse);
        }

        return ResponseEntity.ok(pacientes);
    }

    /**
     * Metodo para actualizar el lenguaje preferido por el usuario
     * @return
     */
    @PutMapping("/preferableLanguage")
    @PreAuthorize("hasAuthority('PACIENTE') or hasAuthority('PACIENTE_ACTIVE')")
    public ResponseEntity<?> setPreferableLanguage(
            @CurrentUsuario PacientePrincipal pacientePrincipal,
            @Valid @RequestBody PreferableLanguageRequest preferableLanguageRequest){

        Paciente paciente = this.pacienteService.findByUsername(pacientePrincipal.getUsername());

        if(!preferableLanguageRequest.getCode().isEmpty()){
            String code = preferableLanguageRequest.getCode();
            LanguageCode lCode = LanguageCode.valueOf(code.toLowerCase());
            Language language = this.languageService.findByCode(lCode);
            paciente.setPreferableLanguage(language);
            this.pacienteService.update(paciente);

            return ResponseEntity.ok(new ApiResponse(
                    true, "Se ha actualizado el idioma preferente"
            ));
        }
        return new ResponseEntity<>(new ApiResponse(
                false, "No existe el lenguaje determinado"
        ), HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo para actualizar parcialmente los datos del paciente, para esto
     * el paciente debe de estar logueado y enviar los datos a actualizar,
     * incluyendo el username original, este username no debe de exceder
     * @param currentPaciente
     * @param pacienteRequest
     * @return
     */
    @PatchMapping
    public ResponseEntity<PacienteProfile> updatePaciente(
            @CurrentUsuario PacientePrincipal currentPaciente,
            @RequestBody PacienteProfile pacienteRequest
    ) {
        if(pacienteRequest.getUsername().equals(currentPaciente.getUsername())) {
            Paciente paciente = this.pacienteService.findByUsername(currentPaciente.getUsername());
            // es etico cambiar el nombre y los apellidos??
            if(pacienteRequest.getNombre() != null){
                paciente.setNombre(pacienteRequest.getNombre());
            }
            if(pacienteRequest.getApellidos() != null) {
                paciente.setApellidos(pacienteRequest.getApellidos());
            }
            if(pacienteRequest.getGenero() != null) {
                Genero genero;
                try {
                    genero = Genero.valueOf(pacienteRequest.getGenero());
                    paciente.setGenero(genero);
                } catch(Exception e) {
                    logger.error("Se ha producido un error en patch de paciente: {}",
                            e.getMessage());
                }
            }
            if(pacienteRequest.getGeneroConviccion() != null) {
                Genero genero;
                try {
                    genero = Genero.valueOf(pacienteRequest.getGeneroConviccion());
                    paciente.setGeneroConviccion(genero);
                } catch(Exception e) {
                    logger.error("Se ha producido un error en patch de paciente: {}",
                            e.getMessage());
                }
            }
            if(pacienteRequest.getPaisNacimiento() != null) {
                Country country;
                country = this.countryService.findByName(pacienteRequest.getPaisNacimiento());
                if(country != null) {
                    paciente.setPaisNacimiento(country);
                } else {
                    logger.error("El pais de nacimiento resultante de mapear el pais del paciente " +
                            "request es nulo debido a un pais desconocido proveniente del request," +
                            "en Patch Paciente Controller");
                }
            }
            if(pacienteRequest.getPaisResidencia() != null) {
                Country country;
                country = this.countryService.findByName(pacienteRequest.getPaisResidencia());
                if(country != null) {
                    paciente.setPaisResidencia(country);
                } else {
                    logger.error("El pais de Residencia resultante de mapear el pais del paciente " +
                            "request es nulo debido a un pais desconocido proveniente del request," +
                            "en Patch Paciente Controller");
                }
            }
            this.pacienteService.update(paciente);
            PacienteProfile pacienteProfile;
            pacienteProfile = mappingPacienteToPacienteRequest(paciente);

            return new ResponseEntity<>(pacienteProfile, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    private PacienteProfile mappingPacienteToPacienteRequest(
            Paciente paciente ) {
        PacienteProfile pacienteProfile = new PacienteProfile(
                paciente.getId(),
                paciente.getUsername(),
                paciente.getNombre(),
                paciente.getApellidos(),
                paciente.getGenero(),
                paciente.getEmail()
        );
        if(paciente.getGeneroConviccion() != null) {
            pacienteProfile.setGeneroConviccion(paciente.getGeneroConviccion().name());
        }
        if(paciente.getPaisNacimiento() != null) {
            pacienteProfile.setPaisNacimiento(paciente.getPaisNacimiento().getName());
        }
        if(paciente.getPaisResidencia() != null) {
            pacienteProfile.setPaisResidencia(paciente.getPaisResidencia().getName());
        }

        return pacienteProfile;
    }
}
