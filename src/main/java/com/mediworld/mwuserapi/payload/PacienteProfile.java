package com.mediworld.mwuserapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>PacienteProfile</h1>
 * Compilado de solamente datos que se devuelven desde el servidor hacia el cliente
 * especificamente estos datos son del paciente logueado
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteProfile extends Profile {
    private String username;

    public PacienteProfile(String id, String username, String nombre, String apellidos) {
        super(id, nombre, apellidos);
        this.username = username;
    }
}
