/**
 * @file: AuthService.java
 * @author: (c) Andy
 * @created: 9/3/2024 10:27
 */

package com.dagarcvj.music.plataform.Auth;


import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dagarcvj.music.plataform.JWT.JwtService;
import com.dagarcvj.music.plataform.User.Rol;
import com.dagarcvj.music.plataform.User.User;
import com.dagarcvj.music.plataform.User.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Servicio que maneja la autenticación de usuarios.
 */

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Realiza el inicio de sesión del usuario.
     *
     * @param request Objeto LoginRequest que contiene las credenciales del usuario.
     * @return Objeto AuthResponse con el token de autenticación.
     */
    
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param request Objeto RegisterRequest que contiene los detalles del usuario a registrar.
     * @return Objeto AuthResponse con el token de autenticación del nuevo usuario registrado.
     */
    
    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .rol(Rol.USER)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
