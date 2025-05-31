package com.estadisticas.estadisticas_app.Modelos;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Representa una categoría dentro del sistema de estadísticas.
 * Cada categoría puede tener múltiples datasets asociados.
 */
@Entity
@Table(name = "categorias", schema = "gestion")
//@JsonIgnoreProperties({"datasets"})  // Ignora la propiedad 'datasets' durante la serialización
public class Categoria {

    /**
     * Identificador único de la categoría.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    /**
     * Nombre descriptivo de la categoría.
     */
    @Column(name = "nombre_categoria", nullable = false, length = 100)
    private String nombreCategoria;

    /**
     * Descripción adicional de la categoría.
     */
    @Column(name = "descripcion_categoria")
    private String descripcionCategoria;

    /**
     * Lista de datasets asociados a esta categoría.
     */
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Dataset> datasets;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Categoria() {}

    /**
     * Constructor que inicializa una categoría con sus atributos principales.
     *
     * @param idCategoria       Identificador único de la categoría.
     * @param nombreCategoria   Nombre de la categoría.
     * @param descripcionCategoria Descripción de la categoría.
     */
    public Categoria(Long idCategoria, String nombreCategoria, String descripcionCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.descripcionCategoria = descripcionCategoria;
    }

    /**
     * Obtiene el identificador único de la categoría.
     *
     * @return id de la categoría.
     */
    public Long getIdCategoria() {
        return idCategoria;
    }

    /**
     * Establece el identificador único de la categoría.
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

    /**
     * Obtiene la lista de datasets asociados a esta categoría.
     *
     * @return lista de datasets.
     */
    public List<Dataset> getDatasets() {
        return datasets;
    }

    /**
     * Establece la lista de datasets asociados a esta categoría.
     *
     * @param datasets lista de datasets.
     */
    public void setDatasets(List<Dataset> datasets) {
        this.datasets = datasets;
    }

    /**
     * Devuelve una representación en cadena de la categoría.
     *
     * @return cadena con los detalles de la categoría.
     */
    @Override
    public String toString() {
        return "Categoria{" +
                "idCategoria=" + idCategoria +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                ", descripcionCategoria='" + descripcionCategoria + '\'' +
                '}';
    }
}