package com.estadisticas.estadisticas_app.Modelos;

import jakarta.persistence.*;
import java.time.LocalDate;


/**
 * Representa una consulta realizada por un usuario sobre un dataset específico.
 * Contiene información como el usuario que la realizó, el dataset consultado,
 * la fecha de la consulta y los filtros aplicados.
 */
@Entity
@Table(name = "consultas", schema = "gestion")
public class Consulta {

    /** Identificador único de la consulta. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta", updatable = false)
    private Long idConsulta;

    /** Usuario que realizó la consulta. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /** Dataset sobre el cual se realizó la consulta. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dataset", nullable = false)
    private Dataset dataset;

    /** Fecha en que se realizó la consulta. */
    @Column(name = "fecha_consulta", nullable = false)
    private LocalDate fechaConsulta;

    /** Filtros aplicados durante la consulta, almacenados como texto. */
    @Column(name = "filtros", columnDefinition = "TEXT")
    private String filtros;

    /** Constructor vacío requerido por JPA. */
    public Consulta() {}

    /**
     * Constructor completo para inicializar todos los campos de la entidad.
     *
     * @param idConsulta ID único de la consulta
     * @param usuario Usuario que realizó la consulta
     * @param dataset Dataset consultado
     * @param fechaConsulta Fecha de realización de la consulta
     * @param filtros Filtros aplicados durante la consulta
     */
    public Consulta(Long idConsulta, Usuario usuario, Dataset dataset, LocalDate fechaConsulta, String filtros) {
        super();
        this.idConsulta = idConsulta;
        this.usuario = usuario;
        this.dataset = dataset;
        this.fechaConsulta = fechaConsulta;
        this.filtros = filtros;
    }

    /** @return ID de la consulta. */
    public Long getIdConsulta() {
        return idConsulta;
    }

    /** @param idConsulta ID de la consulta a establecer. */
    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    /** @return Usuario que realizó la consulta. */
    public Usuario getUsuario() {
        return usuario;
    }

    /** @param usuario Usuario que realizó la consulta. */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /** @return Dataset consultado. */
    public Dataset getDataset() {
        return dataset;
    }

    /** @param dataset Dataset sobre el cual se realizó la consulta. */
    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    /** @return Fecha de la consulta. */
    public LocalDate getFechaConsulta() {
        return fechaConsulta;
    }

    /** @param fechaConsulta Fecha de la consulta a establecer. */
    public void setFechaConsulta(LocalDate fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    /** @return Filtros aplicados en la consulta. */
    public String getFiltros() {
        return filtros;
    }

    /** @param filtros Filtros a establecer. */
    public void setFiltros(String filtros) {
        this.filtros = filtros;
    }
}

