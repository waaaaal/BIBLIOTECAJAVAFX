/**
 * Clase que representa el controlador principal de la ventana principal
 * lo que hará será cargar las dos tablas y conectar las siguientes funcionalidades con la tabla,
 * se podrá añadir , borrar y modificar un usuario buscar por filtros
 * y eliminar o añadir un libro
 * también se podrá prestar y devolver de la primera tabla libros de la segunda, todo esto llevará
 * a los siguientes congtroladores cuando abramos una nueva ventana implementando su lógica
 */
package controlador;

import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import modelo.Libro;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Waldemar Stegierski
 */
public class FXML_Controller implements Initializable {

    @FXML
    private TableView<Usuario> tablausuario;

    @FXML
    private TextField nombre;
    @FXML
    private TextField dni;
    @FXML
    private TableColumn<Usuario, String> nombrecolumna;
    @FXML
    private TableColumn<Usuario, String> dnicolumna;
    @FXML
    private TableColumn<Usuario, String> altacolumna;
    @FXML
    private TableColumn<Usuario, String> libro1columna;
    @FXML
    private TableColumn<Usuario, String> libro2columna;
    @FXML
    private TableColumn<Usuario, String> libro3columna;

    private ObservableList<Usuario> usuarios;
    @FXML
    private TextField codigo;
    @FXML
    private MenuItem ELIMINAR;
    @FXML
    private MenuItem AÑADIRUSUARIO;
    @FXML
    private MenuItem Modifciar;
    @FXML
    private TableView<Libro> tablalibro;
    @FXML
    private TableColumn<Libro, String> codigocolumna;
    @FXML
    private TableColumn<Libro, String> titulocolumna;
    @FXML
    private TableColumn<Libro, String> autorcolumna;
    @FXML
    private TableColumn<Libro, Integer> estadocolumna;
    @FXML
    private TableColumn<Libro, String> fechacolumna;
    @FXML
    private TextField autor;
    @FXML
    private Button prestamo;
    @FXML
    private Button debolucion;
    @FXML
    private ImageView we;
    private ObservableList<Libro> libros;
    @FXML
    private Label labelnombre;
    @FXML
    private Label labelautor;
    @FXML
    private ImageView label1;
    @FXML
    private MenuItem eliminarlibro;

    /**
     * Inicializa la clase principal y inicializa las columnas setteandolas
     * desde la base de datos usando los metodos get y set de las clases modelos
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //si tuviera toogle gruoup tambien como aeropuerto
        //sino normal la tabla y sus columnas
        this.nombrecolumna.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.dnicolumna.setCellValueFactory(new PropertyValueFactory("dni"));
        this.altacolumna.setCellValueFactory(new PropertyValueFactory("alta"));
        this.libro1columna.setCellValueFactory(new PropertyValueFactory("libro1"));
        this.libro2columna.setCellValueFactory(new PropertyValueFactory("libro2"));
        this.libro3columna.setCellValueFactory(new PropertyValueFactory("libro3"));

        //cargar los usuarios a iniciarlo
        this.codigocolumna.setCellValueFactory(new PropertyValueFactory("codigo"));
        this.titulocolumna.setCellValueFactory(new PropertyValueFactory("titulo"));
        this.autorcolumna.setCellValueFactory(new PropertyValueFactory("autor"));
        this.estadocolumna.setCellValueFactory(new PropertyValueFactory("estado"));
        this.fechacolumna.setCellValueFactory(new PropertyValueFactory("Fecha_prestamo"));

        try {
            //crea un objeso usuario
            Usuario objetousuario = new Usuario();
            //obtiene del objeto usuario a través del metodo getusuario todos los usuarios
            //de la base de datos los mete en una lista observable que es como un arraylist y los inicializa en esta clase
            usuarios = objetousuario.getUsuario();

            //settea la lista observable en la tabla
            this.tablausuario.setItems(usuarios);

            //crea un objeto libro y realiza el mismo proceso
            Libro objetolibros = new Libro();
            //mismo que antes
            libros = objetolibros.getUsuario();
            //mismo que antes
            this.tablalibro.setItems(libros);
            //mismo que antes añade a la tabla libros la lista observable

            //ahora creo un arraylist de objetos libros y este metodo devuelve los libros 
            //que se han pasado de fecha los 7 días para probar el metodo pedido en el ejercicio
            //he dicho donde la diferencia es menor a 7 dias en vez de mayor, asi puede probarse y no tener que modificatse
            //la fecha en la base de datos para pruebas.
            ArrayList<Libro> nombreArrayList2 = objetolibros.devoluciolibro();
            
            
            //creo un iterador para iterar sobre el arrraylist que almacena esos libros que se han pasado de plazo
            Iterator<Libro> nombreIterator = nombreArrayList2.iterator();
            while (nombreIterator.hasNext()) {
                
                Libro elemento = nombreIterator.next();
              Usuario b = new Usuario();
                String hola = b.diadevolucionusuario(elemento.getTitulo());
               
                System.out.print("se ha pasado de 7 dias " + elemento.getCodigo() + " / ");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Usuario " + hola);
                alert.setTitle("Error");
                alert.setContentText("Libro: " + elemento.getTitulo() + "\n" + "Codigo: " + elemento.getCodigo() + "\n" + "ha excedido los 7 dias de préstamo");
                alert.showAndWait();
            }
        } catch (ParseException ex) {
            Logger.getLogger(FXML_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
 *Metodo para cargar los usuarios y libros en sus tablas en la vista principal
 * 
 * 
 */
    private void cargarAeropuertos() throws ParseException {

        Usuario objetousuario = new Usuario();
        //array list 
        usuarios = objetousuario.getUsuario();
        this.tablausuario.setItems(usuarios);

        Libro objetolibros = new Libro();
        libros = objetolibros.getUsuario();
        this.tablalibro.setItems(libros);

    }

    
    /**
 * metodo para filtrar por nombre
 * 
 * @param event
 *
 */
    
    
    
