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
import org.springframework.web.bind.annotation.RequestBody;
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
import com.estadisticas.estadisticas_app.Servicios.DatasetServicio;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Controlador que maneja las solicitudes HTTP relacionadas con la administración de usuarios.
 * Solo los administradores pueden acceder a estas rutas, ya que están protegidas con seguridad basada en roles.
 */
@CrossOrigin(origins = "https://jaquedev.es")
@RestController
@RequestMapping("/api/admin/datasets")
public class DatasetControlador {
	
	 private static final Logger logger = LoggerFactory.getLogger(DatasetControlador.class);
	 @Autowired
	    private DatasetServicio datasetServicio;
	 @Autowired
	    private CategoriaRepositorio categoriaRepository;
	 
	 
	 /**
	     * Sube un nuevo dataset al sistema, enviando archivo y metadatos como formulario multipart.
	     *
	     * @param metadataJson JSON con los metadatos del dataset.
	     * @param archivo      Archivo CSV o similar que contiene los datos.
	     * @param adminId      ID del administrador que realiza la carga.
	     * @return El dataset creado o un error detallado si ocurre una excepción.
	 */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/subir", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> subirDataset(
        @RequestPart("metadata") String metadataJson,
        @RequestPart("archivo") MultipartFile archivo,
        @RequestParam("adminId") Long adminId
    ) {
    	 logger.info("Intentando subir dataset por el administrador ID: {}", adminId);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DatasetMetadataDto metadata = objectMapper.readValue(metadataJson, DatasetMetadataDto.class);
            Dataset nuevoDataset = datasetServicio.subirDataset(metadata, archivo, adminId);
            logger.info("Dataset subido correctamente: {}", nuevoDataset.getNombreDataset());
            return ResponseEntity.ok(nuevoDataset);

        } catch (Exception e) {
        	 logger.error("Error al subir el dataset: {}", e.getMessage(), e);
        	
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al subir el dataset: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al subir el dataset: " + e.getMessage());
        }
    }
    
    /**
     * Filtra datasets por nombre, formato o categoría.
     *
     * @param nombre     Nombre parcial o completo del dataset.
     * @param formato    Formato del dataset (por ejemplo, CSV, JSON).
     * @param idCategoria ID de la categoría a filtrar.
     * @return Lista de datasets filtrados según los parámetros.
     */
    @GetMapping("/filtrar")
    public List<DatasetDto> filtrarDatasets(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String formato,
            @RequestParam(required = false) Long idCategoria) {
    	logger.info("Filtrando datasets por nombre: {}, formato: {}, categoría ID: {}", nombre, formato, idCategoria);
        return datasetServicio.filtrarDatasets(nombre, formato, idCategoria);
    }

    /**
     * Lista todos los datasets registrados en el sistema.
     *
     * @return Lista de objetos DatasetDto.
     */
    @GetMapping("/listarDataset")
    public ResponseEntity<List<DatasetDto>> listarTodos() {
    	logger.info("Listando todos los datasets (vista administrador)");
        List<DatasetDto> datasets = datasetServicio.listarTodosLosDatasets();
        return ResponseEntity.ok(datasets);
    }
    /**
     * Elimina un dataset por su ID.
     *
     * @param id ID del dataset a eliminar.
     * @return Respuesta vacía si se elimina correctamente, o error si no se encuentra.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarDataset(@PathVariable Long id) {
    	 logger.info("Eliminando dataset con ID: {}", id);
        boolean eliminado = datasetServicio.eliminarDataset(id);
        if (!eliminado) {
        	 logger.warn("Intento de eliminar dataset no existente con ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dataset no encontrado.");
        }
        logger.info("Dataset eliminado con éxito.");
        return ResponseEntity.noContent().build();
    }
    /**
     * Lista todas las categorías disponibles para clasificación de datasets.
     *
     * @return Lista de entidades Categoria.
     */
    @GetMapping("/listarCategorias")
    public ResponseEntity<List<Categoria>> listarCategorias() {
    	logger.info("Listando todas las categorías.");
        List<Categoria> categorias = categoriaRepository.findAll();
        return ResponseEntity.ok(categorias);  // Devuelve la lista de categorías
    }
    /**
     * Crea una nueva categoría para los datasets.
     *
     * @param categoria Objeto de categoría a crear.
     * @return Categoría creada o error en caso de fallo.
     */
    @PostMapping("/categorias")
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria) {
    	 logger.info("Creando nueva categoría: {}", categoria.getNombreCategoria());
        try {
            categoria.setIdCategoria(null);  // Por si acaso, para evitar que venga con id
            Categoria nuevaCategoria = datasetServicio.crearCategoria(categoria);
            logger.info("Categoría creada con éxito: {}", nuevaCategoria.getNombreCategoria());
            return ResponseEntity.ok(nuevaCategoria);
        } catch (Exception e) {
        	logger.error("Error al crear categoría: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/conteo-por-categoria")
    public Map<String, Long> obtenerConteoPorCategoria() {
        return datasetServicio.obtenerConteoDatasetsPorCategoria();
    }
    
    
}
