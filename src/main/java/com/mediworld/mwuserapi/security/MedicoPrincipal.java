package com.mediworld.mwuserapi.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mediworld.mwuserapi.model.Genero;
import com.mediworld.mwuserapi.model.Medico;
import com.mediworld.mwuserapi.util.AppConstants;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Clase que define los datos de un medico usados para la autentificacion
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
public class MedicoPrincipal implements UserDetails {
    private String id;
    private String email;
    @JsonIgnore
    private String nombre;
    @JsonIgnore
    private String apellidos;
    @JsonIgnore
    private Genero genero;
    @JsonIgnore
    private String password;
    // TODO: Convertirlo a un payload?
    @JsonIgnore
    private String especialidadMedica;
    @JsonIgnore
    private String telefonoCelularOpcionUno;
    @JsonIgnore
    private String telefonoCelularOpcionDos;
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public static MedicoPrincipal create(Medico medico) {
        List<GrantedAuthority> authorities = medico
                .getPerfiles()
                .stream()
                .map(perfil ->
                        new SimpleGrantedAuthority(
                                perfil.getName().name()
                        )
                )
                .collect(Collectors.toList());

        MedicoPrincipal medicoPrincipal = new MedicoPrincipal();
        medicoPrincipal = mappingMedico(medicoPrincipal, medico, authorities);

        return medicoPrincipal;
    }

    /**
     * Returns the username used to authenticate the user. Cannot return <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    @Override
    public String getUsername() {
        // El email sera el username para la autentificacion
        return email;
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        MedicoPrincipal that = (MedicoPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private static MedicoPrincipal mappingMedico(
            MedicoPrincipal medicoPrincipal, Medico medico, List<GrantedAuthority> authorities) {
        medicoPrincipal.setId(medico.getId());
        medicoPrincipal.setNombre(medico.getNombre());
        medicoPrincipal.setApellidos(medico.getApellidos());
        medicoPrincipal.setPassword(medico.getPassword());
        if(medico.getGenero().name().equals(AppConstants.HOMBRE)
                || medico.getGenero().name().equals(AppConstants.MUJER)){
            medicoPrincipal.setGenero(medico.getGenero());
        }
        if(medico.getEspecialidadMedica() != null){
            medicoPrincipal.setEspecialidadMedica(
                    medico.getEspecialidadMedica().getName()
            );
        }
        if(medico.getTelefonoCelularOpcionUno() !=null){
            medicoPrincipal.setTelefonoCelularOpcionUno(
                    medico.getTelefonoCelularOpcionUno()
            );
        }
        if(medico.getTelefonoCelularOpcionDos() !=null){
            medicoPrincipal.setTelefonoCelularOpcionDos(
                    medico.getTelefonoCelularOpcionDos()
            );
        }
        medicoPrincipal.setAuthorities(authorities);

        return medicoPrincipal;
    }
}
