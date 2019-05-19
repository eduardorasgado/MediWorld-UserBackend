package com.mediworld.mwuserapi.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * <h1>SignUpRequest</h1>
 * Payload de tipo Request
 * Clase que representa un payload para la peticion a la api, para el registro del user
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
public class SignUpRequest {
    @NotBlank
    @Size(max = 30, min = 3)
    private String username;
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
    private Date fechaNacimiento;
    @Size(max=10)
    private String genero;
}
