package com.estadisticas.estadisticas_app.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estadisticas.estadisticas_app.Modelos.Categoria;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {
    // Puedes agregar métodos como: findByNombre(String nombre) si lo necesitas más adelante
}