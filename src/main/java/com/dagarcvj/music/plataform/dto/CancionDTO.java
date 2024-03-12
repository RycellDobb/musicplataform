package com.dagarcvj.music.plataform.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.dagarcvj.music.plataform.domain.Artista;
import com.dagarcvj.music.plataform.domain.ListaReproduccion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 
 * @file: CancionController.java
 * @author: (c)2024 Angel
 * @created: 1 mar 2024, 1:02:24
 *
 */

/**
 * Clase DTO (Data Transfer Object) que representa la información de una canción en el sistema.
 * Contiene información como el identificador, título, duración, género, y fecha de lanzamiento de la canción.
 * Además, incluye referencias al artista asociado a la canción y a las listas de reproducción en las que se encuentra la canción.
 */


@Data
public class CancionDTO extends RepresentationModel<CancionDTO>{
	private Long idCancion;
	@NotBlank(message = "El nombre de la canción no puede estar vacío")
    @NotNull(message = "El nombre de la canción ser nulo")
    @Size(min = 1, max = 30, message = "El título de la canción debe tener entre 1 y 30 caracteres")
    private String titulo;
	@NotBlank(message = "La duración no puede estar en blanco")
    @NotNull(message = "La duración no puede ser nulo")
    @Pattern(regexp = "^\\d+:\\d+$", message = "La duración debe tener el formato 'minutos:segundos'")
    private String duracion;
	@NotNull(message = "El género de la canción  no puede ser nulo")
    @NotBlank(message = "El género de la canción no puede estar vacío")
    private String genero;
	@NotNull(message = "La fecha de lanzamiento no puede ser nulo")
    private Date fechaLanzamiento;
    private Artista artista;
    private List<ListaReproduccion> listasReproduccion = new ArrayList<>();

}
