package com.mediworld.mwuserapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * <h1>EspecialidadMedica</h1>
 * Representa el modelo de especialidades medicas por idioma, esto
 * se representa en su campo language, un medico puede tener una especialidad medica
 *
 * @author Eduardo Rasgado Ruiz
 * @see Medico
 */
@Data
@AllArgsConstructor
@Entity
@Table(name="especialidadesmedicas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        })
})
public class EspecialidadMedica {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;
    @NotBlank
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_language")
    private Language language;

    @OneToMany(mappedBy = "especialidadMedica", fetch = FetchType.EAGER)
    private Set<Medico> medicos;
}
