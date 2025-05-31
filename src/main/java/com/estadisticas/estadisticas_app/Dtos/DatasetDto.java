package com.estadisticas.estadisticas_app.Dtos;

import java.time.LocalDate;

/**
 * DTO usado en las respuestas para que los usuarios puedan consultar
 * o visualizar datasets en diferentes formatos como tabla, gráfica o lista.
 */
public class DatasetDto {

    /**
     * Identificador único del dataset.
     */
    private Long idDataset;

    /**
     * Nombre del dataset.
     */
    private String nombreDataset;

    /**
     * Fuente de donde proviene el dataset.
     */
    private String fuenteDataset;

    /**
     * Descripción del dataset.
     */
    private String descripcionDataset;

    /**
     * Formato del dataset (CSV, JSON, XLS, etc.).
     */
    private String formatoDataset;

    /**
     * Fecha de la última actualización del dataset.
     */
    private LocalDate fechaActualizacionDataset;

    /**
     * Nombre legible de la categoría a la que pertenece el dataset.
     */
    private String nombreCategoria;

    /**
     * Nombre del usuario o administrador que subió el dataset.
     */
    private String subidoPorNombre;

    /**
     * Obtiene el identificador del dataset.
     *
     * @return id del dataset.
     */
    public Long getIdDataset() {
        return idDataset;
    }

    /**
     * Establece el identificador del dataset.
     *
     * @param idDataset id del dataset.
     */
    public void setIdDataset(Long idDataset) {
        this.idDataset = idDataset;
    }

    /**
     * Obtiene el nombre del dataset.
     *
     * @return nombre del dataset.
     */
    public String getNombreDataset() {
        return nombreDataset;
    }

    /**
     * Establece el nombre del dataset.
     *
     * @param nombreDataset nombre del dataset.
     */
    public void setNombreDataset(String nombreDataset) {
        this.nombreDataset = nombreDataset;
    }

    /**
     * Obtiene la fuente del dataset.
     *
     * @return fuente del dataset.
     */
    public String getFuenteDataset() {
        return fuenteDataset;
    }

    /**
     * Establece la fuente del dataset.
     *
     * @param fuenteDataset fuente del dataset.
     */
    public void setFuenteDataset(String fuenteDataset) {
        this.fuenteDataset = fuenteDataset;
    }

    /**
     * Obtiene la descripción del dataset.
     *
     * @return descripción del dataset.
     */
    public String getDescripcionDataset() {
        return descripcionDataset;
    }

    /**
     * Establece la descripción del dataset.
     *
     * @param descripcionDataset descripción del dataset.
     */
    public void setDescripcionDataset(String descripcionDataset) {
        this.descripcionDataset = descripcionDataset;
    }

    /**
     * Obtiene el formato del dataset.
     *
     * @return formato del dataset.
     */
    public String getFormatoDataset() {
        return formatoDataset;
    }

    /**
     * Establece el formato del dataset.
     *
     * @param formatoDataset formato del dataset.
     */
    public void setFormatoDataset(String formatoDataset) {
        this.formatoDataset = formatoDataset;
    }

    /**
     * Obtiene la fecha de última actualización del dataset.
     *
     * @return fecha de actualización.
     */
    public LocalDate getFechaActualizacionDataset() {
        return fechaActualizacionDataset;
    }

    /**
     * Establece la fecha de última actualización del dataset.
     *
     * @param fechaActualizacionDataset fecha de actualización.
     */
    public void setFechaActualizacionDataset(LocalDate fechaActualizacionDataset) {
        this.fechaActualizacionDataset = fechaActualizacionDataset;
    }

    /**
     * Obtiene el nombre legible de la categoría.
     *
     * @return nombre de la categoría.
     */
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    /**
     * Establece el nombre legible de la categoría.
     *
     * @param nombreCategoria nombre de la categoría.
     */
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    /**
     * Obtiene el nombre del usuario o admin que subió el dataset.
     *
     * @return nombre del usuario/admin.
     */
    public String getSubidoPorNombre() {
        return subidoPorNombre;
    }

    /**
     * Establece el nombre del usuario o admin que subió el dataset.
     *
     * @param subidoPorNombre nombre del usuario/admin.
     */
    public void setSubidoPorNombre(String subidoPorNombre) {
        this.subidoPorNombre = subidoPorNombre;
    }
}