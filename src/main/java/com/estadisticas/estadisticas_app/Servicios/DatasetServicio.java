package com.estadisticas.estadisticas_app.Servicios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.estadisticas.estadisticas_app.Dtos.DatasetDto;
import com.estadisticas.estadisticas_app.Dtos.DatasetMetadataDto;
import com.estadisticas.estadisticas_app.Dtos.DescargaDto;
import com.estadisticas.estadisticas_app.Modelos.Categoria;
import com.estadisticas.estadisticas_app.Modelos.Dataset;
import com.estadisticas.estadisticas_app.Modelos.Descarga;
import com.estadisticas.estadisticas_app.Modelos.Usuario;
import com.estadisticas.estadisticas_app.Repositorios.CategoriaRepositorio;
import com.estadisticas.estadisticas_app.Repositorios.DatasetRepositorio;
import com.estadisticas.estadisticas_app.Repositorios.DescargaRepositorio;
import com.estadisticas.estadisticas_app.Repositorios.UsuarioRepositorio;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
/**
 * Servicio que contiene la lógica de negocio para la gestión de datasets,
 * incluyendo subida, listado, filtrado, eliminación y descarga de archivos,
 * así como la administración de categorías.
 */

@Service
public class DatasetServicio {
	
	 private static final Logger logger = LoggerFactory.getLogger(AdministradorServicio.class);
	    
	 	
	    @Value("${dataset.upload.dir}")
	    private String uploadDir;

	    @PostConstruct
	    public void init() {
	        // Convierte a ruta absoluta si hace falta
	        uploadDir = Paths.get(System.getProperty("user.dir"), uploadDir).toString();
	    }

	    @Autowired
	    private UsuarioRepositorio usuarioRepository;
	    @Autowired
	    private CategoriaRepositorio categoriaRepository;
	    @Autowired
	    private DatasetRepositorio datasetRepository;
	    @Autowired
	    private DescargaRepositorio descargaRepository;

	
	   @Transactional
	   /**
	    * Sube un nuevo dataset proporcionado por un administrador.
	    *
	    * @param metadata Metadatos del dataset (nombre, formato, descripción, etc.).
	    * @param archivo Archivo físico del dataset.
	    * @param idAdmin ID del administrador que sube el archivo.
	    * @return Dataset creado y almacenado.
	    * @throws IOException Si ocurre un error al guardar el archivo.
	    * @throws ExcepcionNegocio Si el usuario o la categoría no existen, o el formato es inválido.
	    */

	    public Dataset subirDataset(DatasetMetadataDto metadata, MultipartFile archivo, Long idAdmin) throws IOException {

	        // 1. Verifica que el usuario exista
	        Usuario admin = usuarioRepository.findById(idAdmin)
	                .orElseThrow(() -> new IllegalArgumentException("Usuario administrador no encontrado."));

	        // 2. Verifica que la categoría exista
	        Categoria categoria = categoriaRepository.findById(metadata.getIdCategoria())
	                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada."));
	        
	        // 3. Validar que la extensión del archivo coincida con el formato declarado
	        String formatoEsperado = metadata.getFormatoDataset().toLowerCase();
	        String extensionArchivo = obtenerExtensionArchivo(archivo.getOriginalFilename()).toLowerCase();

	        Map<String, List<String>> formatosValidos = Map.of(
	            "csv", List.of("csv"),
	            "json", List.of("json"),
	            "xlsx", List.of("xlsx")
	        );

	        if (!formatosValidos.getOrDefault(formatoEsperado, List.of()).contains(extensionArchivo)) {
	            throw new IllegalArgumentException("El archivo no corresponde al formato declarado: " + metadata.getFormatoDataset());
	        }

	        // 4. Genera nombre único para el archivo
	        String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();

	        // 5. Asegura que el directorio exista
	        java.nio.file.Path uploadPath = Paths.get(uploadDir);
	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	            logger.info("Directorio creado: {}", uploadPath.toAbsolutePath());
	        }

	        // 6. Guarda el archivo físicamente en disco
	        java.nio.file.Path rutaArchivo = uploadPath.resolve(nombreArchivo);
	        Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
	        logger.info("Archivo guardado en: {}", rutaArchivo.toAbsolutePath());

