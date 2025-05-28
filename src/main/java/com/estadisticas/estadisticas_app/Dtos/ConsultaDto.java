package com.estadisticas.estadisticas_app.Dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ConsultaDto {
    
	public class ConsultaDTO {
		
		public Long idUsuario;
	    public Long idDataset;
	    public String filtros; // JSON en texto plano enviado desde el front
	    
	    
	    public Long getIdUsuario() {
			return idUsuario;
		}
		public void setIdUsuario(Long idUsuario) {
			this.idUsuario = idUsuario;
		}
		public Long getIdDataset() {
			return idDataset;
		}
		public void setIdDataset(Long idDataset) {
			this.idDataset = idDataset;
		}
		public String getFiltros() {
			return filtros;
		}
		public void setFiltros(String filtros) {
			this.filtros = filtros;
		}
		
	}

	public Long idDataset;
	public Long idUsuario;
	public String filtros;

	
	
	
	
}