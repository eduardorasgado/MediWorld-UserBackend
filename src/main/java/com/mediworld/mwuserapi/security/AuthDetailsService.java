package com.mediworld.mwuserapi.security;

import com.mediworld.mwuserapi.model.Medico;
import com.mediworld.mwuserapi.model.Paciente;
import com.mediworld.mwuserapi.repository.MedicoRepository;
import com.mediworld.mwuserapi.repository.PacienteRepository;
import com.mediworld.mwuserapi.util.AppConstants;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Servicio de detalle de usuario especificamente usado en el logueo de un
 * usuario ya sea medico o paciete
 * @author Eduardo Rasgado Ruiz
 * @see com.mediworld.mwuserapi.config.SecurityConfiguration
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AuthDetailsService implements UserDetailsService {
    private MedicoRepository medicoRepository;
    private PacienteRepository pacienteRepository;

    public AuthDetailsService(
            MedicoRepository medicoRepository,
            PacienteRepository pacienteRepository
    ) {
        this.medicoRepository = medicoRepository;
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
        boolean matchesEmailRegex = username.matches(AppConstants.EMAIL_REGEX);
        if(matchesEmailRegex){
            // entonces es un medico
            Optional<Medico> medico = this.medicoRepository.findByEmail(username);
            if(medico.isPresent()){
                return MedicoPrincipal.create(medico.get());
            }
        } else {
            // entonces es un paciente
            Optional<Paciente> paciente = this.pacienteRepository.findByUsername(username);
            if(paciente.isPresent()){
                return PacientePrincipal.create(paciente.get());
            }
        }
        throw new UsernameNotFoundException("Usuario no encontrado con nombre de usuario");
    }
}