	        // 7. Crea y guarda el dataset
	        Dataset dataset = new Dataset();
	        dataset.setNombreDataset(metadata.getNombreDataset());
	        dataset.setFuenteDataset(metadata.getFuenteDataset());
	        dataset.setDescripcionDataset(metadata.getDescripcionDataset());
	        dataset.setFormatoDataset(metadata.getFormatoDataset());
	        dataset.setFechaActualizacionDataset(LocalDate.now());
	        dataset.setArchivoDataset(nombreArchivo); // Solo guardamos el nombre del archivo
	        dataset.setCategoria(categoria);
	        dataset.setSubidoPor(admin);

	        // 8. Guardar en BD
	        return datasetRepository.save(dataset);
	    }
	   
	   @Transactional
	   /**
	    * Lista todos los datasets disponibles en el sistema, convertidos a DTO.
	    *
	    * @return Lista de objetos DatasetDto con datos resumidos.
	    */

	    public List<DatasetDto> listarTodosLosDatasets() {
	        List<Dataset> datasets = datasetRepository.findAll();

	        return datasets.stream().map(dataset -> {
	            DatasetDto dto = new DatasetDto();
	            dto.setIdDataset(dataset.getIdDataset());
	            dto.setNombreDataset(dataset.getNombreDataset());
	            dto.setFuenteDataset(dataset.getFuenteDataset());
	            dto.setDescripcionDataset(dataset.getDescripcionDataset());
	            dto.setFormatoDataset(dataset.getFormatoDataset());
	            dto.setFechaActualizacionDataset(dataset.getFechaActualizacionDataset());

	            // Accedemos con precaución a relaciones que podrían ser lazy
	            if (dataset.getCategoria() != null) {
	                dto.setNombreCategoria(dataset.getCategoria().getNombreCategoria());
	            }

	            if (dataset.getSubidoPor() != null) {
	                dto.setSubidoPorNombre(dataset.getSubidoPor().getNombreUsuario());
	            }

	            return dto;
	        }).collect(Collectors.toList());
	    }
	 // Método auxiliar para obtener la extensión del archivo
	    private String obtenerExtensionArchivo(String nombreArchivo) {
	        if (nombreArchivo == null || !nombreArchivo.contains(".")) return "";
	        return nombreArchivo.substring(nombreArchivo.lastIndexOf('.') + 1);
	    }
	    @Transactional
	    /**
	     * Filtra datasets en base a nombre, formato o categoría.
	     *
	     * @param nombre Nombre parcial o completo del dataset.
	     * @param formato Formato del dataset (csv, json, xlsx).
	     * @param idCategoria ID de la categoría deseada.
	     * @return Lista de datasets que cumplen con los filtros.
	     */

	    public List<DatasetDto> filtrarDatasets(String nombre, String formato, Long idCategoria) {
	        List<Dataset> resultados;

	        if (nombre == null && formato == null && idCategoria == null) {
	            resultados = datasetRepository.findAll(); // sin filtros
	        } else {
	            resultados = datasetRepository.findByFiltros(
	                    nombre != null ? "%" + nombre + "%" : null,
	                    formato,
	                    idCategoria
	            );
	        }

	        return resultados.stream()
	                .map(this::convertirADto)
	                .toList();
	    }

	    private DatasetDto convertirADto(Dataset dataset) {
	        DatasetDto dto = new DatasetDto();
	        dto.setIdDataset(dataset.getIdDataset());
	        dto.setNombreDataset(dataset.getNombreDataset());
	        dto.setFuenteDataset(dataset.getFuenteDataset());
	        dto.setDescripcionDataset(dataset.getDescripcionDataset());
	        dto.setFormatoDataset(dataset.getFormatoDataset());
	        dto.setFechaActualizacionDataset(dataset.getFechaActualizacionDataset());

	        if (dataset.getCategoria() != null) {
	            dto.setNombreCategoria(dataset.getCategoria().getNombreCategoria());
	        }

	        if (dataset.getSubidoPor() != null) {
	            dto.setSubidoPorNombre(dataset.getSubidoPor().getNombreUsuario());
	        }

	        return dto;
	    }
	    
	    /**
	     * Elimina un dataset, tanto de la base de datos como del sistema de archivos.
	     *
	     * @param id ID del dataset a eliminar.
	     * @return true si el dataset fue eliminado, false si no se encontró.
	     */
	    @Transactional
	    public boolean eliminarDataset(Long id) {
	        return datasetRepository.findById(id).map(dataset -> {
	            // Eliminar el archivo físico del disco
	            Path archivoPath = Paths.get(uploadDir).resolve(dataset.getArchivoDataset());
	            try {
	            	logger.info("Intentando eliminar dataset con ID: {}", id);
	                Files.deleteIfExists(archivoPath);  // 🔥 Elimina el archivo físico si existe
	            } catch (IOException e) {
	                logger.error("Error al eliminar el archivo del dataset: {}", archivoPath, e);
	            }

	            // Eliminar el dataset de la base de datos
	            datasetRepository.deleteById(id);
	            return true;
	        }).orElse(false);
	    }

	    /**
	     * Obtiene el archivo físico de un dataset para descarga.
	     *
	     * @param id ID del dataset.
	     * @return Archivo como recurso si existe y es legible.
	     * @throws Exception Si el dataset no existe o el archivo no es legible.
	     */
	    @Transactional
	    public Resource obtenerArchivoDataset(Long id) throws Exception {
	    	
	        Dataset dataset = datasetRepository.findById(id)
	            .orElseThrow(() -> new Exception("Dataset no encontrado"));

	        Path path = Paths.get(uploadDir).resolve(dataset.getArchivoDataset());
	        Resource resource = new UrlResource(path.toUri());

	        if (resource.exists() || resource.isReadable()) {
	            return resource;
	        } else {
	            throw new Exception("No se puede leer el archivo");
	        }
	    }
	    /**
	     * Crea una nueva categoría si no existe ya una con el mismo nombre.
	     *
	     * @param categoria Objeto con el nombre de la categoría.
	     * @return Categoría guardada.
	     * @throws ExcepcionNegocio Si el nombre está vacío o ya existe la categoría.
	     */

	    @Transactional
	    public Categoria crearCategoria(Categoria categoria) {
	    	logger.info("Intentando crear categoría: {}", categoria.getNombreCategoria());
	        if (categoria.getNombreCategoria() == null || categoria.getNombreCategoria().trim().isEmpty()) {
	        	logger.warn("Nombre de categoría vacío o nulo");
	            throw new IllegalArgumentException("El nombre de la categoría es obligatorio.");
	        }

	        if (categoriaRepository.existsByNombreCategoria(categoria.getNombreCategoria())) {
	        	logger.warn("Ya existe una categoría con el nombre: {}", categoria.getNombreCategoria());
	            throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");
	        }

	        categoria.setIdCategoria(null); // fuerza creación de nuevo ID
	        return categoriaRepository.save(categoria);
	    }
	    /**
	     *Obtiene el conteo de datasets por categoría 
	     *
	     */

	    @Transactional
	    public Map<String, Long> obtenerConteoDatasetsPorCategoria() {
	        List<Object[]> resultados = datasetRepository.contarDatasetsPorCategoria();
	        Map<String, Long> conteoPorCategoria = new LinkedHashMap<>();

	        for (Object[] fila : resultados) {
	            String categoria = (String) fila[0];
	            Long total = (Long) fila[1];
	            conteoPorCategoria.put(categoria, total);
	        }
	        return conteoPorCategoria;
	    }
	    /**
	     *Registra una descarga en la BBDD
	     *
	     */

	    @Transactional
	    public Descarga registrarDescarga(DescargaDto dto) {
	        Usuario usuario = usuarioRepository.findById(dto.idUsuario)
	                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	        Dataset dataset = datasetRepository.findById(dto.idDataset)
	                .orElseThrow(() -> new RuntimeException("Dataset no encontrado"));

	        Descarga descarga = new Descarga();
	        descarga.setUsuario(usuario);
	        descarga.setDataset(dataset);
	        descarga.setFormato(dto.formato);
	        descarga.setFechaDescarga(LocalDateTime.now());


	        return descargaRepository.save(descarga);
	    }

}
