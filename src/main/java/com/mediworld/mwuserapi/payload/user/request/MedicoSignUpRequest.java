package com.mediworld.mwuserapi.payload.user.request;

import com.mediworld.mwuserapi.payload.user.request.BaseSignUpRequest;
import lombok.Data;

/**
 * <h1>MedicoSignUpRequest</h1>
 * Payload que parsea la informacion que viene del registro de un medico
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
public class MedicoSignUpRequest extends BaseSignUpRequest {
    /**
     * Nota para version 1.0: Este id es de la especialidad medica dado el lenguage
     * de preferencia que maneja el medico, esta especialidad no cambia al cambiar
     * el pais de residencia.
     * Esta especialidad es aplicable para el idioma del pais de residencia
     */
    //private String idEspecialidadMedica;
}
