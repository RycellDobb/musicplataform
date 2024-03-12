package com.dagarcvj.music.plataform.util;

/**
 * 
 * @file: ArtistaController.java
 * @author: (c)2024 Cleysi
 * @created: 1 mar 2024, 9:24:06
 *
 */

/**
 * Clase ApiResponse.
 * 
 * @param <T> Tipo de datos contenido en la respuesta.
 */

import lombok.Data;
@Data
public class ApiResponse <T>{
	
	/** Indica si la operación fue exitosa. */
    private boolean success;
    
    /** Mensaje asociado a la respuesta. */
    private String message;
    
    /** Datos asociados a la respuesta. */
    private T data;

    /**
     * Constructor de la clase ApiResponse.
     * 
     * @param success Indica si la operación fue exitosa.
     * @param message Mensaje asociado a la respuesta.
     * @param data Datos asociados a la respuesta.
     */
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * Obtiene el estado de éxito de la operación.
     * 
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Establece el estado de éxito de la operación.
     * 
     * @param success Nuevo estado de éxito.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Obtiene el mensaje asociado a la respuesta.
     * 
     * @return Mensaje asociado a la respuesta.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Establece el mensaje asociado a la respuesta.
     * 
     * @param message Nuevo mensaje.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Obtiene los datos asociados a la respuesta.
     * 
     * @return Datos asociados a la respuesta.
     */
    public T getData() {
        return data;
    }

    /**
     * Establece los datos asociados a la respuesta.
     * 
     * @param data Nuevos datos.
     */
    public void setData(T data) {
        this.data = data;
    }
}
