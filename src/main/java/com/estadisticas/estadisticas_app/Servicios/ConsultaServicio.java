package com.estadisticas.estadisticas_app.Servicios;

import com.estadisticas.estadisticas_app.Dtos.ConsultaDto;
import com.estadisticas.estadisticas_app.Dtos.DescargaDto;
import com.estadisticas.estadisticas_app.Modelos.*;
import com.estadisticas.estadisticas_app.Repositorios.ConsultaRepositorio;
import com.estadisticas.estadisticas_app.Repositorios.DatasetRepositorio;
import com.estadisticas.estadisticas_app.Repositorios.DescargaRepositorio;
import com.estadisticas.estadisticas_app.Repositorios.UsuarioRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
public class ConsultaServicio {
	
    private final ConsultaRepositorio consultaRepository;
    private final UsuarioRepositorio usuarioRepository;
    private final DatasetRepositorio datasetRepository;
    private final DescargaRepositorio descargaRepository;
    private static final Logger logger = LoggerFactory.getLogger(ConsultaServicio.class);


    public ConsultaServicio(ConsultaRepositorio consultaRepository,
                           UsuarioRepositorio usuarioRepository,
                           DatasetRepositorio datasetRepository,
                           DescargaRepositorio descargaRepository) {
        this.consultaRepository = consultaRepository;
        this.usuarioRepository = usuarioRepository;
        this.datasetRepository = datasetRepository;
        this.descargaRepository = descargaRepository;
    }

    /**
     * Convierte una entidad Consulta a su representación ConsultaDto.
     * 
     * @param consulta La entidad Consulta a convertir.
     * @return El objeto ConsultaDto resultante.
     */

    public ConsultaDto toDTO(Consulta consulta) {
        ConsultaDto dto = new ConsultaDto();
        dto.setIdConsulta(consulta.getIdConsulta());
        dto.setIdUsuario(consulta.getUsuario().getIdUsuario());
        dto.setIdDataset(consulta.getDataset().getIdDataset());
        dto.setFiltros(consulta.getFiltros());
        dto.setFechaConsulta(consulta.getFechaConsulta());

        List<DescargaDto> descargasDTO = consulta.getDescargas() != null
            ? consulta.getDescargas().stream()
                .map(this::toDTO)
                .collect(Collectors.toList())
            : null;

        dto.setDescargas(descargasDTO);

        return dto;
    }

    /**
     * Convierte una entidad Descarga a su representación DescargaDto.
     * 
     * @param descarga La entidad Descarga a convertir.
     * @return El objeto DescargaDto resultante.
     */

    public DescargaDto toDTO(Descarga descarga) {
        DescargaDto dto = new DescargaDto();
        dto.setId(descarga.getId());
        dto.setFormato(descarga.getFormato());
        dto.setFechaDescarga(descarga.getFechaDescarga());
        dto.setIdUsuario(descarga.getUsuario().getIdUsuario());
        dto.setIdDataset(descarga.getDataset().getIdDataset());
        dto.setIdConsulta(descarga.getConsulta() != null ? descarga.getConsulta().getIdConsulta() : null);
        return dto;
    }

    /**
     * Crea una nueva consulta a partir del DTO recibido.
     * 
     * @param dto Datos de la consulta.
     * @return La consulta creada como ConsultaDto.
     * @throws Exception Si el usuario o dataset no existen.
     */

