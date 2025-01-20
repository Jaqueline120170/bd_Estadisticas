package com.estadisticas.estadisticas_app.Dtos;

import java.time.LocalDate;


public class DatasetDto {
	
	private Long id;

    private String nombreDataset;

    private String fuenteDataset;

    private String descripcionDataset;

    private LocalDate fechaActualizacionDataset;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreDataset() {
		return nombreDataset;
	}

	public void setNombreDataset(String nombreDataset) {
		this.nombreDataset = nombreDataset;
	}

	public String getFuenteDataset() {
		return fuenteDataset;
	}

	public void setFuenteDataset(String fuenteDataset) {
		this.fuenteDataset = fuenteDataset;
	}

	public String getDescripcionDataset() {
		return descripcionDataset;
	}

	public void setDescripcionDataset(String descripcionDataset) {
		this.descripcionDataset = descripcionDataset;
	}

	public LocalDate getFechaActualizacionDataset() {
		return fechaActualizacionDataset;
	}

	public void setFechaActualizacionDataset(LocalDate fechaActualizacionDataset) {
		this.fechaActualizacionDataset = fechaActualizacionDataset;
	}
    
    
}
