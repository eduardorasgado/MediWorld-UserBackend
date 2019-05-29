package com.mediworld.mwuserapi.repository;

import com.mediworld.mwuserapi.model.Country;
import com.mediworld.mwuserapi.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *<h1>CountryRepository</h1>
 * Repositorio del modelo de paises para realizar las operaciones
 * individuales para la db
 *
 * @author Eduardo Rasgado Ruiz
 * @see Country
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
    /**
     * Metodo para obtener un pais dado su nombre
     * @param name
     * @return
     */
    public Optional<Country> findByName(String name);

    /**
     * Metodo para obtener un pais dado un lenguaje
     * @param language
     * @return
     */
    public List<Country> findByLanguage(Language language);
}
