package com.dagarcvj.music.plataform.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 
 * @file: PlanMembresiaController.java
 * @author: (c)2024 Junior
 * @created: 1 mar 2024, 1:08:06
 *
 */

/**
 * Clase que representa la entidad de un plan de membresía en el sistema.
 * Contiene información como el identificador, nombre, precio y descripción del plan de membresía.
 * Establece una relación uno a muchos con la entidad Usuario, donde un plan de membresía puede tener múltiples usuarios asociados.
 */


@Entity
@Data
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "idPlan")
public class PlanMembresia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlan;
    
    @Column(unique = true)
    private String nombre;

    @PositiveOrZero(message = "El precio del plan debe ser un valor positivo o cero")
    private Double precio;
    
    private String descripcion;

    // Relación con Usuarios
    @OneToMany(mappedBy = "planMembresia")
    @JsonManagedReference
    private List<Usuario> usuarios;
}