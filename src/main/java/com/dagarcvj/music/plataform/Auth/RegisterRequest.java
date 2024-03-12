package com.dagarcvj.music.plataform.Auth;

import lombok.*;

/**
 * @file: RegisterRequest.java
 * @author: (c) Andy
 * @created: 9/3/2024 10:27
 */

/**
 * Clase que representa la solicitud de registro de usuario.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
	 /**
     * Nombre de usuario.
     */
    String username;

    /**
     * Contraseña del usuario.
     */
    String password;

    /**
     * Nombre del usuario.
     */
    String firstname;

    /**
     * Apellido del usuario.
     */
    String lastname;
}
