package com.mediworld.mwuserapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <h1>MedicoProfile</h1>
 * Payload con informacion esencial publica de un medico
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
@AllArgsConstructor
public class MedicoProfile extends Profile {

    public MedicoProfile(String id, String email, String nombre, String apellidos) {
        super(id, nombre, apellidos);
    }
}