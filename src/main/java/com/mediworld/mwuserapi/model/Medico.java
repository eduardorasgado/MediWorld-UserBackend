package com.mediworld.mwuserapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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
}
