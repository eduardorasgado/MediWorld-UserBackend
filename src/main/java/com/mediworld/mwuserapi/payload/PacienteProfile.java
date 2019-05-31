package com.mediworld.mwuserapi.payload;

import lombok.Data;

/**
 * <h1>PacienteProfile</h1>
 * Compilado de solamente datos que se devuelven desde el servidor hacia el cliente
 * especificamente estos datos son del paciente logueado
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
public class PacienteProfile {
    private String id;
    private String username;
    private String nombre;
    private String apellidos;
    private LanguageResponse language;
    private String paisNacimiento;
    private String paisResidencia;

    public PacienteProfile(String id, String username, String nombre, String apellidos) {
        this.id = id;
        this.username = username;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }
}
