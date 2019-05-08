package com.mediworld.mwuserapi.services;

import com.mediworld.mwuserapi.model.Perfil;
import com.mediworld.mwuserapi.model.PerfilName;

import java.util.Optional;

/**
 * <h1>IPerfilService</h1>
 * Interfaz que define los metodos para el servicio de un perfil de paciente
 */
public interface IPerfilService {
    /**
     * Metodo que devuelve un perfil dado un nombre de perfil
     * @param perfilName
     * @return
     */
    public Perfil findByName(PerfilName perfilName);
}
