package com.mediworld.mwuserapi.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PreferableLanguageRequest {
    @NotBlank
    private String code;
}
