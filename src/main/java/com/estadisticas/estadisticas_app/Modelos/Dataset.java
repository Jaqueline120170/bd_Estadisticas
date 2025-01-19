package com.estadisticas.estadisticas_app.Modelos;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "datasets", schema = "gestion")
public class Dataset {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id_dataset")
	    private Long id;
		
		@Column(name = "nombre_dataset")
	    private String nombreDataset;
		
		@Column(name = "fuente_dataset")
	    private String fuenteDataset;
		
		@Column(name = "descripcion_dataset")
	    private String descripcionDataset;
		
		@Column(name = "archivo_dataset")
	    private String archivoUrl;
		
		@Column(name = "fechaActualizacion_dataset")
	    private String fechaActualizacionDataset;
		
		// Relación 1:n con Indicadores
	    @OneToMany(mappedBy = "dataset", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Indicador> indicadores;

		public Dataset() {
			super();
		}

		public Dataset(Long id, String nombreDataset, String fuenteDataset, String descripcionDataset,
				String archivoUrl, String fechaActualizacionDataset) {
			super();
			this.id = id;
			this.nombreDataset = nombreDataset;
			this.fuenteDataset = fuenteDataset;
			this.descripcionDataset = descripcionDataset;
			this.archivoUrl = archivoUrl;
			this.fechaActualizacionDataset = fechaActualizacionDataset;
		}

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

		public String getFechaActualizacionDataset() {
			return fechaActualizacionDataset;
		}

		public void setFechaActualizacionDataset(String fechaActualizacionDataset) {
			this.fechaActualizacionDataset = fechaActualizacionDataset;
		}

		@Override
		public String toString() {
			return "Dataset [id=" + id + ", nombreDataset=" + nombreDataset + ", fuenteDataset=" + fuenteDataset
					+ ", descripcionDataset=" + descripcionDataset + ", archivoUrl=" + archivoUrl
					+ ", fechaActualizacionDataset=" + fechaActualizacionDataset + "]";
		}
		
		
		
}
