package com.dagarcvj.music.plataform.config;
/**
 * @file: WebSecurityConfig.java
 * @author: (c) Angel
 * @created: 8/3/2024 22:45
 */
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dagarcvj.music.plataform.JWT.JwtAuthenticationFilter;

/**
 * Configuración de seguridad para la aplicación.
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;
    /**
     * Configura la cadena de filtros de seguridad HTTP.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( authorize -> authorize
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/artistas/listar").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/artistas/listar/{id}").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/artistas/listar/{idArtista}/canciones").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/canciones/listar").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/canciones/listar/{id}").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/listasReproduccion/listar").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/listasReproduccion/listar/{id}").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/listasReproduccion/crear").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/listasReproduccion/editar/{idListaReproduccion}/agregar-cancion/{idCancion}").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/listasReproduccion/editar/{idListaReproduccion}/remover-cancion/{idCancion}").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/listasReproduccion/eliminar/{id}").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/listasReproduccion/{idListaReproduccion}/canciones/{idCancion}/artista").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/planMembresias/listar").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/usuarios/crear").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/usuarios/editar/{id}").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/usuarios/editar/{idUsuarioSeguido}/esseguidopor/{idUsuarioSeguidor}").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/usuarios/editar/cancelar-suscripcion/{id}").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/usuarios/editar/{idUsuario}/suscribir/{idPlanMembresia}").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/usuarios/editar/{idUsuario}/asignar-lista-reproduccion/{idListaReproduccion}").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/usuarios/eliminar/{id}").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/usuarios/listar/{idUsuario}/lista-reproduccion/{idListaReproduccion}/canciones/{idCancion}/artista").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/api/**").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(sessionManager->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
