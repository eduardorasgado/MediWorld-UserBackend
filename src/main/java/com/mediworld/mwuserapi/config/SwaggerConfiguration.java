package com.mediworld.mwuserapi.config;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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

    @Value("${app.jwtSecretKey}")
    private String apiKeyString;

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

    @Bean
    SecurityScheme apiKey(){
        return new ApiKey("accessToken", "Authorization", "header");
    }

    @Bean
    public Docket documentation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Lists.newArrayList(apiKey()))
                .apiInfo(apiInfo());
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
                "test-app-client-id",
                "test-app-client-secret",
                "test-app-realm",
                "mediworld",
                "Bearer "+apiKeyString,
                ApiKeyVehicle.HEADER,
                "Authorization",
                ","
        );
    }
}
