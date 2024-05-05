package com.viamatica.autenticacion.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class User extends Base implements UserDetails {

    @Pattern(regexp = "^[^\\W_]*$", message = "No debe contener signos")
    @Pattern(regexp = ".*\\d.*", message = "Debe contener al menos un número")
    @Pattern(regexp = ".*[A-Z].*", message = "Debe contener al menos una letra mayúscula")
    @Size(min = 8, max = 20, message = "Longitud mínima de 8 dígitos y máxima de 20 dígitos")
    @Column(name = "user_name",length = 50, unique = true)
    private String username;


    @Pattern(regexp = ".*[^\\W_].*", message = "Debe contener al menos un signo")
    @Pattern(regexp = ".*[A-Z].*", message = "Debe contener al menos una letra mayúscula")
    @Pattern(regexp = "^\\S*$", message = "No debe contener espacios en blanco")
    @Size(min = 8, max = 255, message = "Longitud mínima de 8 dígitos y máxima de 255 dígitos")
    @Column(name = "user_password",length = 255)
    private String password;

    @Column(name = "user_email",length = 120,unique = true)
    private String userEmail;

    @Column(name = "is_active")
    private Boolean userIsActive;

    @Column(name = "status_user")
    private Boolean statusUser;

    @ManyToOne()
    @JoinColumn(name = "fk_person_id")
    private Person userPerson;

    @ManyToMany
    @JoinTable(
            name = "rol_users", // Table name
            joinColumns = @JoinColumn(name = "user_id"),  // table identifier
            inverseJoinColumns = @JoinColumn(name = "rol_id"))  // table relation
    private List<Rol> rolList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
