package com.estadisticas.estadisticas_app.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estadisticas.estadisticas_app.Modelos.Consulta;


/**
 * Repositorio para la entidad `Conulta`.
 * Extiende de `JpaRepository` para proporcionar acceso a las operaciones CRUD básicas sobre la tabla "consultas".
 * Además, contiene métodos personalizados para consultas específicas relacionadas con la serialización de consultas.
 */
@Repository
public interface ConsultaRepositorio extends JpaRepository<Consulta, Long> {
   
}
