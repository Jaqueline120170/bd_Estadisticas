package com.estadisticas.estadisticas_app.Modelos;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "logs_actividad", schema = "gestion")
public class LogActividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log")
    private Long id;

    @Column(name = "accion", nullable = false)
    private String accion; // "login", "descarga", "consulta", etc.

    @Column(name = "fecha")
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(name = "detalles", columnDefinition = "jsonb")
    private String detalles; // Información adicional

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    // Getters y Setters
    // ...
}
