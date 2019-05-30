package com.mediworld.mwuserapi.services;

import com.mediworld.mwuserapi.model.Country;
import com.mediworld.mwuserapi.model.EspecialidadMedica;
import com.mediworld.mwuserapi.model.Language;
import com.mediworld.mwuserapi.model.Medico;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MedicoServiceImpl implements IMedicoService{

    /**
     * Metodo para agregar un medico a la base de datos utilizando una entidad de medico
     *
     * @param medico
     * @return
     */
    @Override
    public Medico create(Medico medico) {
        return null;
    }

    /**
     * Metodo para actualizar un medico, dada una instancia de medico con los datos actualizados.
     *
     * @param medico
     * @return
     */
    @Override
    public Medico update(Medico medico) {
        return null;
    }

    /**
     * Metodo para eliminar un medico de la base de datos, dada una entidad de medico
     *
     * @param medico
     * @return
     */
    @Override
    public boolean delete(Medico medico) {
        return false;
    }

    /**
     * Buscar un medico acorde a su id, si este medico existe, va a ser devuelto
     *
     * @param id
     * @return
     */
    @Override
    public Medico findById(String id) {
        return null;
    }

    /**
     * Encontrar todos los medicos que existen, sin utilizar filtro alguno
     *
     * @return
     */
    @Override
    public List<Medico> findAll() {
        return null;
    }

    /**
     * Metodo para encontrar un medico dado su email, en caso de que el medico exista lo devuelve
     *
     * @param email
     * @return
     */
    @Override
    public Medico findByEmail(String email) {
        return null;
    }

    /**
     * Metodo para encontrar un medico dada su especialidad medica y su lenguaje de preferencia
     *
     * @param especialidadMedica
     * @param language
     * @return
     */
    @Override
    public List<Medico> findByEspecialidadMedicaAndPreferableLanguage(EspecialidadMedica especialidadMedica, Language language) {
        return null;
    }

    /**
     * Metodo para encontrar un medico dada su especialidad medica y su pais de residencia
     *
     * @param especialidadMedica
     * @param country
     * @return
     */
    @Override
    public List<Medico> findByEspecialidadMedicaAndPaisResidencia(EspecialidadMedica especialidadMedica, Country country) {
        return null;
    }

    /**
     * Meotodo para encontrar un medico dada su especialidad medica y su pais de nacimiento
     *
     * @param especialidadMedica
     * @param country
     * @return
     */
    @Override
    public List<Medico> findByEspecialidadMedicaAndPaisNacimiento(EspecialidadMedica especialidadMedica, Country country) {
        return null;
    }
}
