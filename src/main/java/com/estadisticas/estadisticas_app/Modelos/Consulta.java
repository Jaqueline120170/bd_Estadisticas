package com.estadisticas.estadisticas_app.Modelos;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "consultas", schema = "gestion")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta", updatable = false)
    private Long id;

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

    @Column(name = "parametros_consulta")
    private String parametros;

    @Column(name = "fecha_consulta")
    private String fechaConsulta;

    public Consulta() {}

    public Consulta(Long id, Usuario usuario, List<Indicador> indicadores, String parametros, String fechaConsulta) {
        this.id = id;
        this.usuario = usuario;
        this.indicadores = indicadores;
        this.parametros = parametros;
        this.fechaConsulta = fechaConsulta;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getFechaConsulta() {
		return fechaConsulta;
	}

	public void setFechaConsulta(String fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}

	@Override
	public String toString() {
		return "Consulta [id=" + id + ", usuario=" + usuario + ", indicadores=" + indicadores + ", parametros="
				+ parametros + ", fechaConsulta=" + fechaConsulta + "]";
	}

    
    
}
