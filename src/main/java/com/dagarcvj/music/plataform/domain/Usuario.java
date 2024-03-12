package com.dagarcvj.music.plataform.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 
 * @file: UsuarioController.java
 * @author: (c)2024 Cleysi
 * @created: 1 mar 2024, 1:10:12
 *
 */

/**
 * Clase que representa la entidad de un usuario en el sistema.
 * Contiene información como el identificador, DNI, nombre, correo electrónico, contraseña, fecha de nacimiento y estado de suscripción del usuario.
 * Establece una relación muchos a uno con la entidad PlanMembresia, donde un usuario tiene asociado un plan de membresía.
 * También establece una relación uno a uno con la misma entidad (relación recursiva), donde un usuario puede seguir a otro usuario.
 * Además, tiene una relación uno a uno con la entidad ListaReproduccion, donde un usuario puede tener asociada una lista de reproducción.
 */


@Entity
@Data
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "idUsuario")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    @Column(unique = true)
    private String dni;
    
    @Column(unique = true)
    private String nombre;
    
    @Column(unique = true)
    private String email;
    
    private String password;
    
    @Temporal(TemporalType.DATE)
    private Date fechaNac;
    
    private Boolean suscrito = false;
    

    // Relación con PlanMembresia
    @ManyToOne
    @JsonBackReference
    private PlanMembresia planMembresia;

    // Relación recursiva con otro usuario (seguidor)
    @OneToOne
    @JoinColumn(name = "idUsuarioSeguidor")
    private Usuario usuarioSeguidor;
    
    @OneToOne(mappedBy = "usuario")
    @JsonManagedReference
    private ListaReproduccion listaReproduccion;

}