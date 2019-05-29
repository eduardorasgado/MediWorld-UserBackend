package com.mediworld.mwuserapi.controller;

import com.mediworld.mwuserapi.model.Language;
import com.mediworld.mwuserapi.payload.LanguageResponse;
import com.mediworld.mwuserapi.services.ILanguageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>LanguageController</h1>
 * Rest controller que permite la transaccion con el frontend de datos sobre los lenguajes
 * manejados por la app
 *
 * @author Eduardo Rasgado Ruiz
 * @see ILanguageService
 */
@RestController
@RequestMapping("/api/language")
public class LanguageController {

    private ILanguageService languageService;

    public LanguageController(ILanguageService languageService){
        this.languageService = languageService;
    }

    /**
     * Metodo para devolver todos los lenguajes dentro de la aplicacion hacia el frontend
     * @return Una lista de lenguajes
     */
    @GetMapping
    public ResponseEntity<List<LanguageResponse>> getAll(){
        List<LanguageResponse> languages = new ArrayList<>();
        for(Language l : this.languageService.getAll()){
            languages.add(new LanguageResponse(
                    l.getId(),
                    l.getName(),
                    l.getCode().name()
            ));
        }
        return ResponseEntity.ok(languages);
    }
}
