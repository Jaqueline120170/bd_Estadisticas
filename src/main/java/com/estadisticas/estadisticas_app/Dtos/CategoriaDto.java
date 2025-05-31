package com.estadisticas.estadisticas_app.Dtos;

import com.estadisticas.estadisticas_app.Modelos.Categoria;

/**
 * DTO que representa la información de una categoría,
 * generalmente usada para transferir datos de la entidad Categoria.
 */
public class CategoriaDto {

    /**
     * Identificador único de la categoría.
     */
    private Long idCategoria;

    /**
     * Nombre de la categoría.
     */
    private String nombreCategoria;

    /**
     * Descripción de la categoría.
     */
    private String descripcionCategoria;

    /**
     * Constructor que inicializa el DTO a partir de una entidad Categoria.
     *
     * @param categoria entidad Categoria de la cual extraer los datos.
     */
    public CategoriaDto(Categoria categoria) {
        this.idCategoria = categoria.getIdCategoria();
        this.nombreCategoria = categoria.getNombreCategoria();
        this.descripcionCategoria = categoria.getDescripcionCategoria();
    }

    /**
     * Obtiene el identificador de la categoría.
     *
     * @return id de la categoría.
     */
    public Long getIdCategoria() {
        return idCategoria;
    }

    /**
     * Establece el identificador de la categoría.
     *
     * @param idCategoria id de la categoría.
     */
    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * Obtiene el nombre de la categoría.
     *
     * @return nombre de la categoría.
     */
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    /**
     * Establece el nombre de la categoría.
     *
     * @param nombreCategoria nombre de la categoría.
     */
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    /**
     * Obtiene la descripción de la categoría.
     *
     * @return descripción de la categoría.
     */
    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    /**
     * Establece la descripción de la categoría.
     *
     * @param descripcionCategoria descripción de la categoría.
     */
    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }
}
