package com.mediworld.mwuserapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>Profile</h1>
 * Clase de informacion basica que comparten los payloads de los usuarios
 * de la aplicacion
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    private String id;
    private String nombre;
    private String apellidos;
    private LanguageResponse language;
    private String paisNacimiento;
    private String paisResidencia;

    public Profile(String id, String nombre, String apellidos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }
}
