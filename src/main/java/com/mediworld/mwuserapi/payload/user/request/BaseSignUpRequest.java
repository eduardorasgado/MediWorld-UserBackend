package com.mediworld.mwuserapi.payload.user.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * <h1>BaseSignUpRequest</h1>
 * Clase base para los payloads de registro de medico/paciente
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
public class BaseSignUpRequest {
    @NotBlank
    @Size(max = 50, min = 6)
    @Email
    private String email;
    @NotBlank
    @Size(max = 100, min = 6)
    private String password;
    @NotBlank
    @Size(max = 40, min = 2)
    private String nombre;
    @NotBlank
    @Size(max = 70, min = 2)
    private String apellidos;
    @Size(max=10)
    private String genero;
}
