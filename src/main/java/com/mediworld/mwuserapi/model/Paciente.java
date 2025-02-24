package com.mediworld.mwuserapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="paciente", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class Paciente extends Usuario {
    @Id
    @GeneratedValue(generator = "uuid-system")
    @GenericGenerator(name = "uuid-system", strategy = "uuid2")
    private String id;

    @NotBlank
    @Size(max = 30, min = 3)
    private String username;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_preferableLanguage")
    private Language preferableLanguage;

    @Enumerated(value = EnumType.STRING)
    private Genero generoConviccion;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "paciente_perfiles",
            joinColumns = @JoinColumn(name = "paciente_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id"))
    private Set<Perfil> perfiles = new HashSet<>();
}
