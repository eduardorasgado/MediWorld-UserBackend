package com.mediworld.mwuserapi.services;

import com.mediworld.mwuserapi.model.Language;
import com.mediworld.mwuserapi.model.LanguageCode;
import com.mediworld.mwuserapi.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <h1>LanguageServiceImpl</h1>
 * Implementación de el servicio de lenguaje para operaciones con la db
 *
 * @author Eduardo Rasgado Ruiz
 */
@Service
public class LanguageServiceImpl implements ILanguageService{

    private LanguageRepository languageRepository;

    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }
    /**
     * Metodo que permite agregar un nuevo lenguaje a la db
     *
     * @param language
     * @return
     */
    @Override
    public Language create(Language language) {
        return this.languageRepository.save(language);
    }

    /**
     * Metodo que permite encontrar un lenguage basado en el codigo Locale
     *
     * @param code
     * @return
     */
    @Override
    public Language findByCode(LanguageCode code) {
        Optional<Language> languageContainer = this.languageRepository.findByCode(code);
        if(languageContainer.isPresent()) {
            return languageContainer.get();
        }
        return null;
    }

    /**
     * Metodo que permite encontrar un lenguaje basado en el nombre de este
     *
     * @param name
     * @return
     */
    @Override
    public Language findByName(String name) {
        Optional<Language> languageContainer = this.languageRepository.findByName(name);
        if(languageContainer.isPresent()) {
            return languageContainer.get();
        }
        return null;
    }
}
