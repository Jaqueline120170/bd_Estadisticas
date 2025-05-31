package com.estadisticas.estadisticas_app.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estadisticas.estadisticas_app.Modelos.Descarga;


/**
 * Repositorio para la entidad `descarga`.
 * Extiende de `JpaRepository` para proporcionar acceso a las operaciones CRUD básicas sobre la tabla "descargas".
 * Además, contiene métodos personalizados para consultas específicas relacionadas con la serialización de descargas.
 */
public interface DescargaRepositorio extends JpaRepository<Descarga, Long> {
    
}