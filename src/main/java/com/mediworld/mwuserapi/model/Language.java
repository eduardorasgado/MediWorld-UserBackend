package com.mediworld.mwuserapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * <h1>Language</h1>
 * Clase que permite las traducciones segun cada país que hay en el mundo
 *
 * @author Eduardo Rasgado Ruiz
 * @see EspecialidadMedica
 * @see Country
 * @see Paciente
 * @see Medico
 */
@AllArgsConstructor
@Data
@Entity
@Table(name="languages", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        }),
        @UniqueConstraint(columnNames = {
                "code"
        })
})
public class Language {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String id;
    @NotBlank
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(length = 3)
    @NaturalId
    private LanguageCode code;

    @OneToMany(mappedBy = "language", fetch = FetchType.LAZY)
    private Set<EspecialidadMedica> especialidadMedicas;

    @OneToMany(mappedBy = "language", fetch = FetchType.EAGER)
    private Set<Country> countries;

    @OneToMany(mappedBy = "preferableLanguage", fetch = FetchType.EAGER)
    private Set<Paciente> pacientes;

    @OneToMany(mappedBy = "preferableLanguage", fetch = FetchType.EAGER)
    private Set<Medico> medicos;
}
