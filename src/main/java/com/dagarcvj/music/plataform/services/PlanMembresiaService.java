package com.dagarcvj.music.plataform.services;

import java.util.List;

import com.dagarcvj.music.plataform.domain.PlanMembresia;
import com.dagarcvj.music.plataform.domain.Usuario;
import com.dagarcvj.music.plataform.exception.IllegalOperationException;

import jakarta.persistence.EntityNotFoundException;
/**
 * 
 * @file: PlanMembresiaController.java
 * @author: (c)2024 Junior
 * @created: 1 mar 2024, 1:08:06
 *
 */
public interface PlanMembresiaService {
	/**
     * Lista todos los planes de membresía disponibles.
     *
     * @return Lista de planes de membresía.
     */
    List<PlanMembresia> listarPlanesMembresias();

    /**
     * Busca un plan de membresía por su identificador único.
     *
     * @param idPlanMembresia Identificador único del plan de membresía a buscar.
     * @return Plan de membresía encontrado.
     * @throws EntityNotFoundException Si el plan de membresía no se encuentra.
     */
    PlanMembresia buscarPorIdPlanMembresia(Long idPlanMembresia) throws EntityNotFoundException;

    /**
     * Graba un nuevo plan de membresía en el sistema.
     *
     * @param planMembresia Plan de membresía a grabar.
     * @return Plan de membresía grabado.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    PlanMembresia grabarPlanMembresia(PlanMembresia planMembresia) throws IllegalOperationException;

    /**
     * Actualiza la información de un plan de membresía existente.
     *
     * @param idPlanMembresia Identificador único del plan de membresía a actualizar.
     * @param planMembresia   Plan de membresía con los nuevos datos.
     * @return Plan de membresía actualizado.
     * @throws EntityNotFoundException  Si el plan de membresía no se encuentra.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    PlanMembresia actualizarPlanMembresia(Long idPlanMembresia, PlanMembresia planMembresia)
            throws EntityNotFoundException, IllegalOperationException;

    /**
     * Elimina un plan de membresía por su identificador único.
     *
     * @param idPlanMembresia Identificador único del plan de membresía a eliminar.
     * @throws EntityNotFoundException  Si el plan de membresía no se encuentra.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    void eliminarPlanMembresia(Long idPlanMembresia) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Lista todos los usuarios asociados a un plan de membresía específico.
     *
     * @param idPlanMembresia Identificador único del plan de membresía.
     * @return Lista de usuarios asociados al plan de membresía.
     */
    List<Usuario> listarUsuariosPorIdPlanMembresia(Long idPlanMembresia);
}