    public ConsultaDto crearConsulta(ConsultaDto dto) throws Exception {
        logger.info("Creando consulta para usuario ID: {} y dataset ID: {}", dto.getIdUsuario(), dto.getIdDataset());
        var usuario = usuarioRepository.findById(dto.getIdUsuario())
            .orElseThrow(() -> {
                logger.error("Usuario no encontrado con ID: {}", dto.getIdUsuario());
                return new Exception("Usuario no encontrado con ID: " + dto.getIdUsuario());
            });

        var dataset = datasetRepository.findById(dto.getIdDataset())
            .orElseThrow(() -> {
                logger.error("Dataset no encontrado con ID: {}", dto.getIdDataset());
                return new Exception("Dataset no encontrado con ID: " + dto.getIdDataset());
            });

        Consulta consulta = new Consulta();
        consulta.setUsuario(usuario);
        consulta.setDataset(dataset);
        consulta.setFiltros(dto.getFiltros());
        consulta.setFechaConsulta(dto.getFechaConsulta() != null ? dto.getFechaConsulta() : LocalDate.now());

        Consulta guardada = consultaRepository.save(consulta);
        logger.info("Consulta creada con ID: {}", guardada.getIdConsulta());

        return toDTO(guardada);
    }
    /**
     * Obtiene una consulta por su ID.
     * 
     * @param id ID de la consulta.
     * @return El objeto ConsultaDto correspondiente.
     * @throws Exception Si no se encuentra la consulta.
     */
    public ConsultaDto obtenerConsulta(Long id) throws Exception {
        logger.info("Obteniendo consulta con ID: {}", id);
        Consulta consulta = consultaRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Consulta no encontrada con ID: {}", id);
                return new Exception("Consulta no encontrada con ID: " + id);
            });
        return toDTO(consulta);
    }

    /**
     * Crea una nueva descarga asociada a un usuario, dataset y opcionalmente una consulta.
     * 
     * @param dto Datos de la descarga.
     * @return DescargaDto creado.
     * @throws Exception Si usuario, dataset o consulta (si aplica) no se encuentran.
     */

    public DescargaDto crearDescarga(DescargaDto dto) throws Exception {
        logger.info("Creando descarga para usuario ID: {}, dataset ID: {}, consulta ID: {}", 
            dto.getIdUsuario(), dto.getIdDataset(), dto.getIdConsulta());

        var usuario = usuarioRepository.findById(dto.getIdUsuario())
            .orElseThrow(() -> {
                logger.error("Usuario no encontrado con ID: {}", dto.getIdUsuario());
                return new Exception("Usuario no encontrado con ID: " + dto.getIdUsuario());
            });

        var dataset = datasetRepository.findById(dto.getIdDataset())
            .orElseThrow(() -> {
                logger.error("Dataset no encontrado con ID: {}", dto.getIdDataset());
                return new Exception("Dataset no encontrado con ID: " + dto.getIdDataset());
            });

        Consulta consulta = null;
        if (dto.getIdConsulta() != null) {
            consulta = consultaRepository.findById(dto.getIdConsulta())
                .orElseThrow(() -> {
                    logger.error("Consulta no encontrada con ID: {}", dto.getIdConsulta());
                    return new Exception("Consulta no encontrada con ID: " + dto.getIdConsulta());
                });
        }

        Descarga descarga = new Descarga();
        descarga.setUsuario(usuario);
        descarga.setDataset(dataset);
        descarga.setConsulta(consulta);
        descarga.setFormato(dto.getFormato());
        descarga.setFechaDescarga(dto.getFechaDescarga() != null ? dto.getFechaDescarga() : LocalDateTime.now());

        Descarga guardada = descargaRepository.save(descarga);
        logger.info("Descarga creada con ID: {}", guardada.getId());

        return toDTO(guardada);
    }

    /**
     * Obtiene una descarga por su ID.
     * 
     * @param id ID de la descarga.
     * @return DescargaDto correspondiente.
     * @throws Exception Si no se encuentra la descarga.
     */
    public DescargaDto obtenerDescarga(Long id) throws Exception {
        logger.info("Obteniendo descarga con ID: {}", id);
        Descarga descarga = descargaRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Descarga no encontrada con ID: {}", id);
                return new Exception("Descarga no encontrada con ID: " + id);
            });
        return toDTO(descarga);
    }

    /**
     * Lista todas las consultas registradas en el sistema.
     * 
     * @return Lista de objetos ConsultaDto.
     */
    public List<ConsultaDto> listarConsultas() {
        logger.info("Listando todas las consultas.");
        return consultaRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    /**
     * Lista todas las descargas registradas en el sistema.
     * 
     * @return Lista de objetos DescargaDto.
     */

    public List<DescargaDto> listarDescargas() {
        logger.info("Listando todas las descargas.");
        return descargaRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}
