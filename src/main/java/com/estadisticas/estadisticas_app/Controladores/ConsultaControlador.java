package com.estadisticas.estadisticas_app.Controladores;

import java.util.List;

import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estadisticas.estadisticas_app.Dtos.ConsultaDto;
import com.estadisticas.estadisticas_app.Dtos.DatasetDto;
import com.estadisticas.estadisticas_app.Dtos.DescargaDto;
import com.estadisticas.estadisticas_app.Modelos.Categoria;
import com.estadisticas.estadisticas_app.Repositorios.CategoriaRepositorio;
import com.estadisticas.estadisticas_app.Servicios.ConsultaServicio;
import com.estadisticas.estadisticas_app.Servicios.DatasetServicio;

@CrossOrigin(origins = "https://jaquedev.es")
@RestController
@RequestMapping("/api/consultas")
public class ConsultaControlador {
	
	private static final Logger logger = LoggerFactory.getLogger(AdministradorControlador.class);
	@Autowired
    private ConsultaServicio consultaServicio;
	@Autowired
    private DatasetServicio datasetServicio;
	@Autowired
    private CategoriaRepositorio categoriaRepository;
	
	/**
     * Endpoint para crear una consulta.
     * 
     * .
     */
    @PostMapping("/consultas")
    public ResponseEntity<ConsultaDto> crearConsulta(@RequestBody ConsultaDto consultaDTO) {
    	 logger.info("Creando nueva consulta: {}", consultaDTO);
        try {
            ConsultaDto creado = consultaServicio.crearConsulta(consultaDTO);
            logger.info("Consulta creada con ID: {}", creado.getIdUsuario());
            return ResponseEntity.ok(creado);
        } catch (Exception e) {
        	 logger.error("Error al crear consulta: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint para obtener consulta por id.
     * 
     * .
     */
    @GetMapping("/consultas/{id}")
    public ResponseEntity<ConsultaDto> obtenerConsulta(@PathVariable Long id) {
    	 logger.info("Obteniendo consulta con ID: {}", id);
        try {
            ConsultaDto consultaDTO = consultaServicio.obtenerConsulta(id);
            return ResponseEntity.ok(consultaDTO);
        } catch (Exception e) {
        	logger.error("Consulta con ID {} no encontrada: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para listar consultas.
     * 
     * .
     */
    @GetMapping("/consultas")
    public ResponseEntity<List<ConsultaDto>> listarConsultas() {
        logger.info("Listando todas las consultas...");
        try {
            List<ConsultaDto> consultas = consultaServicio.listarConsultas();
            logger.info("Se encontraron {} consultas", consultas.size());
            return ResponseEntity.ok(consultas);
        } catch (Exception e) {
            logger.error("Error al listar consultas: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }


    /**
     * Endpoint para crear descarga.
     * 
     * .
     */
    @PostMapping("/descargas")
    public ResponseEntity<DescargaDto> crearDescarga(@RequestBody DescargaDto descargaDTO) {
        logger.info("Creando nueva descarga: {}", descargaDTO);
        try {
            DescargaDto creado = consultaServicio.crearDescarga(descargaDTO);
            logger.info("Descarga creada con ID: {}", creado.getId());
            return ResponseEntity.ok(creado);
        } catch (Exception e) {
            logger.error("Error al crear descarga: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }


    /**
     * Endpoint para obtener descarga por id.
     * 
     * .
     */
    @GetMapping("/descargas/{id}")
    public ResponseEntity<DescargaDto> obtenerDescarga(@PathVariable Long id) {
        try {
            DescargaDto descargaDTO = consultaServicio.obtenerDescarga(id);
            return ResponseEntity.ok(descargaDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para listar descargas.
     * 
     * .
     */
    @GetMapping("/descargas")
    public ResponseEntity<List<DescargaDto>> listarDescargas() {
        logger.info("Listando todas las descargas...");
        try {
            List<DescargaDto> descargas = consultaServicio.listarDescargas();
            logger.info("Se encontraron {} descargas", descargas.size());
            return ResponseEntity.ok(descargas);
        } catch (Exception e) {
            logger.error("Error al listar descargas: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Endpoint para filtrar datasets en vista usuario.
     * llamado de dataset servicio
     * .
     */
    @GetMapping("/datasets/filtrar")
    public List<DatasetDto> filtrarDatasets(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String formato,
            @RequestParam(required = false) Long idCategoria) {
        logger.info("Filtrando datasets - nombre: {}, formato: {}, idCategoria: {}", nombre, formato, idCategoria);
        return datasetServicio.filtrarDatasets(nombre, formato, idCategoria);
    }


    /**
     * Endpoint para listar datasets en vista usuario.
     * llamado de dataset servicio
     * .
     */
    @GetMapping("/datasets/listarDataset")
    public ResponseEntity<List<DatasetDto>> listarTodos() {
    	logger.info("Listando todos los datasets para usuario...");
        List<DatasetDto> datasets = datasetServicio.listarTodosLosDatasets();
        return ResponseEntity.ok(datasets);
    }
    /**
     * Endpoint para listar categorias en vista usuario.
     * llamado de dataset servicio
     * .
     */
    @GetMapping("datasets/listarCategorias")
    public ResponseEntity<List<Categoria>> listarCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return ResponseEntity.ok(categorias);  // Devuelve la lista de categorías
    }
    /**
     * Endpoint para descargar datasets en vista usuario.
     * llamado de dataset servicio
     * .
     */
    @GetMapping("/datasets/download/{id}")
    public ResponseEntity<Resource> descargarDataset(@PathVariable Long id) {
        logger.info("Descargando dataset con ID: {}", id);
        try {
            Resource archivo = datasetServicio.obtenerArchivoDataset(id);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + archivo.getFilename() + "\"")
                    .body(archivo);
        } catch (Exception e) {
            logger.error("Error al descargar dataset con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }


}
