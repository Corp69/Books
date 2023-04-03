package com.company.books.backend.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.response.CategoriaResponseRest;
import com.company.books.backend.service.ICategoriaService;
import com.company.books.backend.service.LibroServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/v1")
public class CategoriaRestController {
	
	private static final Logger log = LoggerFactory.getLogger(CategoriaRestController.class);
	

	@ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<JSONObject>test() {
		JSONObject entity = new JSONObject();
		    entity.put("numero", 1);
		    entity.put("titulo", "Informacion de Menu");
		    entity.put("mensaje", "Informacion Correcta");
		    //entity.put("respuesta", (Respuesta == null ? "" : new JSONParser().parse(Respuesta)));
		    return new ResponseEntity<>(entity, HttpStatus.OK);
    }
	
	@Autowired
	private ICategoriaService service;
	
	@GetMapping("/categorias")
	public ResponseEntity<CategoriaResponseRest> consultaCat() {
		ResponseEntity<CategoriaResponseRest> response = service.buscarCategorias();
		return response;
	}
	
	@GetMapping("/categorias/{id}")
	public ResponseEntity<CategoriaResponseRest> consultaPorId(@PathVariable Long id) {
		ResponseEntity<CategoriaResponseRest> response = service.buscarPorId(id);
		return response;
	}
	
	@PostMapping("/categorias")
	public ResponseEntity<CategoriaResponseRest> crear(@RequestBody Categoria request) {
		ResponseEntity<CategoriaResponseRest> response = service.crear(request);
		return response;
	}
	
	@PutMapping("/categorias/{id}")
	public ResponseEntity<CategoriaResponseRest> actualizar (@RequestBody Categoria request, @PathVariable Long id) {
		ResponseEntity<CategoriaResponseRest> response = service.actualizar(request, id);
		return response;
	}
	
	@DeleteMapping("/categorias/{id}")
	public ResponseEntity<CategoriaResponseRest> eliminar (@PathVariable Long id) {
		ResponseEntity<CategoriaResponseRest> response = service.eliminar(id);
		return response;
	}

}
