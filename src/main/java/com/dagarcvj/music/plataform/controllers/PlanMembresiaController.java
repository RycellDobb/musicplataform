package com.dagarcvj.music.plataform.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.dagarcvj.music.plataform.domain.PlanMembresia;
import com.dagarcvj.music.plataform.domain.Usuario;
import com.dagarcvj.music.plataform.dto.PlanMembresiaDTO;
import com.dagarcvj.music.plataform.dto.UsuarioDTO;
import com.dagarcvj.music.plataform.services.PlanMembresiaService;
import com.dagarcvj.music.plataform.util.ApiResponse;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @file: PlanMembresiaController.java
 * @author: (c)2024 Junior
 * @created: 1 mar 2024, 1:08:06
 *
 */

@RestController
@RequestMapping("/api/planMembresias")
public class PlanMembresiaController {
	 @Autowired
	    private PlanMembresiaService planMembresiaService;
	    @Autowired
	    private ModelMapper modelMapper;
	    
	    /**
	     * Método que maneja una solicitud GET para listar todos los planes de membresía.
	     * 
	     * @return ResponseEntity que contiene una lista de planes de membresía en forma de PlanMembresiaDTOs si se encuentran,
	     *         o un ResponseEntity sin contenido si no se encuentran planes de membresía.
	     */

	    @GetMapping(value = "/listar", headers = "X-VERSION=1")
	 	public ResponseEntity<?> listarPlanMembresia() {
	 	    List<PlanMembresia> planMembresias = planMembresiaService.listarPlanesMembresias();
	 	    if (planMembresias == null || planMembresias.isEmpty()) {
	 	        return ResponseEntity.noContent().build();
	 	    } else {
	 	        List<PlanMembresiaDTO> membresiasDTOs = planMembresias.stream()
	 	                .map(planMembresia -> modelMapper.map(planMembresia, PlanMembresiaDTO.class))
	 	                .collect(Collectors.toList());
	 	        
	 	       for(PlanMembresiaDTO plan : membresiasDTOs) {
	 	        	plan.add(linkTo(methodOn(PlanMembresiaController.class).obtenerPlanMembresiaPorId(plan.getIdPlan())).withSelfRel());
	 	        	plan.add(linkTo(methodOn(PlanMembresiaController.class).listarPlanMembresia()).withRel(IanaLinkRelations.COLLECTION));
	 	        }

	 	        ApiResponse<List<PlanMembresiaDTO>> response = new ApiResponse<>(true, "Lista de planes de membresia obtenida con éxito", membresiasDTOs);
	 	        return ResponseEntity.ok(response);
	 	    }
	 	}

	    /**
	     * Método que maneja una solicitud GET para obtener un plan de membresía por su identificador.
	     * 
	     * @param id el identificador del plan de membresía que se desea obtener.
	     * @return ResponseEntity que contiene el plan de membresía con el identificador especificado en forma de PlanMembresiaDTO si se encuentra,
	     *         o un ResponseEntity sin contenido si no se encuentra ningún plan de membresía con el identificador especificado.
	     */

	    @GetMapping(value = "/listar/{id}", headers = "X-VERSION=1")
	    public ResponseEntity<?> obtenerPlanMembresiaPorId(@PathVariable Long id) {
	    	 PlanMembresia planMembresia = planMembresiaService.buscarPorIdPlanMembresia(id);
		     PlanMembresiaDTO  membresiaDTO = modelMapper.map(planMembresia, PlanMembresiaDTO.class);
		     membresiaDTO.add(linkTo(methodOn(PlanMembresiaController.class).obtenerPlanMembresiaPorId(membresiaDTO.getIdPlan())).withSelfRel());
		     membresiaDTO.add(linkTo(methodOn(PlanMembresiaController.class).listarPlanMembresia()).withRel(IanaLinkRelations.COLLECTION));
		     ApiResponse <PlanMembresiaDTO> response =new ApiResponse<>(true, "Plan de membresia obtenido con éxito", membresiaDTO);
		     return ResponseEntity.ok(response);
	    }
	    
	    /**
	     * Método que maneja una solicitud GET para listar todos los usuarios asociados a un plan de membresía por su identificador.
	     * 
	     * @param idPlanMembresia el identificador del plan de membresía del cual se desean obtener los usuarios asociados.
	     * @return ResponseEntity que contiene una lista de usuarios asociados al plan de membresía especificado en forma de UsuarioDTOs si se encuentran,
	     *         o un ResponseEntity sin contenido si no se encuentran usuarios asociados al plan de membresía.
	     */

