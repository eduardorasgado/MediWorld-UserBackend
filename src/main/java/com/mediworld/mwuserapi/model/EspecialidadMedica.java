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

    /**
     * Una especialidad medica debe de enviarse con el lenguaje en el que se encuentre
     * escrito
     */
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_language")
    private Language language;

    /**
     * No necesito traerme a todos los medicos cuando pida una especialidad medica
     * Por ejemplo: en medico controller mapeamos un medico con una especialidad medica
     * dada del front, esta se guarda y cada que se pida desde la entidad de un medico
     * no necesita llegar con la lista de todos los medicos. Esto volvería tonto
     * un proceso como el de traerse la lista de todos los medicos de un pais.
     */
    @OneToMany(mappedBy = "especialidadMedica", fetch = FetchType.LAZY)
    private Set<Medico> medicos;
}