    @FXML
    private void buscarnombre(KeyEvent event) {
        filtrarusuario();
    }

    
    
      /**
 * metodo para filtrar por dni
 * 
 * @param event
 *
 */
    @FXML
    private void buscardni(KeyEvent event) {
        filtrarusuario();
    }

      /**
 * metodo para filtrar por nombre y dni
 * 
 *
 *
 */
    private void filtrarusuario() {
        // cojo los datos de filtro
        String nombre = this.nombre.getText();
        String dni = this.dni.getText();

        Usuario s = new Usuario();

        // Filtramos usando el metodo gerusuario2 de la clase usuario
        ObservableList<Usuario> items = s.getUsuario2(nombre, dni);
        //coge del metodo los usuarios 
        this.tablausuario.setItems(items);

    }

    @FXML
    private void añadirusuario(ActionEvent event) throws ParseException {
        try {
            // carga la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXML_Prestamo.fxml"));

            // Cargo la ventana
            Parent root = loader.load();

            // Cojo el controlador
            FXML_PrestamoController controlador = loader.getController();
            controlador.initAttributtes(usuarios);

            // Creo el Scene
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/img/123.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            // Obtener la resolución de pantalla

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            double screenHeight = bounds.getHeight();

            // Configurar la altura de la ventana para que sea igual a la altura de la pantalla
            stage.setHeight(screenHeight);
            // Configurar la posición de la ventana para que esté centrada
            stage.setX((bounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((bounds.getHeight() - stage.getHeight()) / 2);

            // cojo la persona devuelta
            Usuario p = controlador.getPersona();
            if (p != null) {

                // Añado la persona
                this.usuarios.add(p);

                // Refresco la tabla
                this.tablausuario.refresh();
                cargarAeropuertos();
            }

        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    private void prestar(ActionEvent event) throws ParseException {

        Usuario p = this.tablausuario.getSelectionModel().getSelectedItem();

        if (p == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar una persona");
            alert.showAndWait();
        } else {
            try {
                // Cargo la vista
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXML_prestamodeverdad.fxml"));

                // Cargo la ventana
                Parent root = loader.load();

                // Cojo el controlador
                FXML_prestamodeverdadController controlador = loader.getController();
                controlador.initAttributtes(usuarios, p);

                // Creo el Scene
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
                cargarAeropuertos();
                this.tablalibro.refresh();
                // cojo la persona devuelta
                Usuario aux = controlador.getPersona();
                if (aux != null) {
                    this.tablausuario.refresh();
                }

            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }

    }

      /**
 * metodo para limpiar el textfield de codigo al pinchar en el textfield autor
 * asi no tiene el usuario que borrrar las barritas por defecto
 * 
 * @param event
 *
 */
    @FXML
    private void pincha(MouseEvent event) {

        this.codigo.setText("");
    }

    public void eliminarUsuario(Usuario a) {

        if (a != null) {
            this.usuarios.remove(a);
        } else {
            System.out.println("No debe ser nulo");
        }
    }

    public void eliminarLibroTabla(Usuario a) {

        if (a != null) {
            this.libros.remove(a);
        } else {
            System.out.println("No debe ser nulo");
        }
    }

    @FXML
    private void ELIMINAR(ActionEvent event) {

        // Obtengo el usuario seleecionado
        Usuario a = this.tablausuario.getSelectionModel().getSelectedItem();

        // Si es nulo, muestro error
        if (a == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar un usuario");
            alert.showAndWait();
        } else {

            // Abrimos una ventana de confirmacion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Introduce");
            alert.setContentText("¿Quieres borrar el usuario?");
            // Cogemos el resultado del boton seleccionado
            Optional<ButtonType> action = alert.showAndWait();

            // Si hemos pulsado en aceptar
            if (action.get() == ButtonType.OK) {

                try {

                    if (a.borrarUsuario()) {
                        usuarios.remove(a);

                    } else {
                        Alert alertAeropuerto = new Alert(Alert.AlertType.ERROR);
                        alertAeropuerto.setHeaderText(null);
                        alertAeropuerto.setTitle("Error");
                        alertAeropuerto.setContentText("No se ha borrado el usuario");
                        alertAeropuerto.showAndWait();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(FXML_Controller.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Cargamos de nuevo
                this.tablausuario.refresh();

            }

        }
    }

    @FXML
    private void modifiqui(ActionEvent event) {

        // Obtengo el aeropuerto seleccionado
        Usuario b = this.tablausuario.getSelectionModel().getSelectedItem();

        // Si es nulo, muestro error
        if (b == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar un usuario");
            alert.showAndWait();
        } else {

            try {
                // carga la vista
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXML_ModificarUsuario.fxml"));

                // Cargo el padre
                Parent root = loader.load();

                // Cargo el controlador asociado a la vista y le paso el aeropuerto seleccionado
                FXML_ModificarUsuarioController controlador = loader.getController();
                controlador.initAttributes(b);

                // Creo la scene
                Scene scene = new Scene(root);

                // Creo la stage
                Stage stage = new Stage();

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.setTitle("Editar Usuario");
                stage.getIcons().add(new Image("/img/123.png"));

                stage.showAndWait();

                // cargo los aeropuertos de nuevo
                this.tablausuario.refresh();
                this.tablalibro.refresh();

            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }

        }

    }

    private String generadorcodigobarras(String barcode) {

        String codigo = String.valueOf(barcode);
        StringBuilder codigoBarras = new StringBuilder();
        for (int i = 0; i < codigo.length(); i++) {
            int digito = Character.getNumericValue(codigo.charAt(i));
            switch (digito) {
                case 0:
                    codigoBarras.append("|| |||");
                    break;
                case 1:
                    codigoBarras.append("||| ||");
                    break;
                case 2:
                    codigoBarras.append("||| |");
                    break;
                case 3:
                    codigoBarras.append("| | ||");
                    break;
                case 4:
                    codigoBarras.append("|| ||");
                    break;
                case 5:
                    codigoBarras.append("| |||");
                    break;
                case 6:
                    codigoBarras.append("| | ||");
                    break;
                case 7:
                    codigoBarras.append("| || ||");
                    break;
                case 8:
                    codigoBarras.append("| ||| |");
                    break;
                case 9:
                    codigoBarras.append("|| | ||");
                    break;
                default:
                    throw new IllegalArgumentException("El número debe contener solo dígitos.");
            }
        }
        return codigoBarras.toString();
    }

    @FXML
    private void devolver(ActionEvent event) throws ParseException {
        Usuario p = this.tablausuario.getSelectionModel().getSelectedItem();

        if (p == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar una persona");
            alert.showAndWait();
        } else {
            try {
                // Cargo la vista
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXML_Devolucion.fxml"));

                // Cargo la ventana
                Parent root = loader.load();

                // Cojo el controlador
                FXML_DevolucionController controlador = loader.getController();
                controlador.initAttributtes(usuarios, p);

                // Creo el Scene
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.getIcons().add(new Image("/img/123.png"));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
                cargarAeropuertos();
                this.tablalibro.refresh();
                // cojo la persona devuelta
                Usuario aux = controlador.getPersona();
                if (aux != null) {
                    this.tablausuario.refresh();
                }

            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }

    }

    @FXML
    private void AUTORBUSQUEDA(ActionEvent event) {

        codeBusqueda();

    }

    @FXML
    private void codeBusqueda(ActionEvent event) {
        String loquedice = generadorcodigobarras(this.codigo.getText());

        codeBusqueda();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("CONFIRMAME");
        alert.setContentText("tu código es " + loquedice);
        alert.showAndWait();

    }

      /**
 * metodo para filtrar por codigo y autor
 * 
 *
 *
 */
    private void codeBusqueda() {

        // Abrimos una ventana de confirmacion
        /*
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Introduce");
            String prueba =this.codigo.getText();
          
            alert.setContentText("tu codigo de barras es ?" +  generadorcodigobarras(prueba));
            // Cogemos el resultado del boton seleccionado
            Optional<ButtonType> action = alert.showAndWait();
         */
        // cojo los datos de filtro
        String autor = this.autor.getText();
        String codigo = this.codigo.getText();

        Libro s = new Libro();
        // Filtramos
        ObservableList<Libro> items = s.getLibro(autor, codigo);
        this.tablalibro.setItems(items);

    }

    @FXML
    private void añadirlibro(ActionEvent event) {
        try {
            // carga la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXML_aniadirlibro.fxml"));

            // Cargo la ventana
            Parent root = loader.load();

            // Cojo el controlador
            FXML_aniadirusuarioController controlador = loader.getController();
            controlador.initAttributtes(libros);

            // Creo el Scene
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/img/123.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            // cojo la persona devuelta
            Libro p = controlador.getLibro();
            if (p != null) {

                // Añado la persona
                this.libros.add(p);

                // Refresco la tabla
                this.tablalibro.refresh();
            }

        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    private void entrar(MouseEvent event) {

    }

      /**
 * metodo para filtrar por codigo barras
 * 
 * @param event
 *
 */
    @FXML
    private void escribir(KeyEvent event) {
        codeBusqueda();
    }

      /**
 * metodo para filtrar por autor
 * 
 * @param event
 *
 */
    @FXML
    private void loqueescribeautor(KeyEvent event) {
        codeBusqueda();
    }

    @FXML
    private void eliminarlibro(ActionEvent event) {
        // Obtengo el usuario seleecionado
        Libro a = this.tablalibro.getSelectionModel().getSelectedItem();

        // Si es nulo, muestro error
        if (a == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar un libro");
            alert.showAndWait();
        } else {

            // Abrimos una ventana de confirmacion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Introduce");
            alert.setContentText("¿Quieres borrar el libro?");
            // Cogemos el resultado del boton seleccionado
            Optional<ButtonType> action = alert.showAndWait();

            // Si hemos pulsado en aceptar
            if (action.get() == ButtonType.OK) {

                try {

                    if (a.borrarLibro()) {
                        libros.remove(a);

                    } else {
                        Alert alertAeropuerto = new Alert(Alert.AlertType.ERROR);
                        alertAeropuerto.setHeaderText(null);
                        alertAeropuerto.setTitle("Error");
                        alertAeropuerto.setContentText("No se ha borrado el libro");
                        alertAeropuerto.showAndWait();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(FXML_Controller.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Cargamos de nuevo
                this.tablausuario.refresh();

            }

        }
    }

    @FXML
    private void borrar(MouseEvent event) {
        this.codigo.setText("");
    }
}
