package com.mediworld.mwuserapi.controller;

import com.mediworld.mwuserapi.model.Language;
import com.mediworld.mwuserapi.model.LanguageCode;
import com.mediworld.mwuserapi.model.Paciente;
import com.mediworld.mwuserapi.payload.ApiResponse;
import com.mediworld.mwuserapi.payload.PacienteProfile;
import com.mediworld.mwuserapi.payload.PreferableLanguageRequest;
import com.mediworld.mwuserapi.payload.UserAuthDataAvailability;
import com.mediworld.mwuserapi.security.CurrentUsuario;
import com.mediworld.mwuserapi.security.PacientePrincipal;
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

    private static final Logger logger = LoggerFactory.getLogger(PacienteController.class);

    public PacienteController(IPacienteService pacienteService,
                              ILanguageService languageService) {
        this.pacienteService = pacienteService;
        this.languageService = languageService;
    }

    /**
     * Metodo para responder al cliente con los datos del usuario actualmente logueado dado
     * su token
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
                currentPaciente.getApellidos());

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
    public PacienteProfile getPacienteProfile(@PathVariable(value="username")  String username) {
        Paciente paciente = this.pacienteService.findByUsername(username);

        if(paciente != null){
            PacienteProfile pacienteProfile = new PacienteProfile(paciente.getId(),
                    paciente.getUsername(),
                    paciente.getNombre(),
                    paciente.getApellidos());

            return pacienteProfile;
        }
        return null;
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
                    p.getApellidos()
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

}
