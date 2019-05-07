package com.mediworld.mwuserapi.repository;

import com.mediworld.mwuserapi.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <h1>PacienteRepository</h1>
 * Repositorio para la transaccion de datos con el modelo de Paciente
 *
 * @author Eduardo Rasgado Ruiz
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, String> {
    //
}
