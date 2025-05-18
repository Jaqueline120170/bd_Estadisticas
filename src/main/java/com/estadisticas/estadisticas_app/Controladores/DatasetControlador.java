package com.estadisticas.estadisticas_app.Controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.estadisticas.estadisticas_app.Dtos.DatasetDto;
import com.estadisticas.estadisticas_app.Dtos.DatasetMetadataDto;
import com.estadisticas.estadisticas_app.Modelos.Categoria;
import com.estadisticas.estadisticas_app.Modelos.Dataset;
import com.estadisticas.estadisticas_app.Repositorios.CategoriaRepositorio;
import com.estadisticas.estadisticas_app.Repositorios.DatasetRepositorio;
import com.estadisticas.estadisticas_app.Servicios.DatasetServicio;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Controlador que maneja las solicitudes HTTP relacionadas con la administración de usuarios.
 * Solo los administradores pueden acceder a estas rutas, ya que están protegidas con seguridad basada en roles.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/admin/datasets")
public class DatasetControlador {
	
	private static final Logger logger = LoggerFactory.getLogger(AdministradorControlador.class);
	
	 @Autowired
	    private DatasetServicio datasetServicio;
	 @Autowired
	    private DatasetRepositorio datasetRepository;
	 @Autowired
	    private CategoriaRepositorio categoriaRepository;
	 
	 
	/**
     * Endpoint para subir un dataset de forma manual por el administrador.
     * Solo accesible para administradores.
     * 
     * .
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/subir", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> subirDataset(
        @RequestPart("metadata") String metadataJson,
        @RequestPart("archivo") MultipartFile archivo,
        @RequestParam("adminId") Long adminId
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DatasetMetadataDto metadata = objectMapper.readValue(metadataJson, DatasetMetadataDto.class);

            Dataset nuevoDataset = datasetServicio.subirDataset(metadata, archivo, adminId);
            return ResponseEntity.ok(nuevoDataset);

        } catch (Exception e) {
        	  e.printStackTrace(); // Esto imprime el error completo en consola
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al subir el dataset: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al subir el dataset: " + e.getMessage());
        }
    }
    
    /**
     * Endpoint para filtrar datasets en vista adminnistrador.
     * 
     * .
     */
    @GetMapping("/filtrar")
    public List<DatasetDto> filtrarDatasets(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String formato,
            @RequestParam(required = false) Long idCategoria) {
        return datasetServicio.filtrarDatasets(nombre, formato, idCategoria);
    }

    /**
     * Endpoint para listar datasets en vista administrador.
     * 
     * .
     */
    @GetMapping("/listarDataset")
    public ResponseEntity<List<DatasetDto>> listarTodos() {
        List<DatasetDto> datasets = datasetServicio.listarTodosLosDatasets();
        return ResponseEntity.ok(datasets);
    }
    /**
     * Endpoint para eliminar datasets solo disponible para administrador.
     * 
     * .
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarDataset(@PathVariable Long id) {
        boolean eliminado = datasetServicio.eliminarDataset(id);
        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dataset no encontrado.");
        }
        return ResponseEntity.noContent().build();
    }
    /**
     * Endpoint para listar categorias  en vista administrador.
     * 
     * .
     */
    @GetMapping("/listarCategorias")
    public ResponseEntity<List<Categoria>> listarCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return ResponseEntity.ok(categorias);  // Devuelve la lista de categorías
    }


}
