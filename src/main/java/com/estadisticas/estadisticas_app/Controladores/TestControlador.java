package com.estadisticas.estadisticas_app.Controladores;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestControlador {
	
	  @GetMapping
	    public ResponseEntity<String> testApi() {
	        return ResponseEntity.ok("API FUNCIONANDO");
	    }
	
}
