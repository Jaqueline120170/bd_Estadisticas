package com.estadisticas.estadisticas_app.Modelos;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "graficos", schema = "gestion")
public class Graficos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grafico")
    private Long id;

    @Column(name = "tipo_grafico", nullable = false)
    private String tipoGrafico; // bar, pie, line...

    @Column(name = "configuracion", columnDefinition = "jsonb", nullable = false)
    private String configuracion;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_dataset")
    private Dataset dataset;
    
    
    public Graficos() {
		super();
	}

	public Graficos(Long id, String tipoGrafico, String configuracion, LocalDateTime fechaCreacion, Usuario usuario,
			Dataset dataset) {
		super();
		this.id = id;
		this.tipoGrafico = tipoGrafico;
		this.configuracion = configuracion;
		this.fechaCreacion = fechaCreacion;
		this.usuario = usuario;
		this.dataset = dataset;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoGrafico() {
		return tipoGrafico;
	}

	public void setTipoGrafico(String tipoGrafico) {
		this.tipoGrafico = tipoGrafico;
	}

	public String getConfiguracion() {
		return configuracion;
	}

	public void setConfiguracion(String configuracion) {
		this.configuracion = configuracion;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
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

    // Getters y Setters
    
}
