package com.mediworld.mwuserapi.payload.user.response;

import lombok.Data;

/**
 * Clase que permite enviar la disponibilidad de nombre de usuario y correo electronico de un
 * paciente a la api
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
public class UserAuthDataAvailability {
    private Boolean available;

    public UserAuthDataAvailability(Boolean available) {
        this.available = available;
    }
}
