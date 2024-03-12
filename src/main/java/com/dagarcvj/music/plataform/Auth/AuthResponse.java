/**
 * @file: AuthResponse.java
 * @author: (c) Andy
 * @created: 9/3/2024 10:26
 */

/**
 * Clase que representa la respuesta de autenticación.
 */

package com.dagarcvj.music.plataform.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la respuesta de autenticación.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

/**
 * Token de autenticación.
 */
public class AuthResponse {
    String token;
}