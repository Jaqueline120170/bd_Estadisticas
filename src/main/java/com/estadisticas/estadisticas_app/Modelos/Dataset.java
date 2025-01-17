package com.estadisticas.estadisticas_app.Modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
		@Column(name = "nombre_usuario")
	    private String descripcionDataset;
	    private String archivoUrl;
	    private String fechaActualizacionDataset;
}
