package com.estadisticas.estadisticas_app.Controladores;

import java.util.List;

import java.util.Map;
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
import com.estadisticas.estadisticas_app.Modelos.Consulta;
import com.estadisticas.estadisticas_app.Modelos.Descarga;
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
    @GetMapping("/datasets/listarCategorias")
    public ResponseEntity<List<Categoria>> listarCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        logger.info("Listando todas las categorías.");
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
    @PostMapping("/registro-consulta")
    public ResponseEntity<?> registrarConsulta(@RequestBody ConsultaDto dto) {
        Consulta nuevaConsulta = consultaServicio.registrarConsulta(dto);
        return ResponseEntity.ok(nuevaConsulta);
    }
    @PostMapping("/registro-descarga")
    public ResponseEntity<Descarga> registrarDescarga(@RequestBody DescargaDto dto) {
        Descarga descarga = datasetServicio.registrarDescarga(dto);
        return ResponseEntity.ok(descarga); // si el frontend no necesita texto, puede dejarlo así

    }

}
