package com.mediworld.mwuserapi.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mediworld.mwuserapi.model.Genero;
import com.mediworld.mwuserapi.model.LanguageCode;
import com.mediworld.mwuserapi.model.Paciente;
import com.mediworld.mwuserapi.util.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <h1>PacientePrincipal</h1>
 * Cláse que representa los detalles mapeados de un paciente para tratarlos por la seguridad
 * de la aplicacion. Esta clase es usada por {@link PacienteDetailsService}
 *
 * @author Eduardo Rasgado Ruiz
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PacientePrincipal implements UserDetails {
    private String id;
    private String username;
    @JsonIgnore
    private String nombre;
    @JsonIgnore
    private String apellidos;
    @JsonIgnore
    private Date fechaNacimiento;
    @JsonIgnore
    private Genero genero;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private LanguageCode preferableLanguageCode;
    @JsonIgnore
    private String paisNacimiento = "";
    @JsonIgnore
    private String paisResidencia = "";
    @JsonIgnore
    private  String password;
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Meotodo para crear un paciente con accesos de autoridad
     * @param paciente Un paciente con todos los datos
     * @return
     */
    public static PacientePrincipal create(Paciente paciente) {
        // obteniendo las autoridades que representa el paciente
        List<GrantedAuthority> authorities = paciente
                .getPerfiles()
                .stream()
                .map(perfil ->
                        new SimpleGrantedAuthority(perfil.getName().name())
                        )
                .collect(Collectors.toList());

        PacientePrincipal pacienteAuth = new PacientePrincipal();
        pacienteAuth = mappingPaciente(pacienteAuth,  paciente, authorities);

        return pacienteAuth;
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
        if(this == o) { return true; }
        if(o == null || getClass() != o.getClass()) { return false; }

        PacientePrincipal that = (PacientePrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Metodo que mapea los datos de un paciente a una entidad de tipo
     * PacientePrincipal
     * @param paAuth el paciente con auth
     * @param pa el paciente del modelo
     * @param authorities los permisos
     * @return un paciente con auth
     */
    public static PacientePrincipal mappingPaciente(PacientePrincipal paAuth, Paciente pa,
                                                    List<GrantedAuthority> authorities) {
        paAuth.setId(pa.getId());
        paAuth.setUsername(pa.getUsername());
        paAuth.setNombre(pa.getNombre());
        paAuth.setApellidos(pa.getApellidos());
        paAuth.setFechaNacimiento(pa.getFechaNacimiento());
        paAuth.setEmail(pa.getEmail());
        paAuth.setPassword(pa.getPassword());
        paAuth.setAuthorities(authorities);

        if(pa.getPaisNacimiento() != null){
            paAuth.setPaisNacimiento(pa.getPaisNacimiento().getName());
        }
        if(pa.getPaisResidencia() != null) {
            paAuth.setPaisResidencia(pa.getPaisResidencia().getName());
        }

        if(pa.getPreferableLanguage() != null){
            paAuth.setPreferableLanguageCode(pa.getPreferableLanguage().getCode());
        }

        if(pa.getGenero().name().equals(AppConstants.HOMBRE) || pa.getGenero().name()
                .equals(AppConstants.MUJER)){
            paAuth.setGenero(pa.getGenero());
        }
        return paAuth;
    }
}
