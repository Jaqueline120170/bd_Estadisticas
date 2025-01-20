package com.estadisticas.estadisticas_app.Modelos;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "consultas", schema = "gestion")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta", updatable = false)
    private Long idConsulta;

    // Relación n:1 con Usuario
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // Relación n:m con Indicadores
    @ManyToMany
    @JoinTable(
        name = "consulta_indicador",
        schema = "gestion",
        joinColumns = @JoinColumn(name = "id_consulta"),
        inverseJoinColumns = @JoinColumn(name = "id_indicador")
    )
    private List<Indicador> indicadores;

    // Parámetros adicionales para la consulta
    @Column(name = "parametros_consulta", nullable = true)
    private String parametros;

    // Fecha en la que se realizó la consulta
    @Column(name = "fecha_consulta", nullable = false)
    private LocalDate fechaConsulta;

    // Constructores
    public Consulta() {}

    public Consulta(Long idConsulta, Usuario usuario, List<Indicador> indicadores, String parametros, LocalDate fechaConsulta) {
        this.idConsulta = idConsulta;
        this.usuario = usuario;
        this.indicadores = indicadores;
        this.parametros = parametros;
        this.fechaConsulta = fechaConsulta;
    }

    // Getters y Setters
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

    public List<Indicador> getIndicadores() {
        return indicadores;
    }

    public void setIndicadores(List<Indicador> indicadores) {
        this.indicadores = indicadores;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public LocalDate getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(LocalDate fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    @Override
    public String toString() {
        return "Consulta [idConsulta=" + idConsulta + ", usuario=" + usuario + ", indicadores=" + indicadores + ", parametros="
                + parametros + ", fechaConsulta=" + fechaConsulta + "]";
    }
}

