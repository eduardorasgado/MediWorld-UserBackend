package com.mediworld.mwuserapi.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * <h1>User</h1>
 * Clase que representa el modelo de usuario dentro de la base de datos
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
@Entity
@Table(name="paciente", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class Paciente extends DateAudit {
    @Id
    @GeneratedValue(generator = "uuid-system")
    @GenericGenerator(name = "uuid-system", strategy = "uuid2")
    private String id;

    @NotBlank
    @Size(max = 20, min = 3)
    private String username;

    @NotBlank
    @Size(max = 40, min = 6)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100, min = 6)
    private String password;

    @NotBlank
    @Size(max = 100, min = 2)
    private String nombre;

    @NotBlank
    @Size(max = 100, min = 2)
    private String apellidos;

    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genero genero;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "paciente_perfiles",
                joinColumns = @JoinColumn(name = "paciente_id"),
                inverseJoinColumns = @JoinColumn(name = "perfil_id"))
    private Set<Perfil> perfiles = new HashSet<>();
}
