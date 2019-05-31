package com.mediworld.mwuserapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * <h1>Medico</h1>
 * Clase que representa la entidad de medico para ofrencer el servicio web de este tipo de usuario
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "medico", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class Medico extends Usuario{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;
    @Size(max = 12, min = 6)
    @Nullable
    private String telefonoCelularOpcionUno;
    @Size(max = 12, min = 6)
    @Nullable
    private String telefonoCelularOpcionDos;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_especialidadMedica")
    private EspecialidadMedica especialidadMedica;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_preferableLanguage")
    private Language preferableLanguage;
}
