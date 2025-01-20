package com.estadisticas.estadisticas_app.Modelos;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "indicadores", schema = "gestion")
public class Indicador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_indicador", updatable = false)
    private Long id;

    @Column(name = "nombre_indicador", nullable = false)
    private String nombre;

    @Column(name = "unidad_medida")
    private String unidadMedida;

    // Relación n:1 con Dataset
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dataset", nullable = false)
    private Dataset dataset;
    
    // Relación n:m con Consultas
    @ManyToMany(mappedBy = "indicadores", fetch = FetchType.LAZY)
    private List<Consulta> consultas;

    public Indicador() {}

    public Indicador(Long id, String nombre, String unidadMedida, Dataset dataset) {
        this.id = id;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.dataset = dataset;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    @Override
    public String toString() {
        return "Indicador [id=" + id + ", nombre=" + nombre + ", unidadMedida=" + unidadMedida + "]";
    }
}
