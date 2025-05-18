package com.estadisticas.estadisticas_app.Modelos;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "descargas", schema = "gestion")
public class Descarga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_descarga")
    private Long id;

    @Column(name = "formato_descarga", nullable = false)
    private String formato; // CSV, JSON, Excel...

    @Column(name = "fecha_descarga")
    private LocalDateTime fechaDescarga = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_dataset")
    private Dataset dataset;

    // NUEVO: relación a Consulta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_consulta")
    private Consulta consulta;

    public Descarga() {
        super();
    }

    public Descarga(Long id, String formato, LocalDateTime fechaDescarga, Usuario usuario, Dataset dataset, Consulta consulta) {
        super();
        this.id = id;
        this.formato = formato;
        this.fechaDescarga = fechaDescarga;
        this.usuario = usuario;
        this.dataset = dataset;
        this.consulta = consulta;
    }

    // getters y setters incluyendo consulta

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public LocalDateTime getFechaDescarga() {
        return fechaDescarga;
    }

    public void setFechaDescarga(LocalDateTime fechaDescarga) {
        this.fechaDescarga = fechaDescarga;
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

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
}

