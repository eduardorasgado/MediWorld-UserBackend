package com.mediworld.mwuserapi.security;

import com.mediworld.mwuserapi.model.Paciente;
import com.mediworld.mwuserapi.services.IPacienteService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <h1>PacienteDetailsService</h1>
 * Interfaz que permite cargar un paciente en base a su username
 *
 * @author Eduardo Rasgado Ruiz
 */
public class PacienteDetailsService implements UserDetailsService {

    private IPacienteService pacienteService;

    public PacienteDetailsService(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
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
        //Paciente paciente = this.pacienteService.
        return null;
    }
}

