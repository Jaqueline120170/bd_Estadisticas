package com.estadisticas.estadisticas_app.Servicios;

import com.estadisticas.estadisticas_app.Dtos.ConsultaDto;
import com.estadisticas.estadisticas_app.Modelos.*;
import com.estadisticas.estadisticas_app.Repositorios.ConsultaRepositorio;
import com.estadisticas.estadisticas_app.Repositorios.DatasetRepositorio;
import com.estadisticas.estadisticas_app.Repositorios.UsuarioRepositorio;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
@Service
@Transactional
public class ConsultaServicio {
	
	   @Autowired
	    private UsuarioRepositorio usuarioRepository;

	    @Autowired
	    private DatasetRepositorio datasetRepository;

	    @Autowired
	    private ConsultaRepositorio consultaRepository;
	    
    private static final Logger logger = LoggerFactory.getLogger(ConsultaServicio.class);


    /**
     * Registra una consulta realizada en el front y la guarda en la BBDD
     */
    public Consulta registrarConsulta(ConsultaDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Dataset dataset = datasetRepository.findById(dto.getIdDataset())
                .orElseThrow(() -> new RuntimeException("Dataset no encontrado"));

        Consulta consulta = new Consulta();
        consulta.setUsuario(usuario);
        consulta.setDataset(dataset);
        consulta.setFechaConsulta(LocalDate.now());

        // Convertir el Map en JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            consulta.setFiltros(objectMapper.writeValueAsString(dto.getFiltros()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir filtros a JSON", e);
        }

        return consultaRepository.save(consulta);
    }

   
}
