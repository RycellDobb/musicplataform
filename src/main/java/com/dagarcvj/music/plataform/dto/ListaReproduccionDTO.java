package com.dagarcvj.music.plataform.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.dagarcvj.music.plataform.domain.Cancion;
import com.dagarcvj.music.plataform.domain.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 
 * @file: ListaReproduccionController.java
 * @author: (c)2024 Andy
 * @created: 1 mar 2024, 1:05:10
 *
 */

/**
 * Clase DTO (Data Transfer Object) que representa la información de una lista de reproducción en el sistema.
 * Contiene información como el identificador, nombre y referencias al usuario que posee la lista de reproducción y a las canciones incluidas en ella.
 * Además, incluye una lista de canciones asociadas a la lista de reproducción.
 */



@Data
public class ListaReproduccionDTO extends RepresentationModel<ListaReproduccionDTO>{
	private Long idLista;
    @NotNull(message = "El nombre de la lista de reproducción no puede ser nulo")
    @NotBlank(message = "El nombre de la lista de reproducción no puede estar en blanco")
    @Size(min = 1, max = 50, message = "El nombre de la lista de reproducción debe tener entre 1 y 50 caracteres")
	private String nombre;
	private Usuario usuario;
	private List<Cancion> cancionesLista = new ArrayList<>();
}