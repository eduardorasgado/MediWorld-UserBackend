package com.mediworld.mwuserapi.services;

import com.mediworld.mwuserapi.model.Paciente;
import com.mediworld.mwuserapi.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <h1>PacienteServiceImpl</h1>
 * Clase que implementa los metodos de la interfaz {@link IPacienteService}
 * Para llevar a cabo la interaccion con los repositorios de la clase
 * {@link Paciente}
 *
 * @author Eduardo Rasgado Ruiz
 */
@Service
public class PacienteServiceImpl implements IPacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository){
        this.pacienteRepository = pacienteRepository;
    }

    /**
     * Metodo para traer un paciente dado su id
     *
     * @return El paciente deseado
     */
    @Override
    public Paciente getById(String id) {
        Optional<Paciente> pacienteContainer = this.pacienteRepository.findById(id);
        if(pacienteContainer.isPresent()){
            return pacienteContainer.get();
        }
        return null;
    }

    /**
     * Metodo para crear un nuevo paciente mediante los datos recibidos en el
     * frontend
     *
     * @param paciente los datos del paciente
     * @return El paciente que se ha creado
     */
    @Override
    public Paciente create(Paciente paciente) {
        return this.pacienteRepository.save(paciente);
    }

    /**
     * Metodo que elimina un paciente dada la entidad del paciente que ha sido
     * previamente buscado
     *
     * @param paciente
     * @return
     */
    @Override
    public boolean delete(Paciente paciente) {
        Optional<Paciente> pacienteToDelete = this.pacienteRepository.findById(paciente.getId());
        if(pacienteToDelete.isPresent()) {
            this.pacienteRepository.delete(paciente);
            return true;
        }
        return false;
    }

    /**
     * Metodo que actualiza un paciente que ya existe dentro de la base de datos
     * en caso de no existir no actualiza y regresa un null
     *
     * @param paciente El paciente que va a ser actualizado con los datos que
     *                 van a ser actualizados
     * @return un paciente que fue actualizado o null
     */
    @Override
    public Paciente update(Paciente paciente) {
        return this.pacienteRepository.save(paciente);
    }
}
