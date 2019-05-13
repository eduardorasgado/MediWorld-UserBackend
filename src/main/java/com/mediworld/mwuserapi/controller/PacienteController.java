package com.mediworld.mwuserapi.controller;

import com.mediworld.mwuserapi.model.PerfilName;
import com.mediworld.mwuserapi.payload.PacienteProfile;
import com.mediworld.mwuserapi.security.CurrentPaciente;
import com.mediworld.mwuserapi.security.PacientePrincipal;
import com.mediworld.mwuserapi.services.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private static final Logger logger = LoggerFactory.getLogger(PacienteController.class);

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    /**
     * Metodo para responder al cliente con los datos del usuario actualmente logueado dado
     * su token
     * @param currentPaciente
     * @return
     */
    @GetMapping("/me")
    @PreAuthorize("hasRole('PACIENTE') or hasRole('PACIENTE_ACTIVE')")
    public PacienteProfile getCurrentPaciente(@CurrentPaciente PacientePrincipal currentPaciente) {
        PacienteProfile pacienteProfile = new PacienteProfile(
                currentPaciente.getId(),
                currentPaciente.getUsername(),
                currentPaciente.getNombre(),
                currentPaciente.getApellidos());

        return pacienteProfile;
    }
}
