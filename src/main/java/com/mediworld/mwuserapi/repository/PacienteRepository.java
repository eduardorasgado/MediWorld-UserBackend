package com.mediworld.mwuserapi.repository;

import com.mediworld.mwuserapi.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <h1>PacienteRepository</h1>
 * Repositorio para la transaccion de datos con el modelo de Paciente
 *
 * @author Eduardo Rasgado Ruiz
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, String> {

    /**
     * Metodo para encontrar un paciente dado su email
     * @param email
     * @return Un opcional con paciente
     */
    Optional<Paciente> findByEmail(String email);

    /**
     * Metodo para encontrar un paciente dado su username
     * @param username
     * @return Un opcional con paciente
     */
    Optional<Paciente> findByUsername(String username);

    /**
     * Metodo que retorna true o false dada la existencia de un paciente por su username
     * @param username
     * @return booleano
     */
    Boolean existsByUsername(String username);

    /**
     * Metodo que retorna un booleano dada la existencia de un paciente por su email
     * @param email
     * @return booleano
     */
    Boolean existsByEmail(String email);
}
