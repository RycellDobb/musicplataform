package com.dagarcvj.music.plataform.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @file: ArtistaController.java
 * @author: (c)2024 Cleysi
 * @created: 1 mar 2024, 9:24:06
 *
 */

/**
 * Clase que representa un mensaje de error personalizado.
 * Contiene información sobre el código de estado HTTP, la marca de tiempo,
 * el mensaje de error y una descripción detallada.
 */

@Getter
@Setter
public class ErrorMessage {
	
	private int statusCode;
    private LocalDateTime timestamp;
    private String message;
    private String description;
        
    /**
     * Constructor para crear un ErrorMessage.
     * @param statusCode El código de estado HTTP.
     * @param message El mensaje de error.
     * @param description La descripción detallada del error.
     */
        
    public ErrorMessage(HttpStatus statusCode, String message, String description) {
        this.statusCode = statusCode.value();
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.description = description.replace("uri=", "");
    }
    
}
