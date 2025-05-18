package com.estadisticas.estadisticas_app.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estadisticas.estadisticas_app.Modelos.Descarga;

public interface DescargaRepositorio extends JpaRepository<Descarga, Long> {
    // Similarmente, métodos personalizados si quieres
}