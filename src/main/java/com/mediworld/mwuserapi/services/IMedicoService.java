package com.mediworld.mwuserapi.services;

import com.mediworld.mwuserapi.model.Country;
import com.mediworld.mwuserapi.model.EspecialidadMedica;
import com.mediworld.mwuserapi.model.Language;
import com.mediworld.mwuserapi.model.Medico;

import java.util.List;

/**
 * <h1>IMedicoService</h1>
 * Definición del servicio que representa la transaccion con el modelo de Medico
 *
 * @author Eduardo Rasgado Ruiz
 * @see com.mediworld.mwuserapi.model.Medico
 */
public interface IMedicoService {

    /**
     * Metodo para agregar un medico a la base de datos utilizando una entidad de medico
     * @param medico
     * @return
     */
    Medico create(Medico medico);

    /**
     * Metodo para actualizar un medico, dada una instancia de medico con los datos actualizados.
     * @param medico
     * @return
     */
    Medico update(Medico medico);

    /**
     * Metodo para eliminar un medico de la base de datos, dada una entidad de medico
     * @param medico
     * @return
     */
    boolean delete(Medico medico);

    /**
     * Buscar un medico acorde a su id, si este medico existe, va a ser devuelto
     * @param id
     * @return
     */
    Medico findById(String id);

    /**
     * Encontrar todos los medicos que existen, sin utilizar filtro alguno
     * @return
     */
    List<Medico> findAll();

    /**
     * Metodo para encontrar un medico dado su email, en caso de que el medico exista lo devuelve
     * @param email
     * @return
     */
    Medico findByEmail(String email);

    /**
     * Metodo para encontrar un medico dada su especialidad medica y su lenguaje de preferencia
     * @param especialidadMedica
     * @param language
     * @return
     */
    List<Medico> findByEspecialidadMedicaAndPreferableLanguage(
            EspecialidadMedica especialidadMedica,
            Language language
    );

    /**
     * Metodo para encontrar un medico dada su especialidad medica y su pais de residencia
     * @param especialidadMedica
     * @param country
     * @return
     */
    List<Medico> findByEspecialidadMedicaAndPaisResidencia(
            EspecialidadMedica especialidadMedica,
            Country country
    );

    /**
     * Meotodo para encontrar un medico dada su especialidad medica y su pais de nacimiento
     * @param especialidadMedica
     * @param country
     * @return
     */
    List<Medico> findByEspecialidadMedicaAndPaisNacimiento(
            EspecialidadMedica especialidadMedica,
            Country country
    );

    /**
     * Metodo que devuelve la disponibilidad de un correo determinado dentro de
     * la base de datos de los usuarios de tipo medico
     * @param email
     * @return
     */
    boolean existsByEmail(String email);
}
