package com.dagarcvj.music.plataform.dto;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.dagarcvj.music.plataform.domain.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 
 * @file: PlanMembresiaController.java
 * @author: (c)2024 Junior
 * @created: 1 mar 2024, 1:08:06
 *
 */

/**
 * Clase DTO (Data Transfer Object) que representa la información de un plan de membresía en el sistema.
 * Contiene información como el identificador, nombre, precio y descripción del plan.
 * Además, incluye una lista de usuarios que tienen este plan de membresía.
 */


@Data
public class PlanMembresiaDTO extends RepresentationModel<PlanMembresiaDTO> {
	private Long idPlan;
	@NotNull(message = "El nombre del plan no puede ser nulo")
    @NotBlank(message = "El nombre del plan no puede estar en blanco")
    @Size(min = 4, max = 7, message = "El nombre del plan debe tener entre 4 y 7 caracteres")
	private String nombre;
	@NotNull(message = "El precio del plan no puede ser nulo")
	@PositiveOrZero(message = "El precio del plan debe ser un valor positivo o cero")
    private Double precio;
	@NotNull(message = "La descripción del plan no puede ser nulo")
	@NotBlank(message = "La descripción del plan no puede estar en blanco")
    private String descripcion;
    private List<Usuario> usuarios;
}
