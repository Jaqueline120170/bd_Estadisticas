package com.estadisticas.estadisticas_app.Modelos;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "datasets", schema = "gestion")
public class Dataset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dataset")
    private Long id;

    @Column(name = "nombre_dataset", nullable = false)
    private String nombreDataset;

    @Column(name = "fuente_dataset", nullable = true)
    private String fuenteDataset;

    @Column(name = "descripcion_dataset", nullable = true)
    private String descripcionDataset;

    @Column(name = "archivo_dataset", nullable = true)
    private String archivoUrl;

    @Column(name = "fecha_actualizacion_dataset", nullable = false)
    private LocalDate fechaActualizacionDataset;

    // Relación 1:n con Indicadores
    @OneToMany(mappedBy = "dataset", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Indicador> indicadores;

    // Constructores
    public Dataset() {}

    public Dataset(Long id, String nombreDataset, String fuenteDataset, String descripcionDataset, 
                   String archivoUrl, LocalDate fechaActualizacionDataset) {
        this.id = id;
        this.nombreDataset = nombreDataset;
        this.fuenteDataset = fuenteDataset;
        this.descripcionDataset = descripcionDataset;
        this.archivoUrl = archivoUrl;
        this.fechaActualizacionDataset = fechaActualizacionDataset;
    }

    // Getters y Setters
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

    public String getArchivoUrl() {
        return archivoUrl;
    }

    public void setArchivoUrl(String archivoUrl) {
        this.archivoUrl = archivoUrl;
    }

    public LocalDate getFechaActualizacionDataset() {
        return fechaActualizacionDataset;
    }

    public void setFechaActualizacionDataset(LocalDate fechaActualizacionDataset) {
        this.fechaActualizacionDataset = fechaActualizacionDataset;
    }

    public List<Indicador> getIndicadores() {
        return indicadores;
    }

    public void setIndicadores(List<Indicador> indicadores) {
        this.indicadores = indicadores;
    }

    @Override
    public String toString() {
        return "Dataset [id=" + id + ", nombreDataset=" + nombreDataset + ", fuenteDataset=" + fuenteDataset
                + ", descripcionDataset=" + descripcionDataset + ", archivoUrl=" + archivoUrl
                + ", fechaActualizacionDataset=" + fechaActualizacionDataset + "]";
    }
}
