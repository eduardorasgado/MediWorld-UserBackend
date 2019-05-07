package com.mediworld.mwuserapi.resources.vo;

import lombok.Data;

import java.util.Date;

/**
 * Clase que representa un paciente en bruto para realizar un mapeo con
 * la verdadera clase o modelo de paciente.
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
public class PacienteVO {
    private String username;
    private String email;
    private String password;
    private String nombre;
    private String apellidos;
    private Date fechaNacimiento;
    private boolean genero;
    private Date createdAt;
}
