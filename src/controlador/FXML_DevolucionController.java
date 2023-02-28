/**
 * Clase que representa la devolución, sirve para generar la lógica de la ventana de devolucion del controlador
 * se inicia cuando se pulsa el boton de devolucion y se selecciona a un usuario
 */
package controlador;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Libro;
import modelo.Usuario;

/**
 *
 * @author Waldemar Stegierski
 */
public class FXML_DevolucionController {

    /**
     * variable del string que se inicializa a través de los textfield de libros
     */
    String libro1convertido;
    String libro2convertido;
    String libro3convertido;
    @FXML
    private TextField textnombre;
    @FXML
    private TextField textdni;
    @FXML
    private TextField textlibro1;
    @FXML
    private TextField textlibro2;
    @FXML
    private TextField textlibro3;
    @FXML
    private Button botonaceptar;
    @FXML
    private Button botoncancelar;

    /**
     * lista observable de usuarios
     */
    private ObservableList<Usuario> usuarios;

    /**
     * objeto usuario
     */
    private Usuario usuario;
    private FXML_Controller a;
    @FXML
    private Label etiqueta1;
    @FXML
    private Label etiqueta2;
    @FXML
    private Label etiqueta3;

    /**
     * variables de los libros antiguos que son los que coge el objeto que se
     * pasa al controlador de usuario y que se inicializa
     */
    private String libroantiguo1;
    private String libroantiguo2;

    private String libroantiguo3;

    /**
     * Establece el usuario que se va a inicializar, en esta clase lo que quiere
     * decir que comunica a esta clase un usuario y esta lo establece como suyo.
     * después los settea del objeto que coge y selecciona a través de la tabla
     *
     * @param a es el usuario que se proporciona.
     */
    public void initAttributes(Usuario a) {
        this.usuario = a;

        // Relleno los campos que coge el objeto a que se le pasa a través
        //del main
        this.textnombre.setText(a.getNombre());
        this.textdni.setText(a.getDni());
        this.textlibro1.setText(a.getLibro1());
        this.textlibro2.setText(a.getLibro2());
        this.textlibro3.setText(a.getLibro3());

    }

    /**
     * importante MENCIONAR ESTA INICIALIZACION NO SE UTILIZA se le inicializa
     * un arraylist (listaobservable) de usuarios y se le pasa a esta instancia
     * de clase, al objeto anteriormente definido de usuarios en los atrivutos
     * también desavilita los textfield que no se van a utilizar como
     *
     * @param usuarios es la lista observable que se proporciona.
     */
    public void initAttributtes(ObservableList<Usuario> usuarios) {
        //ha iniciado este constructor en el main y por eso pasa 
        this.usuarios = usuarios;
        this.textlibro1.setVisible(false);
        this.textlibro2.setVisible(false);
        this.textlibro3.setVisible(false);
        this.etiqueta1.setVisible(false);
        this.etiqueta2.setVisible(false);
        this.etiqueta3.setVisible(false);
    }

