package com.estadisticas.estadisticas_app.Dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ConsultaDto {
    private Long idConsulta;
    private Long idUsuario;
    private Long idDataset;
    private String filtros;
    private LocalDate fechaConsulta;
    private List<DescargaDto> descargas;
	public Long getIdConsulta() {
		return idConsulta;
	}
	public void setIdConsulta(Long idConsulta) {
		this.idConsulta = idConsulta;
	}
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
	public LocalDate getFechaConsulta() {
		return fechaConsulta;
	}
	public void setFechaConsulta(LocalDate fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}
	public List<DescargaDto> getDescargas() {
		return descargas;
	}
	public void setDescargas(List<DescargaDto> descargas) {
		this.descargas = descargas;
	}

    // getters y setters
    
}