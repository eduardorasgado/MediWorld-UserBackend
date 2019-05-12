package com.mediworld.mwuserapi.services;

import com.mediworld.mwuserapi.model.Perfil;
import com.mediworld.mwuserapi.model.PerfilName;
import com.mediworld.mwuserapi.repository.PerfilRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * <h1>PerfilServiceImpl</h1>
 *
 * Implementacion de los metodos definidos en la interface {@link IPerfilService}
 * para realizar las transacciones con el repositorio
 *
 * @author Eduardo Rasgado Ruiz
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PerfilServiceImpl implements IPerfilService{

    private PerfilRepository perfilRepository;

    public PerfilServiceImpl(PerfilRepository perfilRepository){
        this.perfilRepository = perfilRepository;
    }

    /**
     * Metodo para guardar un nuevo perfil en la base de datos por medio de
     * un perfil recibido como parametro
     *
     * @param perfil perfil a ser creado
     * @return perfil ya creado
     */
    @Override
    @Transactional(readOnly = false)
    public Perfil create(Perfil perfil) {
        return perfilRepository.save(perfil);
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
