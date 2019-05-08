package com.mediworld.mwuserapi.repository;

import com.mediworld.mwuserapi.model.Perfil;
import com.mediworld.mwuserapi.model.PerfilName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <h1>PerfilRepository</h1>
 * Repositorio para las transacciones con la tabla paciente_perfiles,
 * que determinan el perfil de un paciente
 *
 * @author Eduardo Rasgado Ruiz
 */
@Repository
public interface PerfilRepository extends JpaRepository<Perfil, String> {

    /**
     * Metodo que devuelve un perfil dentro de un contenedor de tipo opcional dado un nombre de
     * de perfil proporcionado
     * @param roleName nombre del perfil
     * @return un opcional con un perfil dentro
     */
    Optional<Perfil> findByName(PerfilName roleName);
}
