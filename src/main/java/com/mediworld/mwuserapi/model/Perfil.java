package com.mediworld.mwuserapi.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/**
 * <h1>Perfil</h1>
 * Clase que representa los roles del paciente dentro de la aplicacion
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
@Entity
@Table(name = "perfiles")
public class Perfil {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;
    @Enumerated(EnumType.STRING)
    @Column(length = 60)
    @NaturalId
    private  PerfilName name;

}
