package com.mediworld.mwuserapi;

import com.mediworld.mwuserapi.model.Perfil;
import com.mediworld.mwuserapi.model.PerfilName;
import com.mediworld.mwuserapi.services.IPerfilService;
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
    /**
     * Metodo que define la zona horaria que manejara la api en general, asi como inicializa
     * los dos primeros perfiles en la base de datos
     */
    @PostConstruct
    void init() {
        // zona horaria de la ciudad de Mexico ->Central Day Lightime
        TimeZone.setDefault(TimeZone.getTimeZone("CDT"));

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
    }

    /**
     * metodo que manda a llamar toda la aplicacion o entry point
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(MwUserApiApplication.class, args);
    }

}
