package com.dagarcvj.music.plataform.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dagarcvj.music.plataform.domain.PlanMembresia;
import com.dagarcvj.music.plataform.domain.Usuario;
import com.dagarcvj.music.plataform.exception.IllegalOperationException;
import com.dagarcvj.music.plataform.repositories.PlanMembresiaRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * 
 * @file: PlanMembresiaController.java
 * @author: (c)2024 Junior
 * @created: 1 mar 2024, 1:08:06
 *
 */

@Service
public class PlanMembresiaServiceImp implements PlanMembresiaService {

	@Autowired
	private PlanMembresiaRepository planMembrisiaRepository;
	
	
	/**
     * Lista todos los planes de membresía registrados en el sistema.
     *
     * @return Lista de planes de membresía.
     */
	
	@Override
    @Transactional(readOnly = true)
	public List<PlanMembresia> listarPlanesMembresias() {
		return (List<PlanMembresia>) planMembrisiaRepository.findAll();
	}

	/**
     * Busca un plan de membresía por su identificador único.
     *
     * @param idPlanMembresia Identificador único del plan de membresía a buscar.
     * @return Plan de membresía encontrado.
     * @throws EntityNotFoundException Si el plan de membresía no se encuentra.
     */
	
	@Override
    @Transactional(readOnly = true)
	public PlanMembresia buscarPorIdPlanMembresia(Long idPlanMembresia){
		Optional <PlanMembresia> plMembresia = planMembrisiaRepository.findById(idPlanMembresia);
		if(plMembresia.isEmpty())throw new EntityNotFoundException("No se encontró ningún plan de Membresia con el ID: " + idPlanMembresia);
		return plMembresia.get();
	}

	/**
     * Registra un nuevo plan de membresía en el sistema.
     *
     * @param planMembresia Plan de membresía a registrar.
     * @return Plan de membresía registrado.
     * @throws IllegalOperationException Si el nombre del plan de membresía ya existe.
     */
	
	@Override
	public PlanMembresia grabarPlanMembresia(PlanMembresia planMembresia) throws IllegalOperationException{
		if(!planMembrisiaRepository.findByNombre(planMembresia.getNombre()).isEmpty()) {
			throw new IllegalOperationException("El plan de membresia ya existe");
		}
		return planMembrisiaRepository.save(planMembresia);
	}

	/**
     * Actualiza la información de un plan de membresía existente.
     *
     * @param idPlanMembresia Identificador único del plan de membresía a actualizar.
     * @param planMembresia   Plan de membresía con los nuevos datos.
     * @return Plan de membresía actualizado.
     * @throws EntityNotFoundException   Si el plan de membresía no se encuentra.
     * @throws IllegalOperationException  Si el nombre del plan de membresía ya existe.
     */
	
	@Override
	@Transactional
	public PlanMembresia actualizarPlanMembresia(Long idPlanMembresia, PlanMembresia planMembresia)throws EntityNotFoundException,IllegalOperationException {
		Optional<PlanMembresia> oPlMembresia= planMembrisiaRepository.findById(idPlanMembresia);
		if(oPlMembresia.isEmpty()) 
			throw new EntityNotFoundException("El plan de membresia no existe");
		if(!planMembrisiaRepository.findByNombre(planMembresia.getNombre()).isEmpty()) {
			throw new IllegalOperationException("El plan de membresia ya existe");
		}
		planMembresia.setIdPlan(idPlanMembresia);
		return planMembrisiaRepository.save(planMembresia);
	}

	/**
     * Elimina un plan de membresía por su identificador único.
     *
     * @param idPlanMembresia Identificador único del plan de membresía a eliminar.
     * @throws EntityNotFoundException     Si el plan de membresía no se encuentra.
     * @throws IllegalOperationException    Si el plan de membresía tiene usuarios asignados.
     */
	
	@Override
	public void eliminarPlanMembresia(Long idPlanMembresia) throws EntityNotFoundException, IllegalOperationException {
		PlanMembresia planMembresia = planMembrisiaRepository.findById(idPlanMembresia).orElseThrow(
				()->new EntityNotFoundException("El plan de membresia no existe")
				);
		
		if (!planMembresia.getUsuarios().isEmpty()) {
			throw new IllegalOperationException("El plan de membresia tiene usuarios asignadas");
		}
					
		planMembrisiaRepository.deleteById(idPlanMembresia);
	}
	
	/**
     * Lista todos los usuarios asociados a un plan de membresía específico.
     *
     * @param idPlanMembresia Identificador único del plan de membresía.
     * @return Lista de usuarios asociados al plan de membresía.
     * @throws EntityNotFoundException Si el plan de membresía no se encuentra.
     */
	
	@Override
    public List<Usuario> listarUsuariosPorIdPlanMembresia(Long idPlanMembresia) {
        Optional<PlanMembresia> planMembresiaOptional = planMembrisiaRepository.findById(idPlanMembresia);

        if (planMembresiaOptional.isPresent()) {
            PlanMembresia planMembresia = planMembresiaOptional.get();
            List<Usuario> usuarios = planMembresia.getUsuarios();
            return usuarios;
        } else {
            throw new EntityNotFoundException("Plan de membresía no encontrado con ID: " + idPlanMembresia);
        }
    }
}