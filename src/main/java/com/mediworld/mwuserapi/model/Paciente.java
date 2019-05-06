package com.mediworld.mwuserapi.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * <h1>User</h1>
 * Clase que representa el modelo de usuario dentro de la base de datos
 */
@Data
@Entity
@Table(name="paciente")
public class Paciente {
    @Id
    @GeneratedValue(generator = "uuid-system")
    @GenericGenerator(name = "uuid-system", strategy = "uuid2")
    private String id;
    private String username;
    private String email;
    private String password;
    private String nombre;
    private String apellidos;
    private Date fechaNacimiento;
    private boolean genero;
    private Date createdAt;
}
