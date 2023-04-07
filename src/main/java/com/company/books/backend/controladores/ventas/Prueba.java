package com.company.books.backend.controladores.ventas;

import java.sql.*;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/test")
public class Prueba {
	
	/**
	 * @return json array para mostrar un listado de las categorias este id = 0  es el titulo de la tabla
	 * @method TbCategorias => mostrara la tabla o listado
	 * @throws SQLException => mostrara el error de la consulta
	 */
	@ResponseBody
    @RequestMapping(value = "/TbCategorias", method = RequestMethod.GET, produces = "application/json")
    public ArrayNode TbCategorias() throws SQLException {
		 String url = "jdbc:postgresql://localhost:5432/TEST";
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
	
	/**
	 * 
	 * @param JSON => pasamos los parametros en formato Json 
	 * 					el metodo lo gestionara en String hay que castear a Json y Sacar los campos  Nesesarios
	 * 
	 * @method: guardar: Metodo donde almacena o actualiza 
	 * 
	 * @return Json: mostrando el resultado desde la base de datos 
	 * @throws SQLException => mostrara el error de la consulta
	 */
	@ResponseBody
	@RequestMapping(value = "/guardar", method = RequestMethod.POST, produces = "application/json")
	public ObjectNode guardar( @RequestBody String  JSON) throws SQLException {
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(JSON, JsonObject.class);
		var url = "jdbc:postgresql://localhost:5432/TEST";
	    ObjectNode respuesta = JsonNodeFactory.instance.objectNode();
	    Connection conexion = DriverManager.getConnection(url, "postgres", "12345dev");
        Statement instruccion = conexion.createStatement();
	    if( jsonObject.get("id").getAsInt() < 1) 
	    {
	    	try {
	            String sql = "insert into categorias (id,nombre, descripcion) "
	            		+ "values ("
	            		+ "(select max(id) + 1 from categorias),'"
	            		+ jsonObject.get("nombre").getAsString().toString() +"','" 
	            		+ jsonObject.get("descripcion").getAsString().toString() +"')";
	      	   	int resultado = instruccion.executeUpdate(sql);
	    	    respuesta.put("Modulo:", "Categorias");
	    	    respuesta.put("Titulo:", "Nueva Categoria");
	    	    respuesta.put("Detalle:", "Agregada Correctamente");
	            instruccion.close();                                              
	            conexion.close();
	            return respuesta;
	        }catch (SQLException ex) {
	        	instruccion.close();                                              
	            conexion.close();
	            respuesta.put("Modulo:", "Categoria");
	            respuesta.put("Detalle Del Error:", ex.toString());
	            return respuesta;
	        }
	    }
	    else {
	    	try {
	    		String sql = "UPDATE categorias SET "
	    				+ "nombre = '"+  jsonObject.get("nombre").getAsString().toString() + "', "
	    				+ "descripcion = '" + jsonObject.get("descripcion").getAsString().toString() +  "' "
	    				+ "WHERE id = "+ jsonObject.get("id").getAsInt();
	      	   	int resultado = instruccion.executeUpdate(sql);
	    	    respuesta.put("Modulo:", "Categorias");
	    	    respuesta.put("Titulo:", "Modificacion");
	    	    respuesta.put("Detalle:", "Actualizada Correctamente");
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
	
	/**
	 * 
	 * @param JSON: pasamos un json cuya estructura sera { "id": "1"} para eliminar la fila nesesaria 
	 * @return JSON: devolvera un Json {"Modulo:", "Categoria", "Detalle:", " se elimino"  } 
	 * @throws SQLException: en caso de tener error mostrara el error en esta clausula
	 */
	@ResponseBody
	@RequestMapping(value = "/eliminar", method = RequestMethod.POST, produces = "application/json")
	public ObjectNode Eliminar( @RequestBody String  JSON) throws SQLException {
		
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(JSON, JsonObject.class);
		
		var url = "jdbc:postgresql://localhost:5432/TEST";
	    ObjectNode respuesta = JsonNodeFactory.instance.objectNode();
	    Connection conexion = DriverManager.getConnection(url, "postgres", "12345dev");
        Statement instruccion = conexion.createStatement();
	    	try {
	            String sql = "delete  from categorias where id = " + jsonObject.get("id").getAsInt();
	      	   	int resultado = instruccion.executeUpdate(sql);
	    	    respuesta.put("Modulo:", "Categorias");
	    	    respuesta.put("Titulo:", "Eliminar Categoria");
	    	    respuesta.put("Detalle:", "Se Elimino Correctamente");
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
