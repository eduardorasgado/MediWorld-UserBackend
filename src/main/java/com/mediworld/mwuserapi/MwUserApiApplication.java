package com.mediworld.mwuserapi;

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

    /**
     * Metodo que define la zona horaria que manejara la api en general
     */
    @PostConstruct
    void init() {
        // zona horaria de la ciudad de Mexico ->Central Day Lightime
        TimeZone.setDefault(TimeZone.getTimeZone("CDT"));
    }

    /**
     * metodo que manda a llamar toda la aplicacion o entry point
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(MwUserApiApplication.class, args);
    }

}