    /**
     * Establece la lista de usuarios que es una lista observable como un
     * arraylist y el usuario Despues setea el nombre y el dni del usuario
     * seleccionado y pasado por controlador
     *
     * @param usuarios lista observable de usuarios
     * @param a el usuario
     */
    public void initAttributtes(ObservableList<Usuario> usuarios, Usuario a) {
        this.usuarios = usuarios;
        this.usuario = a;
        // cargo los datos de la persona
        this.textnombre.setText(a.getNombre());
        this.textdni.setText(a.getDni());

        //cargamos los libros del objeto pasado por inicialización a través del controlador
        this.libroantiguo1 = a.getLibro1();
        this.libroantiguo2 = a.getLibro2();
        this.libroantiguo3 = a.getLibro3();

        //no deja editar esos campos para no modificar al usuario sino solo los libros que se van a devolver
        this.textnombre.setEditable(false);
        this.textdni.setEditable(false);

        //en esta estructura de if anidados se comprueba si el libro que se ha cogido esta vacio o no, si lo esta
        // no permite modificar ese campo lo deja disable, ya que no se puede devolver un libro no prestado, 
        //se definió  antes que ="" significa no prestado = vacio.
        if (a.getLibro1().equals("") != false) {
            this.textlibro1.setDisable(true);
        }
        if (a.getLibro2().equals("") != false) {
            this.textlibro2.setDisable(true);
        }
        if (a.getLibro3().equals("") != false) {
            this.textlibro3.setDisable(true);
        }

        //si ningun libro se ha prestado avisa que no puedes devolver ya que no hay ningún libro prestado 
        //lo que añade otro caso de uso
        if ((a.getLibro1().equals("") != false) && a.getLibro2().equals("") != false && a.getLibro3().equals("") != false) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Moroso");
            alert.setContentText("no tienes mas libros que devolver, no intentes hackear la biblio");
            alert.showAndWait();
        }
    }

    /**
     * Método llamado cuando se presiona el botón "Aceptar". En la ventana de
     * devolución Guarda los datos ingresados por el usuario en un formulario y
     * los procesa según la lógica de la aplicación. Este método se utiliza para
     * confirmar una acción o enviar un formulario completo, dentro de la
     * devolucion. Lo que hace este metodo es que le doy los codigos de los
     * libros para devolverlos (esto es pensando en usar el prestamo y la
     * devolucion con una pistola escaner ) en numero y los transforma a un
     * titulo guardandolo asi en la tabla y la base de datos
     *
     * @param event el evento de clic del usuario que desencadenó la llamada a
     * este método
     * @return void, no hay salida de este método
     */
    @FXML
    private void aceptar(ActionEvent event) throws SQLException, ParseException {

        // a través de los textfields de la ventana devolucion se cogen los datos nombre, dni y libro1,libro2y libro3
        String nombre = this.textnombre.getText();
        String dni = this.textdni.getText();

        String libro1convertido = this.textlibro1.getText();
        String libro2convertido = this.textlibro2.getText();
        String libro3convertido = this.textlibro3.getText();

        //se crea un objeto p de la clase usuario con el constructor que tiene todo menos la fecha de alta.
        Usuario p = new Usuario(nombre, dni, libro1convertido, libro2convertido, libro3convertido);

        // se crea el objeto de libro
        Libro s = new Libro();
        //se settea el titulo al objeto de libro creado anteriormente con el nombre del primer libro cogido, lo comentamos 
        //ya que el setitulo no hace falta.
        // s.setTitulo(libro1convertido);

        //este metodo lo que hace es cambiar el estado del libro a desactivado donde se le da el titulo del objeto 
        //los titulos se settean abajo y estos comentados son una prueba antigua.
        s.estadolibrodesactivado();
        Libro s2 = new Libro();
         s2.setTitulo(libro2convertido);
        Libro s3 = new Libro();
         s3.setTitulo(libro3convertido);

        //aqui convierte el codigo del libro que he leido con la pistola
        //a un titulo a través de un metodo en libro convierte el codigo en titulo
        libro1convertido = s.pasocodigoymedalibro1(libro1convertido);
        //setea al objeto de persona el codigo
        p.setLibro1(libro1convertido);
        //y settea el nombre del titulo al objeto de titulo
        s.setTitulo(libro1convertido);

        libro2convertido = s2.pasocodigoymedalibro1(libro2convertido);
        p.setLibro2(libro2convertido);
        s2.setTitulo(libro2convertido);

        libro3convertido = s3.pasocodigoymedalibro1(libro3convertido);
        p.setLibro3(libro3convertido);
        s3.setTitulo(libro3convertido);

        //cambia en la columna estado a los que anteriormente hemos setteado el estado si estaba prestado a no prestado
        //ya que se ha devuelto
        s.estadolibrodesactivado();
        s2.estadolibrodesactivado();
        s3.estadolibrodesactivado();

        //si el usuario seleccionado es distinto de nulo
        if (this.usuario != null) {

            try {
                //modifica el objeto de usuario pasado por el controlador

                // this.usuario.setNombre(nombre);
                //  this.usuario.setDni(dni);
                //en este if lo que hace es comparar el usuario para por controlador que selecciona de la tabla
                //y compara los libros con los que hemos cogido de la ventana devolucion le hemos pasado un codigo lo ha converitdo
                //y lo que compara es esos titulos son iguales , si es cierto se devuelve el libro prestado
                //libro prestado de la tabla es igual a libro devuelto en la ventana = a devolucion posible
                if (this.usuario.getLibro1().equals(p.getLibro1())) {
                    this.usuario.borrarLibr1();
                    this.usuario.setLibro1("");
                }

                if (this.usuario.getLibro2().equals(p.getLibro2())) {
                    this.usuario.borrarLibr2();
                    this.usuario.setLibro2("");

                }
                if (this.usuario.getLibro3().equals(p.getLibro3())) {
                    this.usuario.borrarLibr3();
                    this.usuario.setLibro3("");

                }
                
                
                 //iguala el usuario de la instancia con los nuevos valores que si han sido efectivas las devoluciones serían
                 //con los libros vacios y es lo que devuelve al controlador este usuario.
                this.usuario = p;

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Informacion");
                alert.setContentText("Se ha devuelto el prestamo de forma eficaz");
                alert.showAndWait();
            } catch (SQLException ex) {
                Logger.getLogger(FXML_prestamodeverdadController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
        

            this.usuario = p;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("el usuario seleccionado es null, porloque no puede realizarse una devolución, selecciona un usuario");
            alert.showAndWait();

        }

        // Cerrar la ventana
        Stage stage = (Stage) this.botonaceptar.getScene().getWindow();
        stage.close();

    }

    /**
     * Método que devuelve el objeto usuario asociado a esta instancia de clase
     *
     * @return el usuario de esta clase
     */
    public Usuario getPersona() {
        return usuario;
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }

}
