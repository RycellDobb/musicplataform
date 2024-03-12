package com.dagarcvj.music.plataform.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.validation.FieldError;
import jakarta.persistence.EntityNotFoundException;

/**
 * 
 * @file: ArtistaController.java
 * @author: (c)2024 Cleysi
 * @created: 1 mar 2024, 9:24:06
 *
 */

/**
 * Clase que maneja las excepciones de validación de argumentos del método en los controladores REST.
 * Proporciona un manejo personalizado para MethodArgumentNotValidException.
 */

@ControllerAdvice
public class GlobalExceptionHandler {

	/**
     * Maneja la excepción MethodArgumentNotValidException y devuelve un ResponseEntity con un mensaje de error personalizado.
     * @param ex La excepción MethodArgumentNotValidException lanzada.
     * @param request La solicitud web asociada.
     * @return ResponseEntity que contiene el mensaje de error personalizado.
     */
    
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errores.put(fieldName, errorMessage);
        });
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                errores.toString(),
        		request.getDescription(false));
                
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Maneja la excepción EntityNotFoundException y devuelve un ResponseEntity con un mensaje de error personalizado.
     * @param ex La excepción EntityNotFoundException lanzada.
     * @param request La solicitud web asociada.
     * @return ResponseEntity que contiene el mensaje de error personalizado.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handlerResourceNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja la excepción IllegalOperationException y devuelve un ResponseEntity con un mensaje de error personalizado.
     * @param ex La excepción IllegalOperationException lanzada.
     * @param request La solicitud web asociada.
     * @return ResponseEntity que contiene el mensaje de error personalizado.
     */
    
    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<ErrorMessage> handlerIllegalOperationException(IllegalOperationException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(VersionMismatchException.class)
    public ResponseEntity<ErrorMessage> handleVersionMismatchException(VersionMismatchException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
        		HttpStatus.BAD_REQUEST, 
        		ex.getMessage(),
        		request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Maneja la excepción Exception y devuelve un ResponseEntity con un mensaje de error personalizado.
     * @param ex La excepción Exception lanzada.
     * @param request La solicitud web asociada.
     * @return ResponseEntity que contiene el mensaje de error personalizado.
     */
    
   @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handlerException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
   
}
