package com.mediworld.mwuserapi.repository;

import com.mediworld.mwuserapi.model.Language;
import com.mediworld.mwuserapi.model.LanguageCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio que permite agregar idiomas a la base de datos
 */
@Repository
public interface LanguageRepository extends JpaRepository<Language, String> {

    /**
     * Metodo para buscar un lenguaje dato su codigo
     * @param code
     * @return
     */
    Optional<Language> findByCode(LanguageCode code);

    /**
     * Metodo para buscar un lenguaje dato su nombre
     * @param name
     * @return
     */
    Optional<Language> findByName(String name);
}
