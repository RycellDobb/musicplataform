package com.dagarcvj.music.plataform.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.dagarcvj.music.plataform.domain.Cancion;
import com.dagarcvj.music.plataform.dto.CancionDTO;
import com.dagarcvj.music.plataform.services.CancionService;
import com.dagarcvj.music.plataform.util.ApiResponse;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;
/**
 * 
 * @file: CancionController.java
 * @author: (c)2024 Angel
 * @created: 1 mar 2024, 1:02:24
 *
 */
@RestController
@RequestMapping("/api/canciones")
public class CancionController {
	@Autowired
    private CancionService cancionService;

 	@Autowired
 	private ModelMapper modelMapper;
    
 	/**
 	 * Método que maneja una solicitud GET para listar todas las canciones.
 	 * 
 	 * @return ResponseEntity que contiene una lista de canciones en forma de CancionDTOs si se encuentran,
 	 *         o un ResponseEntity sin contenido si no se encuentran canciones.
 	 */

    @GetMapping(value ="/listar", headers ="X-VERSION=1")
    public ResponseEntity<?> listarCanciones() {
    	List<Cancion> canciones = cancionService.listarCanciones();
    	if (canciones == null || canciones.isEmpty()) {
 	        return ResponseEntity.noContent().build();
 	    }else {
 	        List<CancionDTO> cancionesDTOs = canciones.stream()
 	                .map(cancion -> modelMapper.map(cancion, CancionDTO.class))
 	                .collect(Collectors.toList());
 	        
 	       for (CancionDTO cancion : cancionesDTOs) {
 	    	   cancion.add(linkTo(methodOn(CancionController.class).obtenerCancionPorId(cancion.getIdCancion())).withSelfRel());
 	    	   cancion.add(linkTo(methodOn(CancionController.class).listarCanciones()).withRel(IanaLinkRelations.COLLECTION));	
 	        }
 	        ApiResponse<List<CancionDTO>> response = new ApiResponse<>(true, "Lista de canciones obtenida con éxito", cancionesDTOs);
 	        return ResponseEntity.ok(response);
 	    }
    }
    
    /**
     * Método que maneja una solicitud GET para obtener una canción por su identificador.
     * 
     * @param id el identificador de la canción que se desea obtener.
     * @return ResponseEntity que contiene la canción con el identificador especificado en forma de CancionDTO si se encuentra,
     *         o un ResponseEntity sin contenido si no se encuentra ninguna canción con el identificador especificado.
     */

