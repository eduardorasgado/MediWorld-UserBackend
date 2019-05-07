package com.mediworld.mwuserapi.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

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
    @Size(max = 15, min = 3)
    private String username;
    @NotBlank
    @Size(max = 40, min = 6)
    @Email
    private String email;
    @NotBlank
    @Size(max = 100, min = 6)
    private String password;
    @NotBlank
    private String nombre;
    private String apellidos;

    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    private boolean genero;

    //@Temporal(TemporalType.TIMESTAMP)
    //private Date createdAt;
}
