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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

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


    public Consulta registrarConsulta(ConsultaDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Dataset dataset = datasetRepository.findById(dto.idDataset)
                .orElseThrow(() -> new RuntimeException("Dataset no encontrado"));

        Consulta consulta = new Consulta();
        consulta.setUsuario(usuario);
        consulta.setDataset(dataset);
        consulta.setFechaConsulta(LocalDate.now());
        consulta.setFiltros(dto.filtros);

        return consultaRepository.save(consulta);
        
    }
   
}
