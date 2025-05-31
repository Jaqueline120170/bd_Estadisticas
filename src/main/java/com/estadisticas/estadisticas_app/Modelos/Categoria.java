package com.estadisticas.estadisticas_app.Modelos;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "categorias", schema = "gestion")
//@JsonIgnoreProperties({"datasets"})  // Ignora la propiedad 'datasets' durante la serialización
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "nombre_categoria", nullable = false, length = 100)
    private String nombreCategoria;

    @Column(name = "descripcion_categoria")
    private String descripcionCategoria;

    // Relación con datasets
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Dataset> datasets;

    // Constructor vacío
    public Categoria() {}

    // Constructor completo
    public Categoria(Long idCategoria, String nombreCategoria, String descripcionCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.descripcionCategoria = descripcionCategoria;
    }

    // Getters y Setters
    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }

    public List<Dataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<Dataset> datasets) {
        this.datasets = datasets;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "idCategoria=" + idCategoria +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                ", descripcionCategoria='" + descripcionCategoria + '\'' +
                '}';
    }
}
