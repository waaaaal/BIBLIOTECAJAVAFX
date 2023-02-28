/**
 *Clase que representa la modificación de un usuario seleccionado en la tabla se
 * selecciona el usuario y se le pasa por el controlador a la nueva ventana para modificarlo y settearlo en la tabla nuevamente
 * y en la base de datos al presionar el boton aceptar.
 */
package controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Waldemar Stegierski
 */
public class FXML_ModificarUsuarioController implements Initializable {

    String dniantiguo;
    @FXML
    private Label etiqueta1;
    @FXML
    private Label etiqueta2;
    @FXML
    private Label etiqueta3;
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
     * objeto usuario de la clase usuario
     */
    private Usuario usuario;

    /**
     * inicializa esta clase del controlador pero como es por el defecto no se
     * le da uso a parte de estar solo presenta para que no falle la ejecucion
     * dl programa, podria sustiturse por otro que lo sobreescribiera
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Establece el usuario que se va a inicializar, en esta clase lo que quiere
     * decir que comunica a esta clase un usuario y esta lo establece como suyo.
     * después los settea del objeto que coge y selecciona a través de la tabla
     * y los settea en los textfield de la ventana modifcar usuario
     *
     * @param a es el usuario que se proporciona.
     */
    public void initAttributes(Usuario a) {
        this.usuario = a;

        //setea en el textfield nombre y dni el nombre y dni del objeto seleccionado
        //en la tabla para modificar
        this.textnombre.setText(a.getNombre());
        this.textdni.setText(a.getDni());
        //guarda en una variable estring el valor cogido del objeto
        dniantiguo = (a.getDni());
        //setea en el usuario el dni antiguo que sería el dni con el que cogio de la tabla
        this.usuario.setDniantiguo(dniantiguo);

        //settea los libros de la persona cogida ya que estos no se van a modificar los vuelve a poner
        //pero al no estar visible los coloca pra luego colocarselos al objeto y el usuario así no puede modificar nada
        this.textlibro1.setText(a.getLibro1());
        this.textlibro2.setText(a.getLibro2());
        this.textlibro3.setText(a.getLibro3());
        //aqui vemos lo que se ha mencionado antes se ha quitado la visibilidad y colocado el nombre de los libros 
        //para asi hacer la consulta a la base de datos con todos los datos y solo cambiar los que se quieren modificar
        //que serian el nomnbre y el dni y no los libros que para eso estarían los metodos devolver y prestar
        this.textlibro1.setVisible(false);
        this.textlibro2.setVisible(false);
        this.textlibro3.setVisible(false);
        this.etiqueta1.setVisible(false);
        this.etiqueta2.setVisible(false);
        this.etiqueta3.setVisible(false);

    }

    /**
     * Método llamado cuando se presiona el botón "Aceptar". En la ventana de
     * modificacion Guarda los datos ingresados por el usuario en un formulario
     * y los procesa según la lógica de la aplicación. Este método se utiliza
     * para confirmar una acción o enviar un formulario completo, dentro de la
     * modificaicon del usuario. Lo que hace este metodo es que le pasas el dni
     * y nombre nuevos que quieres modificaf a través de los textfields y los
     * cambia en la tabla y la base de datos al pulsar aceptar
     *
     * @param event el evento de clic del usuario que desencadenó la llamada a
     * este método
     * @return void, no hay salida de este método
     */
    @FXML
    private void aceptar(ActionEvent event) {
        //del textfield coge los datos
        String nombre = this.textnombre.getText();
        String dni = this.textdni.getText();
        //  String libro1 = this.textlibro1.getText();
        // String libro2 = this.textlibro2.getText();

        // String libro3 = this.textlibro3.getText();
        if (this.usuario != null) {

            try {
                // Modifico el objeto dandole el nuevo nombre y dni cogidos por el textfield
                this.usuario.setNombre(nombre);
                this.usuario.setDni(dni);
             
                //llamo al metodo que lo modifica en la base de datos y modifica en la base de datos con el nuevo objeto
                this.usuario.modificarUsuario();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Informacion");
                alert.setContentText("Se ha modificado correctamente tu usuario👤");
                alert.showAndWait();
            } catch (SQLException ex) {
                Logger.getLogger(FXML_prestamodeverdadController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        // Cerrar la ventana
        Stage stage = (Stage) this.botonaceptar.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) this.botonaceptar.getScene().getWindow();
        stage.close();

    }

}
