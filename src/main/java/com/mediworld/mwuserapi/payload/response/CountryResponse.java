package com.mediworld.mwuserapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <h1>CountryResponse</h1>
 * Payload de respuesta para el ffrontend con la informacion de un pais
 *
 * @author Eduardo Rasgado Ruiz
 * @see com.mediworld.mwuserapi.controller.CountryController
 */
@Data
@AllArgsConstructor
public class CountryResponse {
    private String id;
    private String name;
    private LanguageResponse language;
}
