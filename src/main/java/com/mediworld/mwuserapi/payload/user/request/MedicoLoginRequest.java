package com.mediworld.mwuserapi.payload.user.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Payload que se trae la informacion que trae el request lo login del medico
 *
 * @author Eduardo Rasgado Ruiz
 * @see com.mediworld.mwuserapi.controller.MedicoAuthController
 */
@Data
public class MedicoLoginRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}
