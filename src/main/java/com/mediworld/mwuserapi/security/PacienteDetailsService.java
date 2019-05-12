package com.mediworld.mwuserapi.security;

import com.mediworld.mwuserapi.exception.ResourceNotFoundException;
import com.mediworld.mwuserapi.model.Paciente;
import com.mediworld.mwuserapi.repository.PacienteRepository;
import com.mediworld.mwuserapi.services.IPacienteService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * <h1>PacienteDetailsService</h1>
 * Interfaz que permite cargar un paciente en base a su username
 *
 * @author Eduardo Rasgado Ruiz
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PacienteDetailsService implements UserDetailsService {

    private PacienteRepository pacienteRepository;

    public PacienteDetailsService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }
    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Paciente> paciente = this.pacienteRepository.findByUsername(username);
        if(paciente.isPresent()){
            return PacientePrincipal.create(paciente.get());
        }
        throw new UsernameNotFoundException("Usuario no encontrado con nombre de usuario");
    }

    /**
     * encuentra un ususario dado su id y lo devuelve con sus authorities
     * @param id
     * @return
     */
    public UserDetails loadUserById(String id) {
        Optional<Paciente> paciente = this.pacienteRepository.findById(id);
        if(paciente.isPresent()) {
            return PacientePrincipal.create(paciente.get());
        }
        throw new ResourceNotFoundException("Paciente", "id", id);
    }
}

