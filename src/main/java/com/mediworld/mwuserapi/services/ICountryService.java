package com.mediworld.mwuserapi.services;

import com.mediworld.mwuserapi.model.Country;
import com.mediworld.mwuserapi.model.Language;

import java.util.List;

/**
 * <h1>ICountryService</h1>
 * Interface que define los metodos para el acceso a los datos de paises
 * usando los repositorios
 *
 * @author Eduardo Rasgado Ruiz
 * @see com.mediworld.mwuserapi.repository.CountryRepository
 * @see com.mediworld.mwuserapi.model.Country
 */
public interface ICountryService {
    /**
     * Metodo para crear un pais y agregarlo a la db
     * @return
     */
    public Country create(Country country);

    /**
     * Metodo para actualizar un pais existente
     * @return
     */
    public Country update(Country country);

    /**
     * Metodo para eliminar un pais existente
     */
    public boolean delete(Country country);

    /**
     * Metodo para obtener un pais dado su id
     * @param id
     * @return
     */
    public Country findById(String id);

    /**
     * Metodo para obtener un pais dado su nombre
     * @param name
     * @return
     */
    public Country findByName(String name);

    /**
     * Metodo para obtener todos los paises que hablan un lenguaje
     * @param language
     * @return
     */
    public List<Country> findByLanguage(Language language);

    /**
     * Metodo para obtener todos los paises sin filtro alguno
     * @return
     */
    public List<Country> getAll();
}
