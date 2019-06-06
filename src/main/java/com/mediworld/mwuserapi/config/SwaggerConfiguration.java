package com.mediworld.mwuserapi.config;

import com.google.common.collect.Lists;
import com.google.common.net.HttpHeaders;
import com.mediworld.mwuserapi.security.CurrentUsuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * <h1>SwaggerConfiguration</h1>
 *
 * Clase que representa la configuracion basica para poder trabajar con la documenta
 * cion de la api
 *
 * @author Eduardo Rasgado Ruiz
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Value("${admin.contact}")
    private String contact;
    @Value("${admin.name}")
    private String adminName;
    @Value("${admin.doc.title}")
    private String docTitle;
    @Value("${admin.doc.description}")
    private String docDescription;
    @Value("${admin.doc.version}")
    private String docVersion;
    @Value("${admin.doc.license}")
    private String docLicense;
    @Value("${admin.doc.licenseUrl}")
    private String docLicenseUrl;
    @Value("${admin.doc.termsService}")
    private String termsService;

    /**
     * Campos que referencian informacion referente al negocio dentro de la documentacion
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(docTitle)
                .description(docDescription)
                .license(docLicense)
                .licenseUrl(docLicenseUrl)
                .termsOfServiceUrl(termsService)
                .version(docVersion)
                .contact(new Contact(adminName,"", contact))
                .build();
    }

    /**
     * Define el uso de token bearer dentro de los headers de las peticiones internas
     * de la documentacion
     * @return
     */
    private ApiKey apiKey(){
        return new ApiKey("JWT", HttpHeaders.AUTHORIZATION, "header");
    }

    /**
     * Bean que define como va a manejar la documentacion en contexto con la aplicacion
     * asi como su seguridad y la informacion
     * @return
     */
    @Bean
    public Docket documentation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(CurrentUsuario.class)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Lists.newArrayList(apiKey()))
                .securityContexts(Lists.newArrayList(securityContext()))
                .apiInfo(apiInfo());
    }

    /**
     * Define un contexto de seguridad interno en la documentacion de swagger
     * @return
     */
    @Bean
    SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    /**
     * Helper que define el metodo de parseo de la autentificacion
     * @return
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("JWT", authorizationScopes));
    }
}
