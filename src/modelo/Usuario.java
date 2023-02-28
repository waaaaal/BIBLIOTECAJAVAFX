/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utiles.Conexion;

/**
 *
 * @author ENRIQUE
 */
public class Usuario {

    ArrayList<String> nombreArrayList = new ArrayList<String>();
    String nombre3;
    String nombre2;

    String nombre1;

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

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
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.dni, other.dni)) {
            return false;
        }

        if (!Objects.equals(this.libro1, other.libro1)) {
            return false;
        }
        if (!Objects.equals(this.libro2, other.libro2)) {
            return false;
        }
        if (!Objects.equals(this.libro3, other.libro3)) {
            return false;
        }
        return true;
    }

    public Usuario(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
    }

    private String nombre;
    private String dni;
    private String dniantiguo;

    public String getDniantiguo() {
        return dniantiguo;
    }

    public void setDniantiguo(String dniantiguo) {
        this.dniantiguo = dniantiguo;
    }
    private String alta;
    private String libro1;
    private String libro2;
    private String libro3;

    public Usuario() {
    }

    public Usuario(String nombre, String dni, String alta, String libro1, String libro2, String libro3) {
        this.nombre = nombre;
        this.dni = dni;
        this.alta = alta;
        this.libro1 = libro1;
        this.libro2 = libro2;
        this.libro3 = libro3;
    }

    public Usuario(String nombre, String dni, String libro1, String libro2, String libro3) {
        this.nombre = nombre;
        this.dni = dni;
        this.libro1 = libro1;
        this.libro2 = libro2;
        this.libro3 = libro3;
    }

    public boolean modificarUsuario() throws SQLException {

        // Abro la conexion
        Conexion conexion = new Conexion();

        // Formo el SQL
        String SQL = "UPDATE usuarios SET nombre = '" + this.nombre + "', dni = '" + this.dni + "' WHERE dni = '" + this.dniantiguo + "';";

        // Ejecuto la instruccion
        int filas = conexion.ejecutarInstruccion(SQL);

        // cierro la conexion
        conexion.cerrarConexion();

        // Indico si se ha insertado o no
        if (filas > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean insertar() throws SQLException {

        Conexion conexion = new Conexion();

        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(date);

        String SQL = "INSERT INTO usuarios (nombre, dni,alta, libro1, libro2, libro3) VALUES (";
        SQL += "'" + this.nombre + "', '" + this.dni + "', '" + dateString + "', '" + this.libro1 + "', '" + this.libro2 + "', '" + this.libro3 + "')";

        int filas = conexion.ejecutarInstruccion(SQL);

        //this.id = conexion.ultimoID();
        conexion.cerrarConexion();

        return filas > 0;

    }

    public boolean actualizar() throws SQLException {

        // Abro la conexion
        Conexion conexion = new Conexion();

        // Formo el SQL
        String SQL = "";
        SQL += "UPDATE usuarios SET LIBRO1 = '" + this.libro1 + "', ";
        SQL += "LIBRO2 = '" + this.libro2 + "', ";
        SQL += "LIBRO3 = '" + this.libro3 + "' ";
        SQL += "WHERE dni = '" + this.dni + "';";

        // Ejecuto la instruccion
        int filas = conexion.ejecutarInstruccion(SQL);

        // cierro la conexion
        conexion.cerrarConexion();

        // Indico si se ha insertado o no
        if (filas > 0) {
            return true;
        } else {
            return false;
        }

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

    public String getAlta() {
        return alta;
    }

    public void setAlta(String nota) {
        this.alta = nota;
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

    public boolean borrarUsuario() throws SQLException {

        Conexion conexion = new Conexion();

        String SQL = "DELETE FROM usuarios WHERE dni = '" + this.dni + "';";

        int filas = conexion.ejecutarInstruccion(SQL);

        conexion.cerrarConexion();

        return filas > 0;

    }

       /**
 * metodo para cargar los usuarios de la base de datos en la tabla
 * a través de una lista observable
 * 
 * 
 
 * 
 * @return devuelve una lista observable con todos los usuaruios
     * @throws java.text.ParseException 
 */
    public ObservableList<Usuario> getUsuario() throws ParseException {
        ObservableList<Usuario> obs = FXCollections.observableArrayList();
        try {

            // Abro la conexion
            Conexion conexion = new Conexion();

            // realizo la consulta
            String SQL = "select * from usuarios";

            ResultSet rs = conexion.ejecutarConsulta(SQL);

            // recorro los resultados
            while (rs.next()) {

                // Cojo los datos
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                String alta = rs.getString("alta");
                String libro1 = rs.getString("libro1");
                String libro2 = rs.getString("libro2");
                String libro3 = rs.getString("libro3");
                String codigolibro;
                String codigolibro2;
                String codigolibro3;
                Libro m = new Libro();
                /*
                  if( libro1.equals("")){
                    codigolibro="";
                }else{
                       codigolibro = m.pasocodigoymedalibro(libro1);
                  }
               if( libro2.equals("")){
                    codigolibro2="";
                }else{
                      codigolibro2 = m.pasocodigoymedalibro(libro2);
               }
                if( libro3.equals("")){
                    codigolibro3="";
                }else{
                     codigolibro3 = m.pasocodigoymedalibro(libro3);
                }

                //vamos a hacer el cambio de codigo a libro
                // Creo el servicio
             
          
                 System.out.println(codigolibro2);
              
                 */
                Usuario s = new Usuario(nombre, dni, alta, libro1, libro2, libro3);

                obs.add(s);

            }

            // Cierro la conexion
            conexion.cerrarConexion();

            //
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obs;
    }

    public boolean borrarLibr1() throws SQLException {

        Conexion conexion = new Conexion();

        String SQL = "UPDATE usuarios SET libro1 = ''  WHERE dni='" + dni + "' AND libro1='" + libro1 + "'";

        int filas = conexion.ejecutarInstruccion(SQL);

        conexion.cerrarConexion();

        return filas > 0;

    }

    public boolean borrarLibr2() throws SQLException {

        Conexion conexion = new Conexion();

        String SQL = "UPDATE usuarios SET libro2 = ''  WHERE dni='" + dni + "' AND libro2='" + libro2 + "'";

        int filas = conexion.ejecutarInstruccion(SQL);

        conexion.cerrarConexion();

        return filas > 0;

    }

    public boolean borrarLibr3() throws SQLException {

        Conexion conexion = new Conexion();

        String SQL = "UPDATE usuarios SET libro3 = ''  WHERE dni='" + dni + "' AND libro3='" + libro3 + "'";

        int filas = conexion.ejecutarInstruccion(SQL);

        conexion.cerrarConexion();

        return filas > 0;

    }

       /**
 * Se le da un nombre y dni y devuelve la lista de busquda sobre esos campos,
 * es el metodo detras del filtro de nombre y dni textfields
 * 
 * @param nombre se le da un nombre a la tabla de usuarios
 * @param dni un dni se le da un nombre a la tabla de usuarios
 * 
 * @return listo observable de los usuarios buscados por esos parametros en la bbdd
 */
    public ObservableList<Usuario> getUsuario2(String nombre, String dni) {
        ObservableList<Usuario> obs = FXCollections.observableArrayList();
        try {

            // Abro la conexion
            Conexion conexion = new Conexion();

            // realizo la consulta
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

            ResultSet rs = conexion.ejecutarConsulta(SQL);

            // recorro los resultados
            // recorro los resultados
            while (rs.next()) {

                // Cojo los datos
                String nombre2 = rs.getString("nombre");
                String dni2 = rs.getString("dni");
                String alta2 = rs.getString("alta");
                String libro11 = rs.getString("libro1");
                String libro22 = rs.getString("libro2");
                String libro33 = rs.getString("libro3");
                // Creo el servicio
                Usuario s = new Usuario(nombre2, dni2, alta2, libro11, libro22, libro33);

                obs.add(s);

            }
            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obs;
    }

    
       /**
 * metodo donde se le da un codigo en unos de los libros y se busca que devuelva el titulo donde encage uno de esos codigos
 * libro1 o libro2 o libro3 si es por ejemplo en el libro1 que devuelva el nombre de usuario
 * este metodo se usa para saber que usuario debe que libro
 * @param  codigo
 
 * 
 * @return un string que devuelve el nombre del usuario que tiene uno de esos libros
     * @throws java.text.ParseException 
 */
    public String diadevolucionusuario(String codigo) throws ParseException {

        try {
            // Abro la conexion
            Conexion conexion = new Conexion();

            // realizo la consulta
            String SQL = "select * from usuarios WHERE libro1='" + codigo + "' OR libro2='" + codigo + "' OR libro3='" + codigo + "'";

            ResultSet rs = conexion.ejecutarConsulta(SQL);

            // recorro los resultados
            while (rs.next()) {
                // Cojo los datos
                // Cojo los datos
                nombre3 = rs.getString("nombre");

                // Creo el servicio
            }

            // Cierro la conexion
            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombre3;
    }

    
    /**
 * metodo de prueba no se usa
 * 
 * @param  codigo
 
 * 
 * @return un string que devuelve 
     * @throws java.text.ParseException 
 */
    public String conversioncodigolibros(String codigo) throws ParseException {

        try {
            // Abro la conexion
            Conexion conexion = new Conexion();

            // realizo la consulta
            String SQL = "select * from usuarios WHERE libro1='" + codigo + "'";

            ResultSet rs = conexion.ejecutarConsulta(SQL);

            // recorro los resultados
            while (rs.next()) {
                // Cojo los datos
                // Cojo los datos
                nombre1 = rs.getString("libro1");
                nombre2 = rs.getString("libro2");
                nombre3 = rs.getString("libro3");

                // Creo el servicio
            }

            // Cierro la conexion
            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombre3;
    }

    
    
    
       /**
 * metodo de prueba no se usa
 * 
 * @param  codigo
 
 * 
 * @return un string que devuelve 
     * @throws java.text.ParseException 
 */
    public String conversiondecodigoatitulo1(String codigo) throws ParseException {

        try {
            // Abro la conexion
            Conexion conexion = new Conexion();

            // realizo la consulta
            String SQL = "select * from usuarios WHERE libro1='" + codigo + "'";

            ResultSet rs = conexion.ejecutarConsulta(SQL);

            // recorro los resultados
            while (rs.next()) {
                // Cojo los datos
                // Cojo los datos

                nombre3 = rs.getString("titulo");

                // Creo el servicio
            }

            // Cierro la conexion
            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombre3;
    }

}
