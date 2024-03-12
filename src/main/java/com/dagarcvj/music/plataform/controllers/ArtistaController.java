package com.dagarcvj.music.plataform.controllers;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.dagarcvj.music.plataform.domain.Artista;
import com.dagarcvj.music.plataform.domain.Cancion;
import com.dagarcvj.music.plataform.dto.ArtistaDTO;
import com.dagarcvj.music.plataform.dto.CancionDTO;
import com.dagarcvj.music.plataform.services.ArtistaService;
import com.dagarcvj.music.plataform.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @file: ArtistaController.java
 * @author: (c)2024 Cleysi
 * @created: 1 mar 2024, 9:24:06
 *
 */

@RestController
@RequestMapping("/api/artistas")
@RequiredArgsConstructor
public class ArtistaController {
	@Autowired
	private ArtistaService artistaService;
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * Método que maneja una solicitud GET para listar todos los artistas.
	 * 
	 * @return ResponseEntity que contiene una lista de artistas en forma de ArtistaDTOs si se encuentran,
	 *         o un ResponseEntity sin contenido si no se encuentran artistas.
	 */
	@GetMapping(value ="/listar", headers ="X-VERSION=1")
	public ResponseEntity<?> listarArtistas() {
	    List<Artista> artistas = artistaService.listarArtistas();
	    if (artistas == null || artistas.isEmpty()) {
	        return ResponseEntity.noContent().build();
	    } else {
	        List<ArtistaDTO> artistaDTOs = artistas.stream()
	                .map(artista -> modelMapper.map(artista, ArtistaDTO.class))
	                .collect(Collectors.toList());
	        
	        for(ArtistaDTO artista : artistaDTOs) {
	        	artista.add(linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(artista.getIdArtista())).withSelfRel());
	        	artista.add(linkTo(methodOn(ArtistaController.class).listarArtistas()).withRel(IanaLinkRelations.COLLECTION));
	        }
	        ApiResponse<List<ArtistaDTO>> response = new ApiResponse<>(true, "Lista de artistas obtenida con éxito", artistaDTOs);
	        return ResponseEntity.ok(response);
	    }
	}
	
    /**
     * Método que maneja una solicitud GET para obtener un artista por su identificador.
     *
     * @param id el identificador del artista que se desea obtener.
     * @return ResponseEntity que contiene el artista con el identificador especificado en forma de ArtistaDTO si se encuentra,
     *         o un ResponseEntity sin contenido si no se encuentra ningún artista con el identificador especificado.
     */
	@GetMapping(value ="/listar/{id}", headers ="X-VERSION=1")
	public ResponseEntity<?> obtenerArtistaPorId(@PathVariable Long id) {
	    Artista artista = artistaService.buscarPorIdArtista(id);
	    ArtistaDTO  artistaDTO = modelMapper.map(artista, ArtistaDTO.class);
	    
	    artistaDTO.add(linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(id)).withSelfRel());
	    artistaDTO.add(linkTo(methodOn(ArtistaController.class).listarArtistas()).withRel(IanaLinkRelations.COLLECTION));
	    ApiResponse <ArtistaDTO> response = new ApiResponse<>(true, "Artista obtenido con éxito", artistaDTO);
		return ResponseEntity.ok(response);
	}
	
    /**
     * Método que maneja una solicitud GET para listar todas las canciones asociadas a un artista por su identificador.
     *
     * @param idArtista el identificador del artista del cual se desean obtener las canciones.
     * @return ResponseEntity que contiene una lista de canciones asociadas al artista especificado en forma de CancionDTOs si se encuentran,
     *         o un ResponseEntity sin contenido si no se encuentran canciones asociadas al artista.
     */

	@GetMapping(value ="/listar/{idArtista}/canciones", headers ="X-VERSION=1")
    public ResponseEntity<?> listarCancionesPorIdArtista(@PathVariable Long idArtista) {
        List<Cancion> canciones = artistaService.listarCancionesPorIdArtista(idArtista);

        if (canciones == null || canciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<CancionDTO> cancionDTOs = canciones.stream()
            		.map(cancion -> modelMapper.map(cancion, CancionDTO.class))
            		.collect(Collectors.toList());
            
            for (CancionDTO cancionDTO : cancionDTOs) {
            	cancionDTO.add(linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(idArtista)).withSelfRel());
                cancionDTO.add(linkTo(methodOn(ArtistaController.class).listarCancionesPorIdArtista(idArtista)).withRel("cancion"));
                cancionDTO.add(linkTo(methodOn(ArtistaController.class).listarArtistas()).withRel(IanaLinkRelations.COLLECTION));	
            }
            
            ApiResponse<List<CancionDTO>> response = new ApiResponse<>(true, "Lista de canciones asociadas al artista obtenida con éxito", cancionDTOs);
            return ResponseEntity.ok(response);
        }
    }
	
    /**
     * Método que maneja una solicitud POST para crear un nuevo artista.
     *
     * @param artistaDTO la representación DTO del artista que se desea crear.
     * @return ResponseEntity que indica el éxito de la creación del artista junto con la representación DTO del artista creado si la operación es exitosa.
     */

	@PostMapping(value ="/crear", headers ="X-VERSION=1")
	public ResponseEntity<?> crearArtista(@Valid @RequestBody  ArtistaDTO artistaDTO) {
		Artista  artista = modelMapper.map(artistaDTO, Artista.class);
		artistaService.grabarArtista(artista);
		ArtistaDTO artistaDTOCreado=modelMapper.map(artista, ArtistaDTO.class);
		
		artistaDTOCreado.add(linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(artistaDTOCreado.getIdArtista())).withSelfRel());
	    artistaDTOCreado.add(linkTo(methodOn(ArtistaController.class).crearArtista(artistaDTOCreado)).withRel("artista creado"));
	    artistaDTOCreado.add(linkTo(methodOn(ArtistaController.class).listarArtistas()).withRel(IanaLinkRelations.COLLECTION));
	    ApiResponse <ArtistaDTO> response =new ApiResponse<>(true, "Artista guardado con éxito", artistaDTOCreado);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
    /**
     * Método que maneja una solicitud PUT para editar un artista existente.
     *
     * @param artistaDTO la representación DTO actualizada del artista.
     * @param id el identificador del artista que se desea editar.
     * @return ResponseEntity que indica el éxito de la actualización del artista junto con la representación DTO del artista actualizado si la operación es exitosa.
     */
	@PutMapping(value ="/editar/{id}", headers ="X-VERSION=1")
	public ResponseEntity<?> editarArtista(@Valid @RequestBody  ArtistaDTO artistaDTO, @PathVariable Long id) {
		Artista  artista = modelMapper.map(artistaDTO, Artista.class);
		artistaService.actualizarArtista(id, artista);
		ArtistaDTO artistaDTOActualizado=modelMapper.map(artista, ArtistaDTO.class);
		
		artistaDTOActualizado.add(linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(artistaDTOActualizado.getIdArtista())).withSelfRel());
	    artistaDTOActualizado.add(linkTo(methodOn(ArtistaController.class).listarArtistas()).withRel(IanaLinkRelations.COLLECTION));
		
	    ApiResponse <ArtistaDTO> response =new ApiResponse<>(true, "Artista actualizado con éxito", artistaDTOActualizado);
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	} 
	
    /**
     * Método que maneja una solicitud DELETE para eliminar un artista por su identificador.
     *
     * @param id el identificador del artista que se desea eliminar.
     * @return ResponseEntity que indica el éxito de la eliminación del artista si la operación es exitosa.
     */

	@DeleteMapping(value ="/eliminar/{id}", headers ="X-VERSION=1")
	public ResponseEntity<?> eliminarArtista(@PathVariable Long id) {
	   artistaService.eliminarArtista(id);
	   ApiResponse<String> response = new ApiResponse<>(true, "Artista eliminado con éxito", null);
	   return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
