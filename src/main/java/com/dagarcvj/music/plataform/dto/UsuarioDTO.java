package com.dagarcvj.music.plataform.dto;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.dagarcvj.music.plataform.domain.ListaReproduccion;
import com.dagarcvj.music.plataform.domain.PlanMembresia;
import com.dagarcvj.music.plataform.domain.Usuario;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 
 * @file: UsuarioController.java
 * @author: (c)2024 Cleysi
 * @created: 1 mar 2024, 1:10:12
 *
 */

/**
 * Clase DTO (Data Transfer Object) que representa la información de un usuario en el sistema.
 * Contiene información como el identificador, DNI, nombre, email, contraseña, fecha de nacimiento,
 * suscripción, plan de membresía, usuario seguidor y lista de reproducción asociada.
 * Además, incluye las siguientes relaciones:
 * - El usuario puede tener asociado un plan de membresía.
 * - El usuario puede estar suscrito a plan de membresia.
 * - El usuario puede tener un usuario seguidor.
 * - El usuario puede tener asociada una lista de reproducción.
 */


@Data
public class UsuarioDTO extends RepresentationModel<UsuarioDTO>{
    private Long idUsuario;
    @NotBlank(message = "El DNI no puede estar en blanco")
    @NotNull(message = "El DNI no puede ser nulo")
    @Size(min = 8, max = 8, message = "El DNI debe tener exactamente 8 caracteres")
    private String dni;
    @Column(unique = true)
    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede estar en blanco")
    private String nombre;
    @Column(unique = true)
    @NotBlank(message = "El email no puede estar en blanco")
    @Email(message = "El email debe ser válido")
    private String email;
    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres")
    private String password;
    @NotNull(message = "La fecha de nacimiento no puede ser nulo")
    @Past(message = "La fecha de nacimiento debe estar en el pasado")
    private Date fechaNac;
    
    private PlanMembresia planMembresia;
    private Boolean suscrito;
    private Usuario usuarioSeguidor;
    private ListaReproduccion listaReproduccion;
}
