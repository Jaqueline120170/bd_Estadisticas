package com.estadisticas.estadisticas_app.Dtos;

import java.time.LocalDate;

/**
 * DTO que representa los metadatos recibidos desde el frontend
 * cuando un administrador sube un dataset manualmente.
 */
public class DatasetMetadataDto {

    /**
     * Nombre del dataset.
     */
    private String nombreDataset;

    /**
     * Fuente del dataset.
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
     * Identificador de la categoría asociada al dataset.
     */
    private Long idCategoria;

    /**
     * Identificador del administrador que sube el dataset.
     * Opcional si ya se obtiene del contexto.
     */
    private Long idSubidoPor;

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
     * Obtiene el identificador de la categoría asociada.
     *
     * @return id de la categoría.
     */
    public Long getIdCategoria() {
        return idCategoria;
    }

    /**
     * Establece el identificador de la categoría asociada.
     *
     * @param idCategoria id de la categoría.
     */
    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * Obtiene el identificador del administrador que sube el dataset.
     *
     * @return id del administrador.
     */
    public Long getIdSubidoPor() {
        return idSubidoPor;
    }

    /**
     * Establece el identificador del administrador que sube el dataset.
     *
     * @param idSubidoPor id del administrador.
     */
    public void setIdSubidoPor(Long idSubidoPor) {
        this.idSubidoPor = idSubidoPor;
    }
}