    @GetMapping(value ="/listar/{id}", headers ="X-VERSION=1")
    public ResponseEntity<?> obtenerCancionPorId(@PathVariable Long id) {
        Cancion cancion = cancionService.buscarPorIdCancion(id);
        CancionDTO  cancionDTO = modelMapper.map(cancion, CancionDTO.class);
        
        cancionDTO.add(linkTo(methodOn(CancionController.class).obtenerCancionPorId(id)).withSelfRel());
        cancionDTO.add(linkTo(methodOn(CancionController.class).listarCanciones()).withRel(IanaLinkRelations.COLLECTION));
        ApiResponse <CancionDTO> response =new ApiResponse<>(true, "Cancion obtenida con éxito", cancionDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Método que maneja una solicitud POST para crear una nueva canción.
     * 
     * @param cancionDTO la representación DTO de la canción que se desea crear.
     * @return ResponseEntity que indica el éxito de la creación de la canción junto con la representación DTO de la canción creada si la operación es exitosa.
     */

    @PostMapping(value ="/crear", headers ="X-VERSION=1")
    public ResponseEntity<?> crearCancion(@Valid @RequestBody CancionDTO cancionDTO) {
    	Cancion cancion = modelMapper.map(cancionDTO, Cancion.class);
    	cancionService.grabarCancion(cancion);
    	CancionDTO cancionDTOCreada=modelMapper.map(cancion, CancionDTO.class);
    	
    	cancionDTOCreada.add(linkTo(methodOn(CancionController.class).obtenerCancionPorId(cancionDTOCreada.getIdCancion())).withSelfRel());
    	cancionDTOCreada.add(linkTo(methodOn(CancionController.class).listarCanciones()).withRel(IanaLinkRelations.COLLECTION));
    	ApiResponse <CancionDTO> response =new ApiResponse<>(true, "Cancion guardada con éxito", cancionDTOCreada);
    	return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Método que maneja una solicitud PUT para editar una canción existente.
     * 
     * @param cancionDTO la representación DTO actualizada de la canción.
     * @param id el identificador de la canción que se desea editar.
     * @return ResponseEntity que indica el éxito de la actualización de la canción junto con la representación DTO de la canción actualizada si la operación es exitosa.
     */

    @PutMapping(value ="/editar/{id}", headers ="X-VERSION=1")
    public ResponseEntity<?> editarCancion(@Valid @RequestBody CancionDTO cancionDTO,@PathVariable Long id) {
    	Cancion cancion = modelMapper.map(cancionDTO, Cancion.class);
    	cancionService.actualizarCancion(id, cancion);
    	CancionDTO cancionDTOActualizada=modelMapper.map(cancion, CancionDTO.class);
    	
    	cancionDTOActualizada.add(linkTo(methodOn(CancionController.class).obtenerCancionPorId(cancionDTOActualizada.getIdCancion())).withSelfRel());
    	cancionDTOActualizada.add(linkTo(methodOn(CancionController.class).listarCanciones()).withRel(IanaLinkRelations.COLLECTION));
    	ApiResponse <CancionDTO> response =new ApiResponse<>(true, "Cancion actualizada con éxito", cancionDTOActualizada);
    	return ResponseEntity.status(HttpStatus.OK).body(response);
    	
    }
    
    /**
     * Método que maneja una solicitud DELETE para eliminar una canción por su identificador.
     * 
     * @param id el identificador de la canción que se desea eliminar.
     * @return ResponseEntity que indica el éxito de la eliminación de la canción si la operación es exitosa.
     */

    @DeleteMapping(value ="/eliminar/{id}", headers ="X-VERSION=1")
    public ResponseEntity<?> eliminarCancion (@PathVariable Long id) {
    	cancionService.eliminarCancion(id);
    	ApiResponse<String> response = new ApiResponse<>(true, "Cancion eliminada con exito", null);
    	return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    /**
     * Método que maneja una solicitud PUT para asignar un artista a una canción.
     * 
     * @param idCancion el identificador de la canción a la que se desea asignar el artista.
     * @param idArtista el identificador del artista que se desea asignar a la canción.
     * @return ResponseEntity que indica el éxito de la asignación del artista a la canción junto con la representación DTO de la canción actualizada si la operación es exitosa.
     */

    @PutMapping(value ="/editar/{idCancion}/asignarArtista/{idArtista}", headers ="X-VERSION=1")
    public ResponseEntity<?> asignarArtista(@PathVariable Long idCancion, @PathVariable Long idArtista){
    	Cancion cancion = cancionService.asignarArtista(idCancion, idArtista);
    	CancionDTO cancionDTO = modelMapper.map(cancion, CancionDTO.class);
    	
    	cancionDTO.add(linkTo(methodOn(CancionController.class).obtenerCancionPorId(idCancion)).withSelfRel());
    	cancionDTO.add(linkTo(methodOn(CancionController.class).asignarArtista(idCancion, idArtista)).withRel("asignacion"));
    	cancionDTO.add(linkTo(methodOn(CancionController.class).listarCanciones()).withRel(IanaLinkRelations.COLLECTION));
    	ApiResponse<CancionDTO> response = new ApiResponse<CancionDTO>(true, "Artista asignado con éxito", cancionDTO);
    	return ResponseEntity.ok(response);

    }

}
