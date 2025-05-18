package com.estadisticas.estadisticas_app.Modelos;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "consultas", schema = "gestion")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta", updatable = false)
    private Long idConsulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dataset", nullable = false)
    private Dataset dataset;

    @Column(name = "filtros")
    private String filtros;

    @Column(name = "fecha_consulta", nullable = false)
    private LocalDate fechaConsulta;

    // NUEVO: Relación con Descargas
    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Descarga> descargas;

    public Consulta() {}

    public Consulta(Long idConsulta, Usuario usuario, Dataset dataset, String filtros, LocalDate fechaConsulta) {
        this.idConsulta = idConsulta;
        this.usuario = usuario;
        this.dataset = dataset;
        this.filtros = filtros;
        this.fechaConsulta = fechaConsulta;
    }

    // getters y setters de todos los atributos incluyendo descargas

    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public String getFiltros() {
        return filtros;
    }

    public void setFiltros(String filtros) {
        this.filtros = filtros;
    }

    public LocalDate getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(LocalDate fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public List<Descarga> getDescargas() {
        return descargas;
    }

    public void setDescargas(List<Descarga> descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "idConsulta=" + idConsulta +
                ", usuario=" + (usuario != null ? usuario.getIdUsuario() : null) +
                ", dataset=" + (dataset != null ? dataset.getIdDataset() : null) +
                ", filtros='" + filtros + '\'' +
                ", fechaConsulta=" + fechaConsulta +
                '}';
    }
}

