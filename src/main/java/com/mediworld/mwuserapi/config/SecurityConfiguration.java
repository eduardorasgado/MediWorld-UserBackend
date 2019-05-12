package com.mediworld.mwuserapi.config;

import com.mediworld.mwuserapi.security.JwtAuthenticationEntryPoint;
import com.mediworld.mwuserapi.security.JwtAuthenticationFilter;
import com.mediworld.mwuserapi.security.PacienteDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    @Autowired
    JwtAuthenticationEntryPoint unauthorizedHandler;

    /**
     * Bean de filtro de autenticacion
     * @return
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * Bean de encriptacion de contrasena
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * el autenticador manager de la clase padre como bean de manager de autenticacion
     * @return
     * @throws Exception
     */
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Configutracion de como se administra la autenticacion de los pacientes
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
                throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.pacienteDetailsService)
                .passwordEncoder(this.passwordEncoder());
    }

    /**
     * Metodo de configuracion principal de spring security para proteccion de
     * la api
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(this.unauthorizedHandler)
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                .permitAll()
                .antMatchers("/api/auth/**")
                    .permitAll()
                .antMatchers(HttpMethod.GET, "/api/paciente/**")
                    .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();

        // agregando nuestro filtro JWT de seguridad personalizado
        http
                .addFilterBefore(jwtAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class);
    }
}
