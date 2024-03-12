package com.dagarcvj.music.plataform.Auth;
/**
 */
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @file: LoginRequest.java
 * @author: (c) Andy
 * @created: 9/3/2024 10:27
 */

/**
 * Clase que representa la solicitud de inicio de sesión.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    /**
     * Nombre de usuario.
     */
	String username;
    /**
     * Contraseña del usuario.
     */
    String password;
}