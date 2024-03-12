package com.dagarcvj.music.plataform.User;
/**
 * @file: null.java
 * @author: (c) Cleysi
 * @created: 8/3/2024 22:04
 */
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
/**
 * Clase que representa a un usuario en el sistema.
 * Esta clase implementa la interfaz UserDetails de Spring Security, lo que permite su uso como objeto de usuario
 * autenticable en el sistema.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idUser;
    @Basic
    @Column(unique = true)
    String username;
    String password;
    String firstname;
    String lastname;
    @Enumerated(EnumType.STRING)
    Rol rol;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((rol.name())));
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
