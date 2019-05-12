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
     * Metodo para guardar un nuevo perfil en la base de datos por medio de
     * un perfil recibido como parametro
     * @param perfil perfil a ser creado
     * @return perfil ya creado
     */
    public Perfil create(Perfil perfil);
    /**
     * Metodo que devuelve un perfil dado un nombre de perfil
     * @param perfilName
     * @return
     */
    public Perfil findByName(PerfilName perfilName);
}
