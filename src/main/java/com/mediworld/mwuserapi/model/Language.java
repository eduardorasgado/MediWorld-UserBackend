package com.mediworld.mwuserapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * <h1>Language</h1>
 * Clase que permite las traducciones segun cada país que hay en el mundo
 *
 * @author Eduardo Rasgado Ruiz
 */
@AllArgsConstructor
@Data
@Entity
@Table(name="languages")
public class Language {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String id;
    @NotBlank
    private String LanguageName;
    @Enumerated(EnumType.STRING)
    @Column(length = 3)
    @NaturalId
    private LanguageCode LanguageCode;
}
