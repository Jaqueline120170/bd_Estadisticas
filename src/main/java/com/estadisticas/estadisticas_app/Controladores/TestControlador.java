package com.estadisticas.estadisticas_app.Controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;

@CrossOrigin(origins = "https://jaquedev.es")
@RestController
@RequestMapping("/api/test")
public class TestControlador {
	private static final Logger logger = LoggerFactory.getLogger(TestControlador.class);
	  @GetMapping
	    public ResponseEntity<String> testApi() {
	        return ResponseEntity.ok("API FUNCIONANDO");
	    }
	  @PostConstruct
	  public void init() {
	      logger.info("Aplicación corriendo en puerto: {}", System.getenv("PORT"));
	  }
	  @GetMapping("/health")
	    public ResponseEntity<String> healthCheck() {
	        return ResponseEntity.ok("OK Aplicación en funcionamiento");
	    }

}
