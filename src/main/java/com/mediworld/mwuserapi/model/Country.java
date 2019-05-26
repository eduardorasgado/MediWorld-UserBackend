package com.mediworld.mwuserapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * <h1>Country</h1>
 * Clase que va a manejar el nombre por enumeracion de cada posible pais,
 * manejará posibles normas legales o nomenclaturas
 *
 * @author Eduardo Rasgado Ruiz
 * @see Paciente
 * @see Medico
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String id;

    @NotBlank
    //el nombre del pais mas largo es libia en su nombre nativo con 74 caracteres
    @Column(length = 74)
    private String name;


    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_language")
    private Language language;

    @OneToMany(mappedBy = "paisResidencia", fetch = FetchType.EAGER)
    private Set<Paciente> nacimientoPaciente;
    @OneToMany(mappedBy = "paisNacimiento", fetch = FetchType.EAGER)
    private Set<Paciente> residenciaPaciente;

    @OneToMany(mappedBy = "paisResidencia", fetch = FetchType.LAZY)
    private Set<Medico> nacimientoMedico;
    @OneToMany(mappedBy = "paisNacimiento", fetch = FetchType.LAZY)
    private Set<Medico> residenciaMedico;

}
