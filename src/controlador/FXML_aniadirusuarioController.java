/*
 * Clase que añade un libro a través de la venta que abre pulsando en añadir libro
 * lo que hace es pasarle la lógica que sucede en esa ventana que es darle los valores del nuevo libro
 * y se lo añade a la tabla por una lista observable y a la base de datos por una query de objeto libro.
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
import modelo.Libro;
import modelo.Usuario;

/**
 *
 * @author Waldemar Stegierski
 */
public class FXML_aniadirusuarioController {

    private TextField textnombre;
    private TextField textdni;
    private TextField textlibro1;
    private TextField textlibro2;

    @FXML
    private Button botonaceptar;
    @FXML
    private Button botoncancelar;
    /**
     * es la lista observable de libros que se establece en esta clase a añadir
     * libros el controlador le da una lista observable
     */
    private ObservableList<Libro> Libro;
    /**
     * es el objeto libro que se le pasa a la clase y la inicializa en esta
     */
    private Libro libro;
    private FXML_Controller a;
    @FXML
    private Label etiqueta1;
    @FXML
    private TextField textcodigo;
    @FXML
    private TextField texttitulo;
    @FXML
    private TextField textautor;
    private TextField texteestado;
    private TextField textfechaprestamo;

    /**
     * Establece el libro que se va a inicializar, en esta clase lo que quiere
     * decir que comunica a esta clase un usuario y esta lo establece como suyo.
     *
     * NO SE USA ESTA INICIALIZACION
     *
     * @param a es el libro que se proporciona.
     */
    public void initAttributes(Libro a) {
        this.libro = a;

        // Relleno los campos
    }

    /**
     * ES LA ÚNICA INICIALIZACIÓN DE INIT QUE SE UTILIZA EN ESTA CLASE ñe
     * proporciona una lista observable de libros y la innicializa en esta
     * propia clase
     *
     * @param libros es la lista observable que se proporciona de libros.
     */
    public void initAttributtes(ObservableList<Libro> libros) {
        this.Libro = libros;

    }

    /**
     * Método llamado cuando se presiona el botón "Aceptar". En la ventana de
     * modificacion Guarda los datos ingresados por el usuario en un formulario
     * y los procesa según la lógica de la aplicación. Este método se utiliza
     * para confirmar una acción o enviar un formulario completo, dentro añadir
     * un libro . Lo que hace este metodo es que le s das los datos de un nuevo
     * libro y lo añade a la tabla y la base de datos
     *
     *
     * @param event el evento de clic del usuario que desencadenó la llamada a
     * este método
     * @return void, no hay salida de este método
     */
    @FXML
    private void aceptar(ActionEvent event) {
        // Indico los errores que tengamos

        // Obtengo los datos de la nueva ventana a través de los textfields
        String codigo = this.textcodigo.getText();
        String titulo = this.texttitulo.getText();

        String autor = this.textautor.getText();

        String estado2 = "NO PRESTADO";

        //el objeto q es para pasar el objeto al conteins y despues con el equals
        //definir que si esta en la lista otra objeto con el mismo codigo no permita crearlo
        Libro q = new Libro(codigo, titulo, autor);

        //COMO ANTES SI EN EL ARRAYLIST DE LIBROS ESTA ESTE LIBRO NO SE AÑADE
        if (!Libro.contains(q)) {

            if (this.libro == null) {

                String alta = "";
                // Creo el libro para settear en la base de datos y en esta clase
                Libro p2 = new Libro(codigo, titulo, autor, estado2, alta);
                this.libro = p2;

                try {
                    //llama al metodo para añadirlo en la base de datos a través del objeto libro y clase libro
                    if (p2.añadirlibro()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Exito");
                        alert.setContentText("El libro se ha añadido con éxito 📖");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Error");
                        alert.setContentText("El libro no se ha añadido");
                        alert.showAndWait();
                    }
                    // Cerrar la ventana
                    Stage stage = (Stage) this.botonaceptar.getScene().getWindow();
                    stage.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FXML_aniadirusuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("La persona ya existe");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("el libro ya existe");
            alert.showAndWait();
        }
    }

    /**
     * Método que devuelve el objeto libro asociado a esta instancia de clase
     *
     * @return el libro de esta clase
     */
    public Libro getLibro() {
        return libro;
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) this.botonaceptar.getScene().getWindow();
        stage.close();
    }

}
