package com.estadisticas.estadisticas_app.Controladores;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://jaquedev.es")
@RestController
public class PingControlador {
	 @GetMapping("/api/ping")
	    public String ping() {
	        return "pong";
	    }

}
