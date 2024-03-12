package com.dagarcvj.music.plataform.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 
 * @file: CancionController.java
 * @author: (c)2024 Angel
 * @created: 1 mar 2024, 1:02:24
 *
 */

/**
 * Clase que representa la entidad de una canción en el sistema.
 * Contiene información como el identificador, título, duración, género y fecha de lanzamiento de la canción.
 * Establece una relación muchos a uno con la entidad Artista, donde una canción pertenece a un único artista.
 * También establece una relación muchos a muchos con la entidad ListaReproduccion, donde una canción puede estar presente en múltiples listas de reproducción.
 */


@Entity
@Data
@JsonIdentityInfo(
		generator=ObjectIdGenerators.PropertyGenerator.class,
		property="idCancion")
public class Cancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCancion;
    
    private String titulo;
        
    private String duracion;
    
    private String genero;
        
    @Temporal(TemporalType.DATE)
    private Date fechaLanzamiento;
    
    // Relación con Artista (muchos a uno)
    @ManyToOne
    @JsonBackReference
    private Artista artista;
    
   //Relación con Listas de Reproducción (muchos a muchos)
    @ManyToMany(mappedBy = "cancionesLista")
    //@JsonBackReference
    private List<ListaReproduccion> listasReproduccion = new ArrayList<>();
}