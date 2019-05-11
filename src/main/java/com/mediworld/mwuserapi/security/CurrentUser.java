package com.mediworld.mwuserapi.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/**
 * <h1>CurrentUser</h1>
 * Clase para acceder al paciente actualmente autenticado en el controlador
 * Implementacion de una anotacion de interfaz: metanotacion
 *
 * @author Eduardo Rasgado Ruiz
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {
}
