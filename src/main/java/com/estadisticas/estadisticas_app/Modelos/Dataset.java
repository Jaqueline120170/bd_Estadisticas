package com.estadisticas.estadisticas_app.Modelos;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "datasets", schema = "gestion")
@JsonIgnoreProperties({"categoria"})  // Ignora la propiedad 'categoria' durante la serialización
public class Dataset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dataset")
    private Long idDataset;

    @Column(name = "nombre_dataset", nullable = false)
    private String nombreDataset;

    @Column(name = "fuente_dataset")
    private String fuenteDataset;

    @Column(name = "descripcion_dataset")
    private String descripcionDataset;

    @Column(name = "archivo_dataset")
    private String archivoDataset;

    @Column(name = "formato_dataset", columnDefinition = "varchar")
    private String formatoDataset;

    @Column(name = "fecha_actualizacion_dataset", nullable = false)
    private LocalDate fechaActualizacionDataset;

    // Relación con Usuario que lo subió
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subido_por")
    private Usuario subidoPor;

    // Relación con Categoría
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    // Constructor vacío
    public Dataset() {}

    // Constructor completo
    public Dataset(Long idDataset, String nombreDataset, String fuenteDataset, String descripcionDataset,
                   String archivoDataset, String formatoDataset, LocalDate fechaActualizacionDataset,
                   Usuario subidoPor, Categoria categoria) {
        this.idDataset = idDataset;
        this.nombreDataset = nombreDataset;
        this.fuenteDataset = fuenteDataset;
        this.descripcionDataset = descripcionDataset;
        this.archivoDataset = archivoDataset;
        this.formatoDataset = formatoDataset;
        this.fechaActualizacionDataset = fechaActualizacionDataset;
        this.subidoPor = subidoPor;
        this.categoria = categoria;
    }

    // Getters y setters
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

    public String getArchivoDataset() {
        return archivoDataset;
    }

    public void setArchivoDataset(String archivoDataset) {
        this.archivoDataset = archivoDataset;
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

    public Usuario getSubidoPor() {
        return subidoPor;
    }

    public void setSubidoPor(Usuario subidoPor) {
        this.subidoPor = subidoPor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Dataset{" +
                "idDataset=" + idDataset +
                ", nombreDataset='" + nombreDataset + '\'' +
                ", fuenteDataset='" + fuenteDataset + '\'' +
                ", descripcionDataset='" + descripcionDataset + '\'' +
                ", archivoDataset='" + archivoDataset + '\'' +
                ", formatoDataset='" + formatoDataset + '\'' +
                ", fechaActualizacionDataset=" + fechaActualizacionDataset +
                '}';
    }
}

