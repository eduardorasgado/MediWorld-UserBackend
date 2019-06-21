package com.mediworld.mwuserapi.controller;

import com.mediworld.mwuserapi.model.Country;
import com.mediworld.mwuserapi.payload.response.CountryResponse;
import com.mediworld.mwuserapi.payload.response.LanguageResponse;
import com.mediworld.mwuserapi.services.ICountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>CountryController</h1>
 * Controlador rest para realizar transacciones con el modelo de Paises
 *
 * @author Eduardo Rasgado Ruiz
 * @see com.mediworld.mwuserapi.services.ICountryService
 */
@RestController
@RequestMapping("api/country")
public class CountryController {

    private ICountryService countryService;

    public CountryController(ICountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * Metodo para obtener todos los paises que se manejan en la aplicacion junto a los
     * lenguajes
     * @return Lista de paises
     */
    @GetMapping
    public ResponseEntity<List<CountryResponse>> getAll() {
        List<CountryResponse> countries = new ArrayList<>();

        for(Country c : this.countryService.getAll()){
            countries.add(new CountryResponse(
                    c.getId(),
                    c.getName(),
                    new LanguageResponse(
                            c.getLanguage().getId(),
                            c.getLanguage().getName(),
                            c.getLanguage().getCode().name()
                    )
            ));
        }

        return ResponseEntity.ok(countries);
    }
}
