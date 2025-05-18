package com.estadisticas.estadisticas_app.Controladores;

import java.util.List;

import org.springframework.core.io.Resource;
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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/consultas")
public class ConsultaControlador {
	
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
        try {
            ConsultaDto creado = consultaServicio.crearConsulta(consultaDTO);
            return ResponseEntity.ok(creado);
        } catch (Exception e) {
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
        try {
            ConsultaDto consultaDTO = consultaServicio.obtenerConsulta(id);
            return ResponseEntity.ok(consultaDTO);
        } catch (Exception e) {
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
        return ResponseEntity.ok(consultaServicio.listarConsultas());
    }

    /**
     * Endpoint para crear descarga.
     * 
     * .
     */
    @PostMapping("/descargas")
    public ResponseEntity<DescargaDto> crearDescarga(@RequestBody DescargaDto descargaDTO) {
        try {
            DescargaDto creado = consultaServicio.crearDescarga(descargaDTO);
            return ResponseEntity.ok(creado);
        } catch (Exception e) {
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
        return ResponseEntity.ok(consultaServicio.listarDescargas());
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
        return datasetServicio.filtrarDatasets(nombre, formato, idCategoria);
    }

    /**
     * Endpoint para listar datasets en vista usuario.
     * llamado de dataset servicio
     * .
     */
    @GetMapping("/datasets/listarDataset")
    public ResponseEntity<List<DatasetDto>> listarTodos() {
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
        try {
            Resource archivo = datasetServicio.obtenerArchivoDataset(id);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + archivo.getFilename() + "\"")
                    .body(archivo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}
