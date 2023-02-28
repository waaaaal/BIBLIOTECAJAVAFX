/*
 * Clase que hace un prestamo seleccionando un usuario de la primera tabla añade al campo libro los prestamos realizados
y cambia en la segunda tabla el estado del libro, hay que tener en cuenta que se presta dando el codigo y no el titulo y este luego 
lo cambia a un titulo en la primera tabla usando ese codigo
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
public class FXML_prestamodeverdadController {

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
     * es la lista observable de libros que se establece en esta clase a añadir
     * usuarios el controlador le da una lista observable
     */
    private ObservableList<Libro> libros;
    /**
     * es el objeto usuario que se le pasa a la clase y la inicializa en esta
     */
    private Usuario usuario;
    /**
     * es el objeto libro que se le pasa a la clase y la inicializa en esta
     */
    private Libro libro;
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
     * no se usa
     *
     * @param a es el usuario que se proporciona.
     */
    public void initAttributes(Usuario a) {
        this.usuario = a;

        // Relleno los campos
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
     * de esta propia clase inicializandolo si usas este init no se usa
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
     * arraylist y el usuario se usa
     *
     * @param usuarios lista observable de usuarios
     * @param a el usuario
     * @throws java.text.ParseException
     */
    public void initAttributtes(ObservableList<Usuario> usuarios, Usuario a) throws ParseException {
        this.usuarios = usuarios;
        this.usuario = a;
        // cargo los datos de la persona y de la lista observable que es cargar todas las personas

        //obtengo los datos de la persona seleccionada nombre y dni
        this.textnombre.setText(a.getNombre());
        this.textdni.setText(a.getDni());

        //se crean tres objetos de libro para usar sus metodos de pasar codigo a titulo que hace de consulta
        //a la base de datos
        Libro b = new Libro();
        Libro c = new Libro();
        Libro d = new Libro();

        //del objeto inicializado a que es un objeto libro se verifica si está vacio ese objeto o no que lo que significa es
        //que esta prestado o no , si lo está, lo deja vacio si tiene un titulo lo convierte con el metodo a codigo
        if (a.getLibro1().equals("")) {
            this.textlibro1.setText("");
        } else {
            this.textlibro1.setText(b.pasocodigoymedalibro2(a.getLibro1()));
        }
        if (a.getLibro2().equals("")) {
            this.textlibro2.setText("");
        } else {
            this.textlibro2.setText(c.pasocodigoymedalibro2(a.getLibro2()));
        }
        if (a.getLibro3().equals("")) {
            this.textlibro3.setText("");
        } else {
            this.textlibro3.setText(d.pasocodigoymedalibro2(a.getLibro3()));
        }

        //no permite editar ni el dni ni el nombre para no modificar al suuario y solo modificar el prestamo
        this.textnombre.setEditable(false);
        this.textdni.setEditable(false);

        //en la siguiente estructura de if's lo que hace es deshabilitar los textfields donde hay ya un prestamo para 
        //no hacer prestamos sobre prestamos y solo donde hay huecos
        if (a.getLibro1().equals("") == false) {
            this.textlibro1.setDisable(true);
        }
        if (a.getLibro2().equals("") == false) {
            this.textlibro2.setDisable(true);
        }
        if (a.getLibro3().equals("") == false) {
            this.textlibro3.setDisable(true);
        }

        //si todos estan prestados del objeto cogido de usuario no permite hacer más prestamos y salta una alerta.
        if ((a.getLibro1().equals("") == false) && a.getLibro2().equals("") == false && a.getLibro3().equals("") == false) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Moroso");
            alert.setContentText("no puedes prestar más libros hasta que lo devuelvas");
            alert.showAndWait();
        }

    }

    /**
     * Método llamado cuando se presiona el botón "Aceptar". En la ventana de
     * modificacion Guarda los datos ingresados por el usuario en un formulario
     * y los procesa según la lógica de la aplicación. Este método se utiliza
     * para confirmar una acción o enviar un formulario completo,lo que añade un
     * prestamo en libro1 , libro2 o libro3 permitiendo prestar varios libros a
     * la vez Los prestamos los introduce en la tabla a través de la lista
     * observable que devuelvde del controlador y tambien a la base de datos a
     * través de un objeto del modelo LIBRO Y USUARIO
     *
     *
     * @param event el evento de clic del usuario que desencadenó la llamada a
     * este método
     * @return void, no hay salida de este método
     */
    @FXML
    private void aceptar(ActionEvent event) throws ParseException {

        try {
            // aquí coge los datos de los textfields y los guarda en variables
            String nombre = this.textnombre.getText();
            String dni = this.textdni.getText();

            //almacena los datos de los textfields en esta variable
            String libro1convertido = this.textlibro1.getText();

            String libro2convertido = this.textlibro2.getText();
            String libro3convertido = this.textlibro3.getText();

            // se crea un objeto libro
            Libro s = new Libro();

            //transforma los datos recogidos en los textfields que son codigos a tittulos y  los almacena en estas variables
            String libro1convertido1 = s.pasocodigoymedalibro1(libro1convertido);
            //en el objeto de libro le settea el nuevo nombre que sería el titulo devuelto
            s.setTitulo(libro1convertido1);
            //cambia el estado de libro a activo a través de un metodo del modelo libro
            s.estadolibroactivo();
            s.ponerfechaprestamo();
            Libro s2 = new Libro();
            String libro2convertido1 = s2.pasocodigoymedalibro1(libro2convertido);
            s2.setTitulo(libro2convertido1);
            s2.estadolibroactivo();
            s2.ponerfechaprestamo();

            Libro s3 = new Libro();
            String libro3convertido1 = s3.pasocodigoymedalibro1(libro3convertido);
            s3.setTitulo(libro3convertido1);
            s3.estadolibroactivo();
            s3.ponerfechaprestamo();

            //para sobreescribir la fecha creada antes e instanciada de creaccion ahora se sobreescribe con el prestamo
            //para guardarla en la base de datos
            java.util.Date date = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String alta = sdf.format(date);
            s.setFecha_prestamo(alta);
            s2.setFecha_prestamo(alta);
            s3.setFecha_prestamo(alta);

            //si estan vacios los jtextfields para que no haga una conversion en la base de datos vacia
            //inicializa la variable en ""
            if (libro1convertido.equals("")) {
                libro1convertido1 = "";
            }
            if (libro2convertido.equals("")) {
                libro2convertido1 = "";
            }
            if (libro3convertido.equals("")) {
                libro3convertido1 = "";
            }

            //en esta estructura lo que hace es si introduces un libro vacio no permitirte prestarlo para que no 
            //haga la conversion a la base de datos y devuelva un objeto null que como dijimos solo aceptamos "" como deovluciones
            //y no null
            if (libro2convertido1 == null || libro2convertido1.equals("null")) {
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setHeaderText(null);
                alert3.setTitle("Informacion");
                alert3.setContentText("INTRODUCE UN LIBRO VALIDO" + libro2convertido1);
                alert3.showAndWait();
                libro2convertido1 = "";
            }
            if (libro1convertido1 == null || libro1convertido1.equals("null")) {
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setHeaderText(null);
                alert3.setTitle("Informacion");
                alert3.setContentText("INTRODUCE UN LIBRO VALIDO" + libro2convertido1);
                alert3.showAndWait();
                libro2convertido1 = "";
            }
            if (libro3convertido1 == null || libro3convertido1.equals("null")) {
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setHeaderText(null);
                alert3.setTitle("Informacion");
                alert3.setContentText("INTRODUCE UN LIBRO VALIDO" + libro2convertido1);
                alert3.showAndWait();
                libro2convertido1 = "";
            }

            Usuario p = new Usuario(nombre, dni, libro1convertido1, libro2convertido1, libro3convertido1);

            // Compruebo si el prestamo está ya hecho aunque esta estructura no funciona ya que el equals se ha hehco para 
            //añadir usuarios no se ha diseñado para los prestamos
            //se podria poner una capa aqui intermedia que cribara si está hecha o no la devolucion
            if (!usuarios.contains(p)) {

                // Modificar
                if (this.usuario != null) {

                    try {
                        // Modifico el objeto
                        this.usuario.setNombre(nombre);
                        this.usuario.setDni(dni);
                        this.usuario.setLibro1(libro1convertido1);
                        this.usuario.setLibro2(libro2convertido1);
                        this.usuario.setLibro3(libro3convertido1);
                        this.usuario = p;
                        this.usuario.actualizar();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Informacion");
                        alert.setContentText("Se ha realizado correctamente el préstamo");
                        alert.showAndWait();
                    } catch (SQLException ex) {
                        Logger.getLogger(FXML_prestamodeverdadController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    try {
                        // insertando

                        this.usuario = p;
                        this.usuario.actualizar();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Informacion");
                        alert.setContentText("selecciona un usuario para prestar");
                        alert.showAndWait();
                    } catch (SQLException ex) {
                        Logger.getLogger(FXML_prestamodeverdadController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                // Cerrar la ventana
                Stage stage = (Stage) this.botonaceptar.getScene().getWindow();
                stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("el prestamo ya se ha realizado con ese nombre");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXML_prestamodeverdadController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que devuelve el objeto libro asociado a esta instancia de clase
     *
     * @return el libro de esta clase
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
