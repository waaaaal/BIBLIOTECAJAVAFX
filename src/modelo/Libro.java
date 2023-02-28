/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import utiles.Conexion;

/**
 *
 * @author ENRIQUE
 */
public class Libro {

    ArrayList<String> nombreArrayList = new ArrayList<String>();
    String nombre1;
    String codigo;
    String titulo;
    String autor;
    String estado;
    String fecha_prestamo;

    public Libro(String codigo, String titulo, String autor, String estado) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.estado = estado;
    }

    public Libro(String codigo, String titulo, String autor) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
    }

    public Libro() {

    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Libro other = (Libro) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }

        return true;
    }

    public Libro(String codigo, String titulo, String autor, String estado, String fecha_prestamo) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.estado = estado;
        this.fecha_prestamo = fecha_prestamo;
    }

    public ArrayList<String> getNombreArrayList() {
        return nombreArrayList;
    }

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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(String fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    
    
       /**
 * 
 * metodo que añade un libro y inicializa una fecha que despues será suisttiuida por el prestamo
 * llama a la query y añade el libro
 * 
     * @throws java.sql.SQLException
 */
    public boolean añadirlibro() throws SQLException {

        Conexion conexion = new Conexion();

        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(date);
        String SQL = "INSERT INTO libros (codigo, titulo, autor, estado, Fecha_prestamo) VALUES (";
        SQL += "'" + this.codigo + "', '" + this.titulo + "', '" + this.autor + "', '" + this.estado + "', '" + dateString + "')";

        int filas = conexion.ejecutarInstruccion(SQL);

        //this.id = conexion.ultimoID();
        conexion.cerrarConexion();

        return filas > 0;

    }

    
       /**
 * Se le da un autor y un codigo y devuelve el objeto buscado es el 
 * metodo detras del filtro del libro por autor y codigo
 * 
 * @param autor se le pasa un autor
 * @param codigo se le pasa un codigo
 * @return el string del codigo , del autor le he pasado
 */
    public ObservableList<Libro> getLibro(String autor, String codigo) {
        ObservableList<Libro> obs = FXCollections.observableArrayList();
        try {

            // Abro la conexion
            Conexion conexion = new Conexion();

            // realizo la consulta
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

            ResultSet rs = conexion.ejecutarConsulta(SQL);

            // recorro los resultados
            // recorro los resultados
            while (rs.next()) {

                // Cojo los datos
                String codigo2 = rs.getString("codigo");
                String titulo = rs.getString("titulo");
                String autor2 = rs.getString("autor");
                String estado2 = rs.getString("estado");
                String Fecha_prestamo = rs.getString("Fecha_prestamo");
                // Creo el servicio
                Libro s = new Libro(codigo2, titulo, autor2, estado2, Fecha_prestamo);

                obs.add(s);

            }
            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obs;
    }

    public boolean estadolibroactivo() throws SQLException {

        Conexion conexion = new Conexion();

        String SQL = "UPDATE LIBROS SET ESTADO = 'PRESTADO' where titulo = '" + titulo + "'";

        int filas = conexion.ejecutarInstruccion(SQL);

        conexion.cerrarConexion();

        return filas > 0;

    }

       /**
 * metodo que cambia el estado del libro que se le da titulo a no prestado
     * @throws java.sql.SQLException
 */
    public boolean estadolibrodesactivado() throws SQLException {

        Conexion conexion = new Conexion();

        String SQL = "UPDATE LIBROS SET ESTADO = 'NO PRESTADO' where titulo = '" + titulo + "'";

        int filas = conexion.ejecutarInstruccion(SQL);

        conexion.cerrarConexion();

        return filas > 0;

    }

       /**
 *metodo para pponer una fecha cuando se realiza el prestamo se le facilita el titulo y sustituye la fecha de creacion del libro
 * por esta de prestamo
     * @throws java.sql.SQLException
 */
    public boolean ponerfechaprestamo() throws SQLException {

        Conexion conexion = new Conexion();
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(date);
        String SQL = "UPDATE LIBROS SET Fecha_prestamo = '"+dateString +"' where titulo = '" + titulo + "'";

        int filas = conexion.ejecutarInstruccion(SQL);

        conexion.cerrarConexion();

        return filas > 0;

    }

    public boolean borrarLibro() throws SQLException {

        Conexion conexion = new Conexion();

        String SQL = "DELETE FROM libros WHERE codigo = '" + this.codigo + "';";

        int filas = conexion.ejecutarInstruccion(SQL);

        conexion.cerrarConexion();

        return filas > 0;

    }

       /**
 * es un metodo de prueba
 * 
 * @return lista observable de libros
     * @throws java.text.ParseException
 */
    public ObservableList<Libro> getUsuario() throws ParseException {
        ObservableList<Libro> obs = FXCollections.observableArrayList();
        try {

            // Abro la conexion
            Conexion conexion = new Conexion();

            // realizo la consultaa tba
            String SQL = "select * from libros";

            ResultSet rs = conexion.ejecutarConsulta(SQL);

            // recorro los resultados
            while (rs.next()) {

                // Cojo los datos
                String codigo2 = rs.getString("codigo");
                String titulo = rs.getString("titulo");
                String autor2 = rs.getString("autor");
                String estado2 = rs.getString("estado");
                String Fecha_prestamo = rs.getString("Fecha_prestamo");
                System.out.println(Fecha_prestamo);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = dateFormat.parse(Fecha_prestamo);

                //////
                LocalDateTime fechaHoraLocal = LocalDateTime.now();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String fechaHoraLocalFormateada = fechaHoraLocal.format(formato);
                SimpleDateFormat formato2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date fecha = formato2.parse(fechaHoraLocalFormateada);

                long diferencia = date.getTime() - fecha.getTime();
                long diferenciaEnDias = diferencia / (24 * 60 * 60 * 1000);
                if ((diferenciaEnDias) < 7) {
                    nombreArrayList.add(Fecha_prestamo);
                    System.out.println("has jodido");
                }

                // Creo el servicio
                Libro s = new Libro(codigo2, titulo, autor2, estado2, Fecha_prestamo);

                obs.add(s);
            }

            // Cierro la conexion
            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obs;
    }

    
       /**
 * 
 * 
 * metodo donde devuelve todos los libros que su plazo de devolucion es menor al 
 * de 7 dias , debería ser mayor a 7 dias pero para probarlo se le da la vuelta 
 * devuelve todos los libros que cumplen ese plazo 
 * 
 * @return arraylist de libros
     * @throws java.text.ParseException
 */
    public ArrayList<Libro> devoluciolibro() throws ParseException {
        ArrayList<Libro> lista = new ArrayList<>();
        try {
            // Abro la conexion
            Conexion conexion = new Conexion();

            // realizo la consulta
            String SQL = "select * from libros";

            ResultSet rs = conexion.ejecutarConsulta(SQL);

            // recorro los resultados
            while (rs.next()) {
                // Cojo los datos
                String codigo2 = rs.getString("codigo");
                String titulo = rs.getString("titulo");
                String autor2 = rs.getString("autor");
                String estado2 = rs.getString("estado");
                String Fecha_prestamo = rs.getString("Fecha_prestamo");
                System.out.println(Fecha_prestamo);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = dateFormat.parse(Fecha_prestamo);

                //////
                LocalDateTime fechaHoraLocal = LocalDateTime.now();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String fechaHoraLocalFormateada = fechaHoraLocal.format(formato);
                SimpleDateFormat formato2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date fecha = formato2.parse(fechaHoraLocalFormateada);

                long diferencia = date.getTime() - fecha.getTime();
                long diferenciaEnDias = diferencia / (24 * 60 * 60 * 1000);
                if ((diferenciaEnDias) < 7) {
                    nombreArrayList.add(Fecha_prestamo);
                    System.out.println("has jodido");
                }

                // Creo el servicio
                Libro s = new Libro(codigo2, titulo, autor2, estado2, Fecha_prestamo);

                lista.add(s);
            }

            // Cierro la conexion
            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    
    
    
       /**
 * Se le da un codigo y te devuelve un titulo de la tabla libros
 * 
 * @param codigo se le pasa un titulo
 * 
 * @return el string del titulo de ese codigo pasado
     * @throws java.text.ParseException
 */
    public String pasocodigoymedalibro1(String codigo) throws ParseException {

        try {
            // Abro la conexion
            Conexion conexion = new Conexion();

            // realizo la consulta
            String SQL = "select * from libros WHERE codigo='" + codigo + "'";

            ResultSet rs = conexion.ejecutarConsulta(SQL);

            // recorro los resultados
            while (rs.next()) {
                // Cojo los datos
                // Cojo los datos
                nombre1 = rs.getString("titulo");

                // Creo el servicio
            }

            // Cierro la conexion
            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombre1;
    }

   /**
 * Se le da un titulo y te da el codigo de la tabla libros
 * 
 * @param codigo se le pasa un titulo
 * 
 * @return el string del codigo , del autor le he pasado
     * @throws java.text.ParseException
 */
    public String pasocodigoymedalibro2(String codigo) throws ParseException {

        try {
            // Abro la conexion
            Conexion conexion = new Conexion();

            // realizo la consulta
            String SQL = "select * from libros WHERE titulo='" + codigo + "'";

            ResultSet rs = conexion.ejecutarConsulta(SQL);

            // recorro los resultados
            while (rs.next()) {
                // Cojo los datos
                // Cojo los datos
                nombre1 = rs.getString("codigo");

                // Creo el servicio
            }

            // Cierro la conexion
            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombre1;
    }

    /**
 * no se utiliza este método era solo una prueba
 * 
 * 
 * @param codigo El segundo número entero.
 * @return 
     * @throws java.text.ParseException 
 */
    public String pasocodigoymedalibro3(String codigo) throws ParseException {

        try {
            // Abro la conexion
            Conexion conexion = new Conexion();

            // realizo la consulta
            String SQL = "select * from libros WHERE codigo='" + codigo + "'";

            ResultSet rs = conexion.ejecutarConsulta(SQL);

            // recorro los resultados
            while (rs.next()) {
                // Cojo los datos
                // Cojo los datos
                nombre1 = rs.getString("titulo");

                // Creo el servicio
            }

            // Cierro la conexion
            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombre1;
    }

}
