package com.estadisticas.estadisticas_app.Modelos;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representa un conjunto de datos disponible en la plataforma.
 * Contiene información como nombre, fuente, descripción, formato, archivo,
 * así como referencias a su categoría, usuario que lo subió y sus descargas asociadas.
 */
@Entity
@Table(name = "datasets", schema = "gestion")
@JsonIgnoreProperties({"categoria"})  // Ignora la propiedad 'categoria' durante la serialización
public class Dataset {

    /** Identificador único del dataset. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dataset")
    private Long idDataset;

    /** Nombre descriptivo del dataset. */
    @Column(name = "nombre_dataset", nullable = false)
    private String nombreDataset;

    /** Fuente u origen del dataset. */
    @Column(name = "fuente_dataset")
    private String fuenteDataset;

    /** Descripción detallada del contenido del dataset. */
    @Column(name = "descripcion_dataset")
    private String descripcionDataset;

    /** Ruta o nombre del archivo del dataset. */
    @Column(name = "archivo_dataset")
    private String archivoDataset;

    /** Formato del archivo (ej. CSV, JSON, XLSX). */
    @Column(name = "formato_dataset", columnDefinition = "varchar")
    private String formatoDataset;

    /** Fecha de la última actualización del dataset. */
    @Column(name = "fecha_actualizacion_dataset", nullable = false)
    private LocalDate fechaActualizacionDataset;

    /** Usuario que subió el dataset. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subido_por")
    private Usuario subidoPor;

    /** Categoría a la que pertenece el dataset. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    /** Lista de descargas asociadas al dataset. */
    @OneToMany(mappedBy = "dataset", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Descarga> descargas;

    /** Constructor vacío requerido por JPA. */
    public Dataset() {}

    /**
     * Constructor completo para inicializar todas las propiedades del dataset.
     *
     * @param idDataset ID del dataset
     * @param nombreDataset Nombre del dataset
     * @param fuenteDataset Fuente de origen
     * @param descripcionDataset Descripción del contenido
     * @param archivoDataset Nombre o ruta del archivo
     * @param formatoDataset Formato del archivo
     * @param fechaActualizacionDataset Fecha de última actualización
     * @param subidoPor Usuario que lo subió
     * @param descargas Lista de descargas asociadas
     * @param categoria Categoría del dataset
     */
    public Dataset(Long idDataset, String nombreDataset, String fuenteDataset, String descripcionDataset,
                   String archivoDataset, String formatoDataset, LocalDate fechaActualizacionDataset, Usuario subidoPor,
                   List<Descarga> descargas, Categoria categoria) {
        super();
        this.idDataset = idDataset;
        this.nombreDataset = nombreDataset;
        this.fuenteDataset = fuenteDataset;
        this.descripcionDataset = descripcionDataset;
        this.archivoDataset = archivoDataset;
        this.formatoDataset = formatoDataset;
        this.fechaActualizacionDataset = fechaActualizacionDataset;
        this.subidoPor = subidoPor;
        this.descargas = descargas;
        this.categoria = categoria;
    }

    /** @return ID del dataset. */
    public Long getIdDataset() {
        return idDataset;
    }

    /** @param idDataset ID a establecer. */
    public void setIdDataset(Long idDataset) {
        this.idDataset = idDataset;
    }

    /** @return Nombre del dataset. */
    public String getNombreDataset() {
        return nombreDataset;
    }

    /** @param nombreDataset Nombre a establecer. */
    public void setNombreDataset(String nombreDataset) {
        this.nombreDataset = nombreDataset;
    }

    /** @return Fuente del dataset. */
    public String getFuenteDataset() {
        return fuenteDataset;
    }

    /** @param fuenteDataset Fuente a establecer. */
    public void setFuenteDataset(String fuenteDataset) {
        this.fuenteDataset = fuenteDataset;
    }

    /** @return Descripción del dataset. */
    public String getDescripcionDataset() {
        return descripcionDataset;
    }

    /** @param descripcionDataset Descripción a establecer. */
    public void setDescripcionDataset(String descripcionDataset) {
        this.descripcionDataset = descripcionDataset;
    }

    /** @return Archivo del dataset. */
    public String getArchivoDataset() {
        return archivoDataset;
    }

    /** @param archivoDataset Nombre o ruta del archivo a establecer. */
    public void setArchivoDataset(String archivoDataset) {
        this.archivoDataset = archivoDataset;
    }

    /** @return Formato del dataset. */
    public String getFormatoDataset() {
        return formatoDataset;
    }

    /** @param formatoDataset Formato a establecer. */
    public void setFormatoDataset(String formatoDataset) {
        this.formatoDataset = formatoDataset;
    }

    /** @return Fecha de última actualización del dataset. */
    public LocalDate getFechaActualizacionDataset() {
        return fechaActualizacionDataset;
    }

    /** @param fechaActualizacionDataset Fecha de actualización a establecer. */
    public void setFechaActualizacionDataset(LocalDate fechaActualizacionDataset) {
        this.fechaActualizacionDataset = fechaActualizacionDataset;
    }

    /** @return Usuario que subió el dataset. */
    public Usuario getSubidoPor() {
        return subidoPor;
    }

    /** @param subidoPor Usuario a establecer como autor del dataset. */
    public void setSubidoPor(Usuario subidoPor) {
        this.subidoPor = subidoPor;
    }

    /** @return Categoría a la que pertenece el dataset. */
    public Categoria getCategoria() {
        return categoria;
    }

    /** @param categoria Categoría a establecer. */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /** @return Lista de descargas asociadas al dataset. */
    public List<Descarga> getDescargas() {
        return descargas;
    }

    /** @param descargas Lista de descargas a establecer. */
    public void setDescargas(List<Descarga> descargas) {
        this.descargas = descargas;
    }

    /** @return Representación en forma de cadena del dataset. */
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