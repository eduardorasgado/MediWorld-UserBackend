package com.mediworld.mwuserapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Estructura de respuesta de una solicitud de lenguaje por el frontend
 */
@Data
@AllArgsConstructor
public class LanguageResponse {
    private String id;
    private String language;
    private String code;
}
