package com.mediworld.mwuserapi.services;

import com.mediworld.mwuserapi.model.Language;
import com.mediworld.mwuserapi.model.LanguageCode;

import java.util.List;

/**
 * <h1>ILanguageService</h1>
 *
 * Definición de la interfaz para el servicio de los lenguajes de la aplicación
 *
 * @author Eduardo Rasgado Ruiz
 * @see Language
 */
public interface ILanguageService {

    /**
     * Metodo que permite agregar un nuevo lenguaje a la db
     * @param language
     * @return
     */
    Language create(Language language);

    /**
     * Metodo para encontrar un lenguaje existente dado su id
     * @param id
     * @return
     */
    Language findById(String id);

    /**
     * Metodo que permite encontrar un lenguage basado en el codigo Locale
     * @param code
     * @return
     */
    Language findByCode(LanguageCode code);

    /**
     * Metodo que permite encontrar un lenguaje basado en el nombre de este
     * @param name
     * @return
     */
    Language findByName(String name);

    /**
     * Metodo para obtener todos los lenguages sin usar un filtro
     * @return
     */
    List<Language> getAll();
}
