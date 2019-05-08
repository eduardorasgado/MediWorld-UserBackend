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

    /**
     * Metodo que consigue una lista de todos los pacientes disponibles de los repositorios
     * @return
     */
    public List<Paciente> getAll();

    /**
     * Metodo para encontrar un paciente dado su email
     * @return
     */
    public Paciente findByEmail(String email);

    /**
     * Metodo para encontrar un paciente dado su nombre de usuario
     * @param username
     * @return
     */
    public Paciente findByUsername(String username);

    /**
     * Metodo para comprobar si existe un paciente dado su nombre de usuario
     * @param username
     * @return
     */
    public Boolean existsByUsername(String username);

    /**
     * Metodo para comprobar si existe un paciente dado su email
     * @param email
     * @return
     */
    public Boolean existsByEmail(String email);

}
