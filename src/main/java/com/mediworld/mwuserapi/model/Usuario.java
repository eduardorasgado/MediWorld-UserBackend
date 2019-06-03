package com.mediworld.mwuserapi.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * <h1>Usuario</h1>
 * Clase padre que permite manipular al usuario paciente/medico sin necesidad de reproducir doble
 * codigo para la verificacion de sus token de acceso a logueo
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
@MappedSuperclass
public class Usuario extends DateAudit {

    @NotBlank
    @Size(max = 50, min = 6)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100, min = 6)
    private String password;

    @NotBlank
    @Size(max = 50, min = 2)
    private String nombre;

    @NotBlank
    @Size(max = 70, min = 2)
    private String apellidos;

    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genero genero;


    @Enumerated(EnumType.STRING)
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paisResidencia")
    private Country paisResidencia;

    @Enumerated(EnumType.STRING)
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paisNacimiento")
    private Country paisNacimiento;

}