	    @GetMapping(value = "/listar/{idPlanMembresia}/usuarios", headers = "X-VERSION=1")
	    public ResponseEntity<?> listarUsuariosPorIdPlanMembresia(@PathVariable Long idPlanMembresia) {
	        List<Usuario> usuarios = planMembresiaService.listarUsuariosPorIdPlanMembresia(idPlanMembresia);

	        if (usuarios == null || usuarios.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        } else {
	            List<UsuarioDTO> usuarioDTOs = usuarios.stream()
	                    .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
	                    .collect(Collectors.toList());
	            
	            for(UsuarioDTO usuariomap : usuarioDTOs) {
	            	usuariomap.add(linkTo(methodOn(PlanMembresiaController.class).obtenerPlanMembresiaPorId(usuariomap.getIdUsuario())).withSelfRel());
	            	usuariomap.add(linkTo(methodOn(PlanMembresiaController.class).listarPlanMembresia()).withRel(IanaLinkRelations.COLLECTION));
	            	usuariomap.add(linkTo(methodOn(PlanMembresiaController.class).listarUsuariosPorIdPlanMembresia(idPlanMembresia)).withRel("relacion"));
	            }

	            ApiResponse<List<UsuarioDTO>> response = new ApiResponse<>(true, "Lista de usuarios asociados al plan de membresía obtenida con éxito", usuarioDTOs);
	            return ResponseEntity.ok(response);
	        }
	    }
	    
	    /**
	     * Método que maneja una solicitud POST para crear un nuevo plan de membresía.
	     * 
	     * @param planMembresiaDTO la representación DTO del plan de membresía que se desea crear.
	     * @return ResponseEntity que indica el éxito de la creación del plan de membresía junto con la representación DTO del plan de membresía creado si la operación es exitosa.
	     */

	    @PostMapping(value = "/crear", headers = "X-VERSION=1")
	    public ResponseEntity<?> crearPlanMembresia(@Valid @RequestBody PlanMembresiaDTO planMembresiaDTO) {
	    	PlanMembresia  planMembresia = modelMapper.map(planMembresiaDTO, PlanMembresia.class);
	    	planMembresiaService.grabarPlanMembresia(planMembresia);
	    	PlanMembresiaDTO membresiaDTOCreado=modelMapper.map(planMembresia, PlanMembresiaDTO.class);
	    	
	    	membresiaDTOCreado.add(linkTo(methodOn(PlanMembresiaController.class).obtenerPlanMembresiaPorId(membresiaDTOCreado.getIdPlan())).withSelfRel());
	    	membresiaDTOCreado.add(linkTo(methodOn(PlanMembresiaController.class).listarPlanMembresia()).withRel(IanaLinkRelations.COLLECTION));
	    	
	        ApiResponse <PlanMembresiaDTO> response =new ApiResponse<>(true, "Plan de Membresia guardado con éxito", membresiaDTOCreado);
	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    }

	    /**
	     * Método que maneja una solicitud PUT para editar un plan de membresía existente.
	     * 
	     * @param planMembresiaDTO la representación DTO actualizada del plan de membresía.
	     * @param id el identificador del plan de membresía que se desea editar.
	     * @return ResponseEntity que indica el éxito de la actualización del plan de membresía junto con la representación DTO del plan de membresía actualizado si la operación es exitosa.
	     */

	    @PutMapping(value = "/editar/{id}", headers = "X-VERSION=1")
	    public ResponseEntity<?> editarPlanMembresia(@Valid @RequestBody  PlanMembresiaDTO planMembresiaDTO,@PathVariable Long id) {
	    	PlanMembresia  planMembresia = modelMapper.map(planMembresiaDTO, PlanMembresia.class);
	    	planMembresiaService.actualizarPlanMembresia(id, planMembresia);
	    	PlanMembresiaDTO membresiaDTOActualizado=modelMapper.map(planMembresia, PlanMembresiaDTO.class);
	    	
	    	membresiaDTOActualizado.add(linkTo(methodOn(PlanMembresiaController.class).obtenerPlanMembresiaPorId(membresiaDTOActualizado.getIdPlan())).withSelfRel());
	    	membresiaDTOActualizado.add(linkTo(methodOn(PlanMembresiaController.class).listarPlanMembresia()).withRel(IanaLinkRelations.COLLECTION));
	        ApiResponse <PlanMembresiaDTO> response =new ApiResponse<>(true, "Plan de Membresia actualizado con éxito", membresiaDTOActualizado);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    }
	    /**
	     * Método que maneja una solicitud DELETE para eliminar un plan de membresía por su identificador.
	     * 
	     * @param id el identificador del plan de membresía que se desea eliminar.
	     * @return ResponseEntity que indica el éxito de la eliminación del plan de membresía si la operación es exitosa.
	     */

	    @DeleteMapping(value = "/eliminar/{id}", headers = "X-VERSION=1")
	    public ResponseEntity<?> eliminarPlanMembresia(@PathVariable Long id) {
	    	planMembresiaService.eliminarPlanMembresia(id);
		    ApiResponse<String> response = new ApiResponse<>(true, "Plan de Membresia eliminado con éxito", null);
		    return ResponseEntity.status(HttpStatus.OK).body(response);
	    }
}
