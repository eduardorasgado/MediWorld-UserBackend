package com.mediworld.mwuserapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Clase que permite el acceso de un cliente frontend mediante requests de tipo
 * origin request.
 *
 * @author Eduardo Rasgado Ruiz
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final long MAX_AGE_SECS = 3600;

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("HEAD",
                        "OPTIONS",
                        "GET",
                        "POST",
                        "PUT",
                        "PATCH",
                        "DELETE")
                .maxAge(this.MAX_AGE_SECS);
    }
}
