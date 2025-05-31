package com.estadisticas.estadisticas_app.Modelos;

import java.time.LocalDateTime;
import jakarta.persistence.*;

/**
 * Representa una descarga realizada por un usuario desde la plataforma.
 * Incluye información sobre el formato, la fecha y el dataset descargado.
 */
@Entity
@Table(name = "descargas", schema = "gestion")
public class Descarga {

    /** Identificador único de la descarga. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_descarga")
    private Long id;

    /** Formato en que se realizó la descarga (CSV, JSON, Excel, etc.). */
    @Column(name = "formato_descarga", nullable = false)
    private String formato;

    /** Fecha y hora en la que se realizó la descarga. */
    @Column(name = "fecha_descarga")
    private LocalDateTime fechaDescarga = LocalDateTime.now();

    /** Usuario que realizó la descarga. */
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    /** Dataset que fue descargado. */
    @ManyToOne
    @JoinColumn(name = "id_dataset", nullable = false)
    private Dataset dataset;

    /** Constructor vacío requerido por JPA. */
    public Descarga() {
        super();
    }

    /**
     * Constructor con todos los atributos.
     *
     * @param id ID de la descarga
     * @param formato Formato de la descarga
     * @param fechaDescarga Fecha y hora de la descarga
     * @param usuario Usuario que realiza la descarga
     * @param dataset Dataset que fue descargado
     */
    public Descarga(Long id, String formato, LocalDateTime fechaDescarga, Usuario usuario, Dataset dataset) {
        super();
        this.id = id;
        this.formato = formato;
        this.fechaDescarga = fechaDescarga;
        this.usuario = usuario;
        this.dataset = dataset;
    }

    /** @return ID de la descarga. */
    public Long getId() {
        return id;
    }

    /** @param id ID de la descarga a establecer. */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return Formato en que se realizó la descarga. */
    public String getFormato() {
        return formato;
    }

    /** @param formato Formato de descarga a establecer. */
    public void setFormato(String formato) {
        this.formato = formato;
    }

    /** @return Fecha y hora de la descarga. */
    public LocalDateTime getFechaDescarga() {
        return fechaDescarga;
    }

    /** @param fechaDescarga Fecha de descarga a establecer. */
    public void setFechaDescarga(LocalDateTime fechaDescarga) {
        this.fechaDescarga = fechaDescarga;
    }

    /** @return Usuario que realizó la descarga. */
    public Usuario getUsuario() {
        return usuario;
    }

    /** @param usuario Usuario a asociar con la descarga. */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /** @return Dataset descargado. */
    public Dataset getDataset() {
        return dataset;
    }

    /** @param dataset Dataset a asociar con la descarga. */
    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }
}
