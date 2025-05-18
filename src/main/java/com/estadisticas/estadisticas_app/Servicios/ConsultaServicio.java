package com.estadisticas.estadisticas_app.Servicios;

import com.estadisticas.estadisticas_app.Dtos.ConsultaDto;
import com.estadisticas.estadisticas_app.Dtos.DescargaDto;
import com.estadisticas.estadisticas_app.Modelos.*;
import com.estadisticas.estadisticas_app.Repositorios.ConsultaRepositorio;
import com.estadisticas.estadisticas_app.Repositorios.DatasetRepositorio;
import com.estadisticas.estadisticas_app.Repositorios.DescargaRepositorio;
import com.estadisticas.estadisticas_app.Repositorios.UsuarioRepositorio;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class ConsultaServicio {

    private final ConsultaRepositorio consultaRepository;
    private final UsuarioRepositorio usuarioRepository;
    private final DatasetRepositorio datasetRepository;
    private final DescargaRepositorio descargaRepository;

    public ConsultaServicio(ConsultaRepositorio consultaRepository,
                           UsuarioRepositorio usuarioRepository,
                           DatasetRepositorio datasetRepository,
                           DescargaRepositorio descargaRepository) {
        this.consultaRepository = consultaRepository;
        this.usuarioRepository = usuarioRepository;
        this.datasetRepository = datasetRepository;
        this.descargaRepository = descargaRepository;
    }

    // Convierte Consulta -> ConsultaDTO
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

    // Convierte Descarga -> DescargaDTO
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

    // Crear una nueva Consulta a partir de DTO (simplificado)
    public ConsultaDto crearConsulta(ConsultaDto dto) throws Exception {
        // Busca Usuario y Dataset para asignar
        var usuario = usuarioRepository.findById(dto.getIdUsuario())
            .orElseThrow(() -> new Exception("Usuario no encontrado"));

        var dataset = datasetRepository.findById(dto.getIdDataset())
            .orElseThrow(() -> new Exception("Dataset no encontrado"));

        Consulta consulta = new Consulta();
        consulta.setUsuario(usuario);
        consulta.setDataset(dataset);
        consulta.setFiltros(dto.getFiltros());
        consulta.setFechaConsulta(dto.getFechaConsulta() != null ? dto.getFechaConsulta() : LocalDate.now());

        Consulta guardada = consultaRepository.save(consulta);

        return toDTO(guardada);
    }

    // Obtener Consulta por id
    public ConsultaDto obtenerConsulta(Long id) throws Exception {
        Consulta consulta = consultaRepository.findById(id)
            .orElseThrow(() -> new Exception("Consulta no encontrada"));
        return toDTO(consulta);
    }

    // Crear una nueva Descarga a partir de DTO
    public DescargaDto crearDescarga(DescargaDto dto) throws Exception {
        var usuario = usuarioRepository.findById(dto.getIdUsuario())
            .orElseThrow(() -> new Exception("Usuario no encontrado"));

        var dataset = datasetRepository.findById(dto.getIdDataset())
            .orElseThrow(() -> new Exception("Dataset no encontrado"));

        Consulta consulta = null;
        if(dto.getIdConsulta() != null) {
            consulta = consultaRepository.findById(dto.getIdConsulta())
                .orElseThrow(() -> new Exception("Consulta no encontrada"));
        }

        Descarga descarga = new Descarga();
        descarga.setUsuario(usuario);
        descarga.setDataset(dataset);
        descarga.setConsulta(consulta);
        descarga.setFormato(dto.getFormato());
        descarga.setFechaDescarga(dto.getFechaDescarga() != null ? dto.getFechaDescarga() : LocalDateTime.now());

        Descarga guardada = descargaRepository.save(descarga);

        return toDTO(guardada);
    }

    // Obtener Descarga por id
    public DescargaDto obtenerDescarga(Long id) throws Exception {
        Descarga descarga = descargaRepository.findById(id)
            .orElseThrow(() -> new Exception("Descarga no encontrada"));
        return toDTO(descarga);
    }

    // Listar todas las consultas (opcional)
    public List<ConsultaDto> listarConsultas() {
        return consultaRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    // Listar todas las descargas (opcional)
    public List<DescargaDto> listarDescargas() {
        return descargaRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
}
