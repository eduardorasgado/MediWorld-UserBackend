package com.mediworld.mwuserapi.services;

import com.mediworld.mwuserapi.model.Perfil;
import com.mediworld.mwuserapi.model.PerfilName;
import com.mediworld.mwuserapi.repository.PerfilRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerfilServiceImpl implements IPerfilService{

    private PerfilRepository perfilRepository;

    public PerfilServiceImpl(PerfilRepository perfilRepository){
        this.perfilRepository = perfilRepository;
    }
    /**
     * Metodo que devuelve un perfil dado un nombre de perfil
     *
     * @param perfilName nombre de perfil
     * @return un perfil
     */
    @Override
    public Perfil findByName(PerfilName perfilName) {
        Optional<Perfil> perfilContainer = this.perfilRepository.findByName(perfilName);
        if(perfilContainer.isPresent()){
            return perfilContainer.get();
        }
        return null;
    }
}
