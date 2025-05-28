package com.estadisticas.estadisticas_app.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estadisticas.estadisticas_app.Modelos.Consulta;

public interface ConsultaRepositorio extends JpaRepository<Consulta, Long> {
   
}
