/**
 * Clase que representa el controlador de añadir usuario, una vez pulsado el
 * boton de acción de añadir usuario te llevará a esta lógica que rellenará
 * los datos y al aceptar o cancelar volverá a la principal, por lo que
 * esta clase muestra toda la logíca del controlador añadir usuario.
 */
package controlador;

import java.sql.SQLException;
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
import modelo.Usuario;

/**
 *
 * @author Waldemar Stegierski
 */
public class FXML_PrestamoController {

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
     * es la lista observable de usuarios que se establece en esta clase a
     * añadir usuarios el controlador le da una lista observable
     */
    private ObservableList<Usuario> usuarios;

    /**
     * usuario es el nombre del usuario que se inicializa en esta clase para
     * coger sus metodos y atributos
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
     * Establece el usuario que se va a inicializar, en esta clase lo que quiere
     * decir que comunica a esta clase un usuario y esta lo establece como suyo.
     *
     * @param a es el usuario que se proporciona.
     */
    public void initAttributes(Usuario a) {
        this.usuario = a;

        // al inicializar proporciona a los textfields de esta ventana
        //los datos del usuario que se ha inicializado en esta clase
        //y los coloca en los textfields como un texto
        //solo sucede si inicializamos este initatribute
        this.textnombre.setText(a.getNombre());
        this.textdni.setText(a.getDni());
        this.textlibro1.setText(a.getLibro1());
        this.textlibro2.setText(a.getLibro2());
        this.textlibro3.setText(a.getLibro3());

    }

    /**
     * ES LA ÚNICA INICIALIZACIÓN DE INIT QUE SE UTILIZA EN ESTA CLASE junto a
     * la de usuario Establece la lista observable de usuarios a esta clase es
     * decir que comunica a esta clase una lista observable que es como un
     * arraylist siendo la lista de la tabla y esta se convierte en el arraylist
     * de esta propia clase inicializandolo si usas este init
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
     * arraylist y el usuario
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
        this.textlibro1.setText(a.getLibro1());
        this.textlibro2.setText(a.getLibro2());
        this.textlibro3.setText(a.getLibro3());

    }

    /**
     *
     * Método llamado cuando se presiona el botón "Aceptar" en la ventana de
     * añadir usuario . Guarda los datos ingresados por el usuario en un
     * formulario y los procesa según la lógica de la aplicación. Este método se
     * utiliza para confirmar una acción o enviar un formulario completo.
     *
     * @param event el evento de clic del usuario que desencadenó la llamada a
     * este método
     * @return void, no hay salida de este método
     */
    @FXML
    private void aceptar(ActionEvent event) {

        // Obtengo el de dato del nombre a través del textfield
        String nombre = this.textnombre.getText();
        // Obtengo el de dato del dni a través del textfield

        String dni = this.textdni.getText();

        //la siguiente estructura recorre la lista observable de la clase que es usuarios 
        //itera y comprueba si 
        boolean mio = false;
        for (Object obj : usuarios) { // Iterar a través de los elementos de la lista
            if (obj instanceof Usuario) { // Comprobar si el elemento es una instancia de la clase Usuario (o la clase que corresponda)
                Usuario persona = (Usuario) obj; // Convertir el objeto a la clase Usuario (o la clase que corresponda)
                if (persona.getDni().equals(dni)) {
                    mio = true;// Comprobar si el valor de la columna DNI es igual al valor buscado
                    // El valor buscado se encuentra en la lista
                    break; // Salir del bucle for-each si se encuentra el valor buscado
                }
            }
        }

        //aqui comprueba con la iteración anterior si existe el dni en un usuario de la lista no permite 
        //crear este usuario y pararía aquí ya el método aceptar lo que no permitiría añadirlo
        if (mio == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Elige otro dni, este ya existe");
            alert.showAndWait();
            Stage stage = (Stage) this.botonaceptar.getScene().getWindow();
            stage.close();

            //este otro else lo que mira si el dni no se encuentra mira este siguiente caso que es verificar
            //si el tamaño es correcto, como sabemos el dni tiene que ser 9 carácetes ni mayor ni menor.
        } else if (dni.length() > 9 || dni.length() < 9) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Elige un tamaño de 9 caracteres para tu dni");
            alert.showAndWait();
            Stage stage = (Stage) this.botonaceptar.getScene().getWindow();
            stage.close();

            //si los dos siguientes casos anteriores no se cumplen es porque 
            //se puede crear el usuario porloque sucedería este caso, el último else
        } else {

            //cogemos los libros de los textfields como están ocultados darán
            //serán vacios esto lo que permtie es crear a los usuarios con libros
            //vacios que significa que no tienen ningun libro prestado
            // es como el paradigma establecido libro ="" es libro no prestado.
            String libro1 = this.textlibro1.getText();
            String libro2 = this.textlibro2.getText();
            String libro3 = this.textlibro3.getText();
            //cogemos los libros de los textfields y creamos con el dni y nombre
            //anteriores un usuario
            Usuario p = new Usuario(nombre, dni, libro1, libro2, libro3);

            //aqui compara si el arraylist de usuarios contiene a este nuevo usuario que se va a crear
            //si no lo contiene lo añade
            if (!usuarios.contains(p)) {
                //y si no lo contiene y no es null, puede pasar de añadr un usuario nulo
                if (this.usuario == null) {

                    //creo un objeto date que le de la fecha de hoy
                    java.util.Date date = new java.util.Date();
                    //creo el formato
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //creo el string donde le voy a meter el formado
                    String alta = sdf.format(date);
                    // Creo el usuario con los libros sin la fecha solo meterlo en la base de datos
                    Usuario dir = new Usuario(nombre, dni, libro1, libro2, libro3);
                    //  otro usuario con la fecha
                    Usuario dir2 = new Usuario(nombre, dni, alta, libro1, libro2, libro3);
                    //al usuario de la clase lo inicializo con este que es el que vamos a añadir
                    this.usuario = dir2;

                    try {
                        // inserto o añado al usuario llamando al metodo de insertar
                        // de la clase usurio
                        if (dir.insertar()) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setTitle("Exito");
                            alert.setContentText("El usuario 👤 se ha añadido correctamente?");
                            alert.showAndWait();
                            //si no funciona la query de añadir en la base de datos envía el flujo de datos
                            //a este else
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText(null);
                            alert.setTitle("Error");
                            alert.setContentText("El usuario no se ha podido añadir a nuestra base de datos");
                            alert.showAndWait();
                        }
                        // Cerrar la ventana
                        Stage stage = (Stage) this.botonaceptar.getScene().getWindow();
                        stage.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(FXML_PrestamoController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                //si el usuario esta contenido en la lista observable el flujo va aqui saltando un mensaje de aviso que existe
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("El usuario ya existe, no puedes crear otro igual");
                alert.showAndWait();
            }
        }
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
        Stage stage = (Stage) this.botonaceptar.getScene().getWindow();
        stage.close();
    }

}
