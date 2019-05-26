package com.mediworld.mwuserapi.repository;

import com.mediworld.mwuserapi.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <h1>MedicoRepository</h1>
 * Repositorio que realiza las transacciones individuales a la base de datos con respecto al
 * modelo de {@link Medico}, esta interfaz extiende de {@link JpaRepository}
 *
 * @author Eduardo Rasgado Ruiz
 */
@Repository
public interface MedicoRepository extends JpaRepository<Medico, String> {

    /**
     * Metodo para encontrar un medico dado su email
     * @param email
     * @return
     */
    Optional<Medico> findByEmail(String email);

    /**
     * Metodo para encontrar todos los medicos dada una especialidad medica y el lenguage en el
     * que este la especialidad medica
     * @param especialidadMedica
     * @return
     */
    List<Medico> findByEspecialidadMedicaAndPreferableLanguage(
            EspecialidadMedica especialidadMedica, Language language);

    /**
     * Metodo para encontrar todos los medicos dada una especialidad y dado un pais de residencia
     * @return
     */
    List<Medico> findByEspecialidadMedicaAndPaisResidencia(
            EspecialidadMedica especialidadMedica, Country country
    );

    /**
     * Metodo para encontrar todos los medicos dada una especialidad y dado un pais de nacimiento
     * @return
     */
    List<Medico> findByEspecialidadMedicaAndPaisNacimiento(
            EspecialidadMedica especialidadMedica, Country country
    );

}
