package com.mediworld.mwuserapi.model;

/**
 * <h1>PerfilName</h1>
 * Representa el perfil que puede tener un paciente dentro de la aplicacion
 *
 * @author Eduardo Rasgado Ruiz
 */
public enum PerfilName {
    // un paciente normal
    PACIENTE,
    // un paciente que actualmente esta vendiendo sus datos
    PACIENTE_ACTIVE,
    // medico normal en plan free
    MEDICO,
    // medico que tiene suscripcion
    MEDICO_ACTIVE
}