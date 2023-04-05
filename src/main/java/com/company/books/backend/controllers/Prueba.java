package com.company.books.backend.controllers;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/inventario")
public class Prueba {

	@ResponseBody
    @RequestMapping(value = "/test03", method = RequestMethod.GET, produces = "application/json")
    public ArrayNode test03() throws SQLException {
		 String url = "jdbc:postgresql://localhost:5432/Dev";
		 Connection conexion = DriverManager.getConnection(url, "postgres", "12345dev");
         Statement instruccion = conexion.createStatement();
		try {
			String sql = "SELECT * FROM categorias";
            ResultSet resultado = instruccion.executeQuery(sql);
            ObjectNode encabezado = JsonNodeFactory.instance.objectNode();
            encabezado.put("id:", "0");
            encabezado.put("Consulta:", "ok");
            encabezado.put("Modulo:", "Categorias");
            encabezado.put("Titulo:", "Consulta Correcta");
            ArrayNode categorias = JsonNodeFactory.instance.arrayNode();
            categorias.add(encabezado);
            while (resultado.next()) {
                ObjectNode categoria = JsonNodeFactory.instance.objectNode();
                categoria.put("id", resultado.getInt("id"));
                categoria.put("nombre", resultado.getString("nombre"));
                categoria.put("descripcion", resultado.getString("descripcion"));
                categorias.add(categoria);
            }
            resultado.close();
            instruccion.close();
            conexion.close();
            return categorias;
        }catch (SQLException ex) {
            ex.printStackTrace(System.out);
            instruccion.close();
            conexion.close();
            return null;
        }	
    }
	
	@ResponseBody
	@RequestMapping(value = "/test04", method = RequestMethod.POST, produces = "application/json")
	public ObjectNode guardar() throws SQLException {
	    var url = "jdbc:postgresql://localhost:5432/Dev";
	    ObjectNode respuesta = JsonNodeFactory.instance.objectNode();
	    Connection conexion = DriverManager.getConnection(url, "postgres", "12345dev");
        Statement instruccion = conexion.createStatement();
	    try {
            String sql = "insert into categorias (id,nombre, descripcion) values (22,'TERROR',' EL AMANECER OBSCURO')";
      	   	int resultado = instruccion.executeUpdate(sql);
    	    System.out.println(resultado);
    	    respuesta.put("Modulo:", "Categorias");
    	    respuesta.put("Titulo:", "Nueva Categoria");
    	    respuesta.put("Detalle:", "Agregada Correctamente");
            instruccion.close();                                              
            conexion.close();
            return respuesta;
        }catch (SQLException ex) {
        	instruccion.close();                                              
            conexion.close();
            ex.printStackTrace(System.out);
            respuesta.put("Modulo:", "Categoria");
            respuesta.put("Detalle Del Error:", ex.toString());
            return respuesta;
        }
}
	
	
	
	
}
