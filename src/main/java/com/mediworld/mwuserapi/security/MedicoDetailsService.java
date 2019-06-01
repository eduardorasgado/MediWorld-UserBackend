package com.mediworld.mwuserapi.security;

import com.mediworld.mwuserapi.exception.ResourceNotFoundException;
import com.mediworld.mwuserapi.model.Medico;
import com.mediworld.mwuserapi.repository.MedicoRepository;
import com.mediworld.mwuserapi.services.IMedicoService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MedicoDetailsService implements UserDetailsService {

    private MedicoRepository medicoRepository;

    public MedicoDetailsService(MedicoRepository medicoRepository){
        this.medicoRepository = medicoRepository;
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
        Optional<Medico> medico = this.medicoRepository.findByEmail(username);
        if(medico.isPresent()){
            return MedicoPrincipal.create(medico.get());
        }

        throw new UsernameNotFoundException("Usuario tipo medico no encotrado usando el email");
    }

    public UserDetails loadUserById(String id) {
        Optional<Medico> medico = this.medicoRepository.findById(id);
        if(medico.isPresent()){
            return MedicoPrincipal.create(medico.get());
        }

        throw  new ResourceNotFoundException("Medico", "id", id);
    }
}
