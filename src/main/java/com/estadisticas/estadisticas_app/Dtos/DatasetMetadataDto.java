package com.estadisticas.estadisticas_app.Dtos;

import java.time.LocalDate;

//Este es el DTO que recibirás desde el frontend cuando el admin sube un dataset manualmente:
public class DatasetMetadataDto {

    private String nombreDataset;
    private String fuenteDataset;
    private String descripcionDataset;
    private String formatoDataset;         // CSV, JSON, XLS, etc.
    private LocalDate fechaActualizacionDataset;
    private Long idCategoria;              // Relación con la categoría seleccionada
    private Long idSubidoPor;              // ID del admin que lo sube (opcional si ya lo tienes del contexto)

    // Getters y Setters
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

    public String getFormatoDataset() {
        return formatoDataset;
    }

    public void setFormatoDataset(String formatoDataset) {
        this.formatoDataset = formatoDataset;
    }

    public LocalDate getFechaActualizacionDataset() {
        return fechaActualizacionDataset;
    }

    public void setFechaActualizacionDataset(LocalDate fechaActualizacionDataset) {
        this.fechaActualizacionDataset = fechaActualizacionDataset;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Long getIdSubidoPor() {
        return idSubidoPor;
    }

    public void setIdSubidoPor(Long idSubidoPor) {
        this.idSubidoPor = idSubidoPor;
    }
}
