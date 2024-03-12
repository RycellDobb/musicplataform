package com.dagarcvj.music.plataform.Auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

/**
 * @file: AuthController.java
 * @author: (c) Andy
 * @created: 9/3/2024 10:26
 */

/**
 * Clase controladora que maneja los puntos finales relacionados con la autenticación.
 */

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Punto final para el inicio de sesión del usuario.
     *
     * @param request Objeto LoginRequest que contiene las credenciales del usuario.
     * @return ResponseEntity que contiene un objeto AuthResponse con los detalles de autenticación.
     */
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }
    
    /**
     * Punto final para el registro de usuarios.
     *
     * @param request Objeto RegisterRequest que contiene los detalles de registro del usuario.
     * @return ResponseEntity que contiene un objeto AuthResponse con los detalles de autenticación.
     */
    
    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }


}
