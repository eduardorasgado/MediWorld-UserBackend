package com.mediworld.mwuserapi.services;


import com.mediworld.mwuserapi.model.Paciente;

import java.util.List;

/**
 * <h1>PacienteService</h1>
 * Clase que permite traer las transacciones desde el repositorio del modelo de
 * paciente
 *
 * @author Eduardo Rasgado Ruiz
 */
public interface IPacienteService {

    /**
     * Metodo para traer un paciente dado su id
     * @return El paciente deseado
     */
    public Paciente getById(String id);

    /**
     * Metodo para crear un nuevo paciente mediante los datos recibidos en el
     * frontend
     * @param paciente los datos del paciente
     * @return El paciente que se ha creado
     */
    public Paciente create(Paciente paciente);

    /**
     * Metodo que elimina un paciente dada la entidad del paciente que ha sido
     * previamente buscado
     * @param paciente
     * @return
     */
    public boolean delete(Paciente paciente);

    /**
     * Metodo que actualiza un paciente que ya existe dentro de la base de datos
     * en caso de no existir no actualiza y regresa un null
     * @param paciente El paciente que va a ser actualizado con los datos que
     *                 van a ser actualizados
     * @return un paciente que fue actualizado o null
     */
    public Paciente update(Paciente paciente);

    public List<Paciente> getAll();

}
