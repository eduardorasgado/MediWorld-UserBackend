package com.mediworld.mwuserapi.controller;

import com.mediworld.mwuserapi.model.Medico;
import com.mediworld.mwuserapi.services.IMedicoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @return+
     */
    @GetMapping("/me")
    public Medico getCurrentMedico() {
        System.out.println("Youre calling [Medico Rest Controller]");
        return null;
    }
}
