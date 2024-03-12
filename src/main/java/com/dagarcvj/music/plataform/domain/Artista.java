package com.dagarcvj.music.plataform.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * 
 * @file: ArtistaController.java
 * @author: (c)2024 Cleysi
 * @created: 1 mar 2024, 9:24:06
 *
 */

/**
 * Clase que representa la entidad de un artista en el sistema.
 * Contiene información como el identificador, nombre, género musical, país de origen y fecha de nacimiento del artista.
 * También establece una relación uno a muchos con la entidad Cancion, donde un artista puede tener múltiples canciones asociadas.
 */


@Entity
@Data
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArtista;
    
    @Column(unique = true)
    private String nombre;
   
    private String generoMusical;
    
    private String paisOrigen;
    
    @Temporal(TemporalType.DATE)
    private Date fechanac;

    // Relación con Canciones (uno a muchos)
    @OneToMany(mappedBy = "artista")
    @JsonManagedReference
    private List<Cancion> canciones = new ArrayList<>();
}