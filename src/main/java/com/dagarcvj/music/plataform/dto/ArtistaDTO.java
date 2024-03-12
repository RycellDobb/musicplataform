package com.dagarcvj.music.plataform.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.dagarcvj.music.plataform.domain.Cancion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
/**
 * 
 * @file: ArtistaController.java
 * @author: (c)2024 Cleysi
 * @created: 1 mar 2024, 9:24:06
 *
 */

/**
 * Clase DTO (Data Transfer Object) que representa la información de un artista en el sistema.
 * Contiene información como el identificador, nombre, género musical, país de origen y fecha de nacimiento del artista.
 * Además, incluye una lista de canciones asociadas al artista.
 */


@Data
public class ArtistaDTO extends RepresentationModel<ArtistaDTO>{
    private Long idArtista;
    @NotBlank(message = "El nombre del artista no puede estar vacío")
    @NotNull(message = "La nombre del artista no puede ser nula")
    private String nombre;
    @NotBlank(message = "El genero musical no puede estar vacío")
    @NotNull(message = "La genero musical no puede ser nulo")
    private String generoMusical;
    @NotBlank(message = "El pais de origen no puede estar vacío")
    private String paisOrigen;
    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    @Past(message = "La fecha de nacimiento debe estar en el pasado")
    private Date fechanac;
    private List<Cancion> canciones = new ArrayList<>();
}
