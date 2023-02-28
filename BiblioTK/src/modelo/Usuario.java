/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import conexiondb.ConexionMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ENRIQUE
 */
public class Usuario {

    private String nombre;
    private String dni;
    private int nota;
    private String libro1;
    private String libro2;
    private String libro3;

    public Usuario() {
    }

    public Usuario(String nombre, String dni, int nota, String libro1, String libro2, String libro3) {
        this.nombre = nombre;
        this.dni = dni;
        this.nota = nota;
        this.libro1 = libro1;
        this.libro2 = libro2;
        this.libro3 = libro3;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getLibro1() {
        return libro1;
    }

    public void setLibro1(String libro1) {
        this.libro1 = libro1;
    }

    public String getLibro2() {
        return libro2;
    }

    public void setLibro2(String libro2) {
        this.libro2 = libro2;
    }

    public String getLibro3() {
        return libro3;
    }

    public void setLibro3(String libro3) {
        this.libro3 = libro3;
    }

    public ObservableList<Usuario> getUsuario() {
        ObservableList<Usuario> obs = FXCollections.observableArrayList();
        try {

            // Abro la conexion
            ConexionMySQL conexion = new ConexionMySQL("localhost", "3308", "biblioteca", "root", "1234");

            // realizo la consulta
            conexion.ejecutarConsulta("select * "
                    + "from usuarios ");

            ResultSet rs = conexion.getResultSet();

            // recorro los resultados
            while (rs.next()) {

                // Cojo los datos
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                int nota = rs.getInt("nota");
                String libro1 = rs.getString("libro1");
                String libro2 = rs.getString("libro2");
                String libro3 = rs.getString("libro3");
                // Creo el servicio
                Usuario s = new Usuario(nombre, dni, nota, libro1, libro2, libro3);

                obs.add(s);

            }

            // Cierro la conexion
            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obs;
    }

    public ObservableList<Usuario> getUsuario2(String nombre, String dni) {
        ObservableList<Usuario> obs = FXCollections.observableArrayList();
        try {

            // Abro la conexion
            ConexionMySQL conexion = new ConexionMySQL("localhost", "3308", "biblioteca", "root", "1234");

            String SQL = "select *"
                    + "from usuarios"
                    + " where DNI like '" + dni.trim() + "%'" + " AND NOMBRE like '" + nombre.trim() + "%'";

            /*
            // completo la consulta segun lo que venga
            if (NIF != null) {
                SQL += "and c.nif='" + NIF + "'";
            }

            if (fechaAlquiler != null && fechaEntrega != null) {
                SQL += "and s.fecha_alquiler >= '" + fechaAlquiler.toString() + "' and s.fecha_entrega <= '" + fechaEntrega.toString() + "' ";
            } else if (fechaAlquiler != null) {
                SQL += "and s.fecha_alquiler >= '" + fechaAlquiler.toString() + "' ";
            } else if (fechaEntrega != null) {
                SQL += "s.fecha_entrega <= '" + fechaEntrega.toString() + "' ";
            }
             */
            // realizo la consulta
            conexion.ejecutarConsulta(SQL);

            ResultSet rs = conexion.getResultSet();

            // recorro los resultados
            // recorro los resultados
            while (rs.next()) {

                // Cojo los datos
                String nombre2 = rs.getString("nombre");
                String dni2 = rs.getString("dni");
                int nota2 = rs.getInt("nota");
                String libro11 = rs.getString("libro1");
                String libro22 = rs.getString("libro2");
                String libro33 = rs.getString("libro3");
                // Creo el servicio
                Usuario s = new Usuario(nombre2, dni2, nota2, libro11, libro22, libro33);

                obs.add(s);

            }
            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obs;
    }

}
