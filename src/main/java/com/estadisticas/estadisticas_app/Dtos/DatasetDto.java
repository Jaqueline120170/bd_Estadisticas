package com.estadisticas.estadisticas_app.Dtos;

import java.time.LocalDate;

//Este DTO se usa en las respuestas para que los usuarios consulten o visualicen datasets en una tabla, gráfica o lista.
public class DatasetDto {

    private Long idDataset;

    private String nombreDataset;
    private String fuenteDataset;
    private String descripcionDataset;
    private String formatoDataset;
    private LocalDate fechaActualizacionDataset;

    private String nombreCategoria;   // Nombre legible de la categoría
    private String subidoPorNombre;   // Nombre del usuario/admin que lo subió

    // Getters y Setters

    public Long getIdDataset() {
        return idDataset;
    }

    public void setIdDataset(Long idDataset) {
        this.idDataset = idDataset;
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

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getSubidoPorNombre() {
        return subidoPorNombre;
    }

    public void setSubidoPorNombre(String subidoPorNombre) {
        this.subidoPorNombre = subidoPorNombre;
    }
}