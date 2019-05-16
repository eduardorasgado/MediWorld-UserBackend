package com.mediworld.mwuserapi.resources.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * DEPRECATED
 * Clase que representa un paciente en bruto para realizar un mapeo con
 * la verdadera clase o modelo de paciente.
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
public class PacienteVO {
    @NotBlank
    @Size(max = 20, min = 3)
    private String username;
    @NotBlank
    @Size(max = 40, min = 6)
    @Email
    private String email;
    @NotBlank
    @Size(max = 100, min = 6)
    private String password;
    @NotBlank
    @Size(max = 100, min = 2)
    private String nombre;
    @NotBlank
    @Size(max = 100, min = 2)
    private String apellidos;
    private Date fechaNacimiento;
    private String genero;
}
