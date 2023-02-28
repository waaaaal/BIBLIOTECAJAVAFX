/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utiles;

/**
 *
 * @author ENRIQUE
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase ConexionDB, nos permite conectarnos a una base de datos MySQL
 * 
 */
public class Conexion{
    
    private Connection conexion;
    
    /**
     * Crea la conexion
     * @throws SQLException 
     */
    public Conexion() throws SQLException {

        // Adaptalos a tu conexion
        String host = "localhost:3308";
        String baseDatos = "biblioteca";
        String usuario = "root";
        String password = "1234";

        // Cadena de conexion para conectarnos a la base de datos en MySQL
        String cadenaConexion = "jdbc:mysql://" + host + "/" + baseDatos;

        // Creo la conexion 
        conexion = DriverManager.getConnection(cadenaConexion, usuario, password);

        // Hace commit automaticamente
        conexion.setAutoCommit(true);
        
    }
    
    /**
     * Ejecuta una consulta
     * @param SQL Consulta a ejecutar
     * @return ResultSet con los datos de la consulta
     * @throws SQLException 
     */
    public ResultSet ejecutarConsulta(String SQL) throws SQLException {
        Statement statement = this.conexion.createStatement();
        return statement.executeQuery(SQL);
    }
    
    /**
     * Ejecuta una instrucción
     * @param SQL Instrucción a ejecutar
     * @return numero de filas afectadas
     * @throws SQLException 
     */
    public int ejecutarInstruccion(String SQL) throws SQLException {
        Statement statement = this.conexion.createStatement();
        return statement.executeUpdate(SQL);
    }
    
    /**
     * Devuelve el ultimo ID insertado
     * @return ultimo id
     * @throws SQLException 
     */
    public int ultimoID() throws SQLException {
        ResultSet rs = this.ejecutarConsulta("SELECT last_insert_id() as last_id;");
        rs.next();
        return rs.getInt("last_id");
    }
    
    /**
     * Cierra la conexión
     * @throws SQLException 
     */
    public void cerrarConexion() throws SQLException {
        this.conexion.close();
    }
}
