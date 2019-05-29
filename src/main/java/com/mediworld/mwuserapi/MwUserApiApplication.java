package com.mediworld.mwuserapi;

import com.mediworld.mwuserapi.model.*;
import com.mediworld.mwuserapi.services.ICountryService;
import com.mediworld.mwuserapi.services.ILanguageService;
import com.mediworld.mwuserapi.services.IPerfilService;
import com.mediworld.mwuserapi.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * <h1>MwUserApiApplication</h1>
 * Proyecto principal de la API de Usuarios para el proyecto maestro
 * MediWorld.
 *
 * @author Eduardo Rasgado Ruiz
 * @since may/2019
 * @version 1.0
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EntityScan(basePackageClasses = {
        MwUserApiApplication.class,
        // JPA 2.1 converters to turn JSR-310 types into legacy Date s.
        Jsr310JpaConverters.class
})
public class MwUserApiApplication {

    @Autowired
    private IPerfilService perfilService;

    @Autowired
    private ILanguageService languageService;

    @Autowired
    private ICountryService countryService;
    /**
     * Metodo que define la zona horaria que manejara la api en general, asi como inicializa
     * los dos primeros perfiles en la base de datos
     */
    @PostConstruct
    void init() {
        // zona horaria de la ciudad de Mexico ->Central Day Lightime
        TimeZone.setDefault(TimeZone.getTimeZone("CDT"));

        // NOTA: ESTAS OPERACIONES NO VAN A PRODUCCIÓN, ESTAS OPERACIONES SON HECHAS DESDE
        // LA APLICACION FRONTEND ADMINISTRADORA
        this.addProfiles();
        this.addLanguages();
        this.addCountries();
    }

    /**
     * metodo que manda a llamar toda la aplicacion o entry point
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(MwUserApiApplication.class, args);
    }

    public void addProfiles(){
        //Agregando dos perfiles iniciales a la base de datos
        Perfil paciente = new Perfil();
        Perfil pacienteActive = new Perfil();
        Perfil medico = new Perfil();
        Perfil medicoActive = new Perfil();

        if(perfilService.findByName(PerfilName.PACIENTE) == null) {
            paciente.setName(PerfilName.PACIENTE);
            perfilService.create(paciente);
        }
        if(perfilService.findByName(PerfilName.PACIENTE_ACTIVE) == null){
            pacienteActive.setName(PerfilName.PACIENTE_ACTIVE);
            perfilService.create(pacienteActive);
        }

        if(perfilService.findByName(PerfilName.MEDICO) == null){
            medico.setName(PerfilName.MEDICO);
            perfilService.create(medico);
        }
        if(perfilService.findByName(PerfilName.MEDICO_ACTIVE) == null){
            medicoActive.setName(PerfilName.MEDICO_ACTIVE);
            perfilService.create(medicoActive);
        }
        paciente = null;
        pacienteActive = null;
        medico = null;
        medicoActive = null;
    }

    public void addLanguages() {
        System.out.println("[LANGUAGES CREATION: ARE NEEDED TO CREATE COUNTRIES]");
        Language spanish = new Language();
        Language english = new Language();
        Language french = new Language();
        Language german = new Language();

        if(this.languageService.findByCode(LanguageCode.es) == null) {
            spanish.setCode(LanguageCode.es);
            spanish.setName(AppConstants.SPANISH);
            this.languageService.create(spanish);
        }

        if(this.languageService.findByCode(LanguageCode.en) == null) {
            english.setCode(LanguageCode.en);
            english.setName(AppConstants.ENGLISH);
            this.languageService.create(english);
        }

        if(this.languageService.findByCode(LanguageCode.fr) == null) {
            french.setCode(LanguageCode.fr);
            french.setName(AppConstants.FRENCH);
            this.languageService.create(french);
        }

        if(this.languageService.findByCode(LanguageCode.de) == null) {
            german.setCode(LanguageCode.de);
            german.setName(AppConstants.GERMAN);
            this.languageService.create(german);
        }

        spanish = null;
        english = null;
        french = null;
        german = null;
    }

    public void addCountries() {
        System.out.println("[COUNTRIES CREATION: ARE NEEDED TO CREATE PACIENTE AND MEDICO, REQUIRES LANGUAGE]");
        Country mexico = new Country();
        Country usa = new Country();
        Country france = new Country();
        Country chile = new Country();
        Country argentina = new Country();

        if(this.countryService.findByName("Mexico") == null){
            mexico.setName("Mexico");
            mexico.setLanguage(this.languageService.findByCode(LanguageCode.es));
            this.countryService.create(mexico);
        }
        if(this.countryService.findByName("Estados Unidos") == null){
            usa.setName("Estados Unidos");
            usa.setLanguage(this.languageService.findByCode(LanguageCode.en));
            this.countryService.create(usa);
        }
        if(this.countryService.findByName("France") == null){
            france.setName("France");
            france.setLanguage(this.languageService.findByCode(LanguageCode.fr));
            this.countryService.create(france);
        }
        if(this.countryService.findByName("Chile") == null){
            chile.setName("Chile");
            chile.setLanguage(this.languageService.findByCode(LanguageCode.es));
            this.countryService.create(chile);
        }
        if(this.countryService.findByName("Argentina") == null){
            argentina.setName("Argentina");
            argentina.setLanguage(this.languageService.findByCode(LanguageCode.es));
            this.countryService.create(argentina);
        }

        // verificar si existen los paises en la db previo a agregarlos
        mexico = null;
        usa = null;
        france = null;
        chile = null;
        argentina = null;
    }

}
