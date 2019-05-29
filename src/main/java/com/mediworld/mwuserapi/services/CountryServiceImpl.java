package com.mediworld.mwuserapi.services;

import com.mediworld.mwuserapi.model.Country;
import com.mediworld.mwuserapi.model.Language;
import com.mediworld.mwuserapi.repository.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion del servicio del modelo Pais(Country)
 *
 * @author Eduardo Rasgado Ruiz
 * @see com.mediworld.mwuserapi.model.Country
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CountryServiceImpl implements ICountryService {

    private CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
    /**
     * Metodo para crear un pais y agregarlo a la db
     *
     * @param country
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Country create(Country country) {
        return this.countryRepository.save(country);
    }

    /**
     * Metodo para actualizar un pais existente
     *
     * @param country
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Country update(Country country) {
        return this.countryRepository.save(country);
    }

    /**
     * Metodo para eliminar un pais existente
     *
     * @param country
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean delete(Country country) {
        Optional<Country> countryContainer = this.countryRepository.findById(country.getId());
        if(countryContainer.isPresent()) {
            this.countryRepository.delete(countryContainer.get());
            return true;
        }
        return false;
    }

    /**
     * Metodo para obtener un pais dado su id
     *
     * @param id
     * @return
     */
    public Country findById(String id) {
        Optional<Country> countryContainer = this.countryRepository.findById(id);
        if(countryContainer.isPresent()){
            return countryContainer.get();
        }
        return null;
    }

    /**
     * Metodo para obtener un pais dado su nombre
     *
     * @param name
     * @return
     */
    public Country findByName(String name) {
        Optional<Country> countryContainer = this.countryRepository.findByName(name);
        if(countryContainer.isPresent()) {
            return countryContainer.get();
        }
        return null;
    }

    /**
     * Metodo para obtener todos los paises que hablan un lenguaje
     *
     * @param language
     * @return
     */
    public List<Country> findByLanguage(Language language) {
        return this.countryRepository.findByLanguage(language);
    }

    /**
     * Metodo para obtener todos los paises sin filtro alguno
     *
     * @return
     */
    public List<Country> getAll() {
        return this.countryRepository.findAll();
    }
}
