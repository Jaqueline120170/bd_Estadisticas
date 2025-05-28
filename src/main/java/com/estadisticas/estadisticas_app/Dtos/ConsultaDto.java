package com.estadisticas.estadisticas_app.Dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ConsultaDto {
    
	
		

	public Long idUsuario;
	public Long idDataset;
	public Map<String, Object> filtros;
	
	
	
	public Long getIdUsuario() {
		
		return idUsuario;
	}
	public Long getIdDataset() {
		
		return idDataset;
	}
	public Object getFiltros() {
		
		return filtros;
	}

	}
