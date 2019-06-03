package com.mediworld.mwuserapi.services;

import com.mediworld.mwuserapi.model.Country;
import com.mediworld.mwuserapi.model.EspecialidadMedica;
import com.mediworld.mwuserapi.model.Language;
import com.mediworld.mwuserapi.model.Medico;
import com.mediworld.mwuserapi.repository.MedicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MedicoServiceImpl implements IMedicoService{

    private MedicoRepository medicoRepository;

    public MedicoServiceImpl(MedicoRepository medicoRepository){
        this.medicoRepository = medicoRepository;
    }

    /**
     * Metodo para agregar un medico a la base de datos utilizando una entidad de medico
     *
     * @param medico
     * @return
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Medico create(Medico medico) {
        return this.medicoRepository.save(medico);
    }

    /**
     * Metodo para actualizar un medico, dada una instancia de medico con los datos actualizados.
     *
     * @param medico
     * @return
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Medico update(Medico medico) {
        return this.medicoRepository.save(medico);
    }

    /**
     * Metodo para eliminar un medico de la base de datos, dada una entidad de medico
     *
     * @param medico
     * @return
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean delete(Medico medico) {
        Optional<Medico> medicoContainer = this.medicoRepository.findById(medico.getId());
        if(medicoContainer.isPresent()){
            this.medicoRepository.delete(medicoContainer.get());
            return true;
        }
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
        Optional<Medico> medico = this.medicoRepository.findById(id);
        if(medico.isPresent()){
            return medico.get();
        }
        return null;
    }

    /**
     * Encontrar todos los medicos que existen, sin utilizar filtro alguno
     *
     * @return
     */
    @Override
    public List<Medico> findAll() {
        return this.medicoRepository.findAll();
    }

    /**
     * Metodo para encontrar un medico dado su email, en caso de que el medico exista lo devuelve
     *
     * @param email
     * @return
     */
    @Override
    public Medico findByEmail(String email) {
        Optional<Medico> medico = this.medicoRepository.findByEmail(email);

        if(medico.isPresent()){
            return medico.get();
        }
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
    public List<Medico> findByEspecialidadMedicaAndPreferableLanguage(
            EspecialidadMedica especialidadMedica, Language language) {
        return this.medicoRepository.findByEspecialidadMedicaAndPreferableLanguage(
                especialidadMedica,
                language
        );
    }

    /**
     * Metodo para encontrar un medico dada su especialidad medica y su pais de residencia
     *
     * @param especialidadMedica
     * @param country
     * @return
     */
    @Override
    public List<Medico> findByEspecialidadMedicaAndPaisResidencia(
            EspecialidadMedica especialidadMedica, Country country) {
        return this.medicoRepository.findByEspecialidadMedicaAndPaisResidencia(
                especialidadMedica,
                country
        );
    }

    /**
     * Meotodo para encontrar un medico dada su especialidad medica y su pais de nacimiento
     *
     * @param especialidadMedica
     * @param country
     * @return
     */
    @Override
    public List<Medico> findByEspecialidadMedicaAndPaisNacimiento(
            EspecialidadMedica especialidadMedica, Country country) {
        return this.medicoRepository.findByEspecialidadMedicaAndPaisNacimiento(
                especialidadMedica,
                country
        );
    }

    /**
     * Metodo que devuelve la disponibilidad de un correo determinado dentro de
     * la base de datos de los usuarios de tipo medico
     *
     * @param email
     * @return
     */
    @Override
    public boolean existsByEmail(String email) {
        return this.medicoRepository.existsByEmail(email);
    }
}
