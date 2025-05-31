package com.estadisticas.estadisticas_app.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estadisticas.estadisticas_app.Modelos.Categoria;


/**
 * Repositorio para la entidad `Categoria`.
 * Extiende de `JpaRepository` para proporcionar acceso a las operaciones CRUD básicas sobre la tabla "categorias".
 * Además, contiene métodos personalizados para consultas específicas relacionadas con el registro d ecategorias.
 */
@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

	boolean existsByNombreCategoria(String nombre);
   
}