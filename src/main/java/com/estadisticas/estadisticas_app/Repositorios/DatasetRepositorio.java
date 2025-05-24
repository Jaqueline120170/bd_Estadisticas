package com.estadisticas.estadisticas_app.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.estadisticas.estadisticas_app.Modelos.Dataset;


	@Repository
	public interface DatasetRepositorio extends JpaRepository<Dataset, Long> {
		@Query("SELECT d FROM Dataset d " +
			       "WHERE (:nombre IS NULL OR d.nombreDataset LIKE :nombre) " +
			       "AND (:formato IS NULL OR d.formatoDataset = :formato) " +
			       "AND (:idCategoria IS NULL OR d.categoria.idCategoria = :idCategoria)")
			List<Dataset> findByFiltros(@Param("nombre") String nombre,
			                            @Param("formato") String formato,
			                            @Param("idCategoria") Long idCategoria);

	}


