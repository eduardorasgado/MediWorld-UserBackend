package com.mediworld.mwuserapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
@SpringBootApplication
public class MwUserApiApplication {

    /**
     * Metodo que define la zona horaria que manejara la api en general
     */
    @PostConstruct
    void started() {
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
