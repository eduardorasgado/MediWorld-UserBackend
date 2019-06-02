package com.mediworld.mwuserapi.config;

import com.mediworld.mwuserapi.security.JwtAuthenticationEntryPoint;
import com.mediworld.mwuserapi.security.JwtAuthenticationFilter;
import com.mediworld.mwuserapi.security.MedicoDetailsService;
import com.mediworld.mwuserapi.security.PacienteDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
@Configuration
public class SecurityConfiguration {
    /**
     * Bean de filtro de autenticacion
     * @return
     */
    @Bean
    public static JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * Bean de encriptacion de contrasena
     * @return
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @Order(1)
    public static class PacienteSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Autowired
        protected JwtAuthenticationEntryPoint unauthorizedHandler;

        @Autowired
        private PacienteDetailsService pacienteDetailsService;

        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @Autowired
        private PasswordEncoder passwordEncoder;

        public PacienteSecurityConfiguration() {
            super();
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
                    .passwordEncoder(passwordEncoder);
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
                    .antMatcher("/api/paciente/**")
                    .authorizeRequests()
                    // permitir request anonimos de recursos
                    //los recursos que no aparecen aqui van a estar prohibidos para todos los usuarios
                    .antMatchers(
                            HttpMethod.GET,
                            "/",
                            "/v2/api-docs",           // swagger
                            "/webjars/**",            // swagger-ui webjars
                            "/swagger-resources/**",  // swagger-ui resources
                            "/configuration/**", // swagger configuration
                            "/favicon.ico",
                            "/**/*.png",
                            "/**/*.gif",
                            "/**/*.svg",
                            "/**/*.jpg",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js")
                    .permitAll()
                    .antMatchers("/api/paciente/auth/**",
                            "/api/language/**",
                            "/api/country/**")
                    .permitAll()
                    .antMatchers(
                            "/api/paciente/checkUsernameAvailability",
                            "/api/paciente/checkEmailAvailability")
                    .permitAll()
                    .antMatchers(HttpMethod.GET, "/api/paciente/**")
                    .permitAll()
                    .antMatchers("/api/paciente/preferableLanguage")
                    .permitAll()
                    .anyRequest()
                    .authenticated();

            // agregando nuestro filtro JWT de seguridad personalizado
            http
                    .addFilterBefore(jwtAuthenticationFilter,
                            UsernamePasswordAuthenticationFilter.class);

            // disable page caching
            http
                    .headers().cacheControl();
        }
    }

    @Configuration
    @Order(2)
    public static class MedicoSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        private JwtAuthenticationEntryPoint unauthorizedHandler;

        @Autowired
        private MedicoDetailsService medicoDetailsService;

        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @Autowired
        private PasswordEncoder passwordEncoder;

        public MedicoSecurityConfiguration(){
            super();
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
                    .userDetailsService(this.medicoDetailsService)
                    .passwordEncoder(passwordEncoder);
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
                    .antMatcher("/api/medico/**")
                    .authorizeRequests()
                    .antMatchers(
                            HttpMethod.GET,
                            "/",
                            "/v2/api-docs",           // swagger
                            "/webjars/**",            // swagger-ui webjars
                            "/swagger-resources/**",  // swagger-ui resources
                            "/configuration/**", // swagger configuration
                            "/favicon.ico",
                            "/**/*.png",
                            "/**/*.gif",
                            "/**/*.svg",
                            "/**/*.jpg",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js")
                    .permitAll()
                    // permitir request anonimos de recursos
                    //los recursos que no aparecen aqui van a estar prohibidos para todos los usuarios
                    .antMatchers("/api/medico/auth/**")
                    .permitAll()
                    //.antMatchers( "/api/medico/checkEmailAvailability")
                    //.permitAll()
                    .antMatchers(HttpMethod.GET, "/api/medico/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated();

            // agregando nuestro filtro JWT de seguridad personalizado
            http
                    .addFilterBefore(jwtAuthenticationFilter,
                            UsernamePasswordAuthenticationFilter.class);

            // disable page caching
            http
                    .headers().cacheControl();
        }
    }
}
