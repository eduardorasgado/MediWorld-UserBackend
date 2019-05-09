package com.mediworld.mwuserapi.utils;

import com.mediworld.mwuserapi.security.PacienteDetailsService;
import io.jsonwebtoken.JwtHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * <h1>SecurityConfiguration</h1>
 * Clase de configuracion de la seguridad de la api
 *
 * @author Eduardo Rasgado Ruiz
 */
@Configuration
@EnableWebSecurity
// activa los niveles de seguridad
@EnableGlobalMethodSecurity(
        // activa la anotacion @Secured, con la cual se puede proteger un controller o service dado un rol
        securedEnabled = true,
        // activa la anotacion @RolesAlliwed
        jsr250Enabled = true,
        // activa empresiones complejas basadas en sintaxis de control de acceso:
        // @PreAuthorize/@PostAuthoize
        prePostEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    PacienteDetailsService pacienteDetailsService;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

}
