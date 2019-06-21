package com.mediworld.mwuserapi.payload.user.response;

import com.mediworld.mwuserapi.model.Genero;
import com.mediworld.mwuserapi.payload.user.response.Profile;
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
    private String generoConviccion;

    public PacienteProfile(String id, String username, String nombre,
                           String apellidos, Genero genero, String email) {
        super(id, nombre, apellidos, genero.name(), email);
        this.username = username;
    }

    @Override
    public String toString() {
        return "PacienteProfile{" +
                "username='" + username + '\'' +
                ", generoConviccion='" + generoConviccion + '\'' +
                "} " + super.toString();
    }
}
