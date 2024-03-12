package com.dagarcvj.music.plataform.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 
 * @file: ListaReproduccionController.java
 * @author: (c)2024 Andy
 * @created: 1 mar 2024, 1:05:10
 *
 */

/**
 * Clase que representa la entidad de una lista de reproducción en el sistema.
 * Contiene información como el identificador y nombre de la lista de reproducción.
 * Establece una relación uno a uno con la entidad Usuario, donde una lista de reproducción pertenece a un único usuario.
 * También establece una relación muchos a muchos con la entidad Cancion, donde una lista de reproducción puede contener múltiples canciones.
 */


@Entity
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idLista")

public class ListaReproduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLista;
    
    @Column(unique = true)
    private String nombre;

    // Relación con Usuario a uno a uno
    @OneToOne
    @JsonBackReference
    private Usuario usuario;
    
    @ManyToMany
    @JoinTable(
        name = "cancion_listaReproduccion",
        joinColumns = @JoinColumn(name = "idLista"),
        inverseJoinColumns = @JoinColumn(name = "idCancion"))
    
    //@JsonManagedReference
    private List<Cancion> cancionesLista = new ArrayList<>();

}