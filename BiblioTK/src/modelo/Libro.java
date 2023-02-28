/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import conexiondb.ConexionMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ENRIQUE
 */
public class Libro {

    private String codigo;
    private String titulo;
    private String autor;
    private int estado;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    private String fecha;

    public Libro() {
    }

    public Libro(String codigo, String titulo, String autor, int estado, String fecha) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.estado = estado;
        this.fecha = fecha;
    }

    public String getcodigo() {
        return codigo;
    }

    public void setcodigo(String codigo) {
        this.codigo = codigo;
    }

    public String gettitulo() {
        return titulo;
    }

    public void settitulo(String varchar) {
        this.titulo = varchar;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public ObservableList<Libro> getLibro() {
        ObservableList<Libro> obs = FXCollections.observableArrayList();
        try {

            // Abro la conexion
            ConexionMySQL conexion = new ConexionMySQL("localhost", "3308", "biblioteca", "root", "1234");

            // realizo la consulta
            conexion.ejecutarConsulta("select * "
                    + "from libros ");

            ResultSet rs = conexion.getResultSet();

            // recorro los resultados
            while (rs.next()) {

                // Cojo los datos
                String codigo = rs.getString("codigo");
                String titulo = rs.getString("titulo");
               String autor = rs.getString("autor");
               int estado = rs.getInt("estado");
                String fecha = rs.getString("fecha_prestamo");
              
                // Creo el servicio
                Libro s = new Libro(codigo,titulo,autor,estado,fecha);

                obs.add(s);

            }

            // Cierro la conexion
            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obs;
    }

    public ObservableList<Libro> getLibro2(String Autor, String codigo) {
        ObservableList<Libro> obs = FXCollections.observableArrayList();
        try {

            // Abro la conexion
            ConexionMySQL conexion = new ConexionMySQL("localhost", "3308", "biblioteca", "root", "1234");

            String SQL = "select *"
                    + "from libros"
                    + " where autor like '" + autor.trim() + "%'" + " AND codigo like '" + codigo.trim() + "%'";

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
               String codigo2 = rs.getString("codigo");
                String titulo2 = rs.getString("titulo");
               String autor2 = rs.getString("autor");
               int estado2 = rs.getInt("estado");
                String fecha2 = rs.getString("fecha_prestamo");
                Libro s = new Libro(codigo2,titulo2,autor2,estado2,fecha2);

                obs.add(s);

            }
            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obs;
    }

}
