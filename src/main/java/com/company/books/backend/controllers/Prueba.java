package com.company.books.backend.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventario")
public class Prueba {

	private HashMap<String, String> metadata = new HashMap<>();
	
	public HashMap<String, String> getMetadata() {
		return metadata;
	}
	
	@ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
    public HashMap<String, String> test() {
		
		HashMap<String, String> respuesta = new HashMap<String, String>();
		respuesta.put("tipo", "tipo");
		respuesta.put("codigo", "tipo");
		respuesta.put("dato", "dato");
		    return respuesta;
    }
	
	@ResponseBody
    @RequestMapping(value = "/test02", method = RequestMethod.GET, produces = "application/json")
    public HashMap<String, String> test02() {
		
		HashMap<String, String> respuesta = new HashMap<String, String>();
		respuesta.put("Titulo:", "safe sell acaba de iniciar");
		respuesta.put("Estado:", "Funcionando al 100%");
		respuesta.put("Detalle", " se actualizo correctamente");
		    return respuesta;
    }
	
	
	
}
