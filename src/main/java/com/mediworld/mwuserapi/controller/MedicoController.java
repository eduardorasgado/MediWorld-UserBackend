package com.mediworld.mwuserapi.controller;

import com.mediworld.mwuserapi.payload.user.response.MedicoProfile;
import com.mediworld.mwuserapi.payload.user.response.UserAuthDataAvailability;
import com.mediworld.mwuserapi.security.CurrentUsuario;
import com.mediworld.mwuserapi.security.MedicoPrincipal;
import com.mediworld.mwuserapi.services.IMedicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <h1>MedicoController</h1>
 * Controlador rest para la interaccion del frontend con el medico
 *
 * @author Eduardo Rasgado Ruiz
 */
@RestController
@RequestMapping("/api/medico")
public class MedicoController {

    private IMedicoService medicoService;

    public MedicoController(IMedicoService medicoService) {
        this.medicoService = medicoService;
    }

    /**
     * Metodo para obtener los datos del medico actualmente logueado
     * @return
     */
    @GetMapping("/me")
    @PreAuthorize("hasAuthority('MEDICO') or hasAuthority('MEDICO_ACTIVE')")
    public MedicoProfile getCurrentMedico(
            @CurrentUsuario MedicoPrincipal currentMedico
            ) {

        MedicoProfile medicoProfile = new MedicoProfile(
                currentMedico.getId(),
                currentMedico.getEmail(),
                currentMedico.getNombre(),
                currentMedico.getApellidos(),
                currentMedico.getGenero()
        );
        return medicoProfile;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICO') or hasAuthority('MEDICO_ACTIVE')")
    public ResponseEntity<MedicoProfile> getMedicoProfile(
            @PathVariable(value = "id") String idMedico
    ) {
        System.out.println("Id medico: "+idMedico);
        return null;
    }

    @GetMapping("/checkEmailAvailability")
    public UserAuthDataAvailability getEmailAvailability(
            @RequestParam("email") String email
    ) {
        boolean emailAvailable = !this.medicoService.existsByEmail(email);
        return new UserAuthDataAvailability(
                emailAvailable
        );
    }
}
