/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Libro;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author ENRIQUE
 */
public class FXML_Controller implements Initializable {

    @FXML
    private TextField nombretext;
    @FXML
    private TextField dni_text;
    @FXML
    private TableColumn<Usuario, String> nombre;
    @FXML
    private TableColumn<Usuario, String> dni;
    @FXML
    private TableColumn<Usuario, String> nota;
    @FXML
    private TableColumn<Usuario, String> libro1;
    @FXML
    private TableColumn<Usuario, String> libro2;
    @FXML
    private TableColumn<Usuario, String> libro3;
    @FXML
    private TableView<Usuario> tablitaa;
    @FXML
    private TableColumn<Libro, String> codigo;
    @FXML
    private TableColumn<Libro, String> titulo;
    @FXML
    private TableColumn<Libro, String> autor;
    @FXML
    private TableColumn<Libro, String> estado;
    @FXML
    private TableColumn<Libro, String> fechaprestamo;
    @FXML
    private TableView<Libro> tabla2;

  private ObservableList<Usuario> usuarios;
    @FXML
    private MenuItem aniadirusuario;
    @FXML
    private MenuItem alta1;
    @FXML
    private TextField text_autor;
    @FXML
    private TextField text_codigo;
    @FXML
    private Button prestamo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
        this.nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
         this.dni.setCellValueFactory(new PropertyValueFactory("dni"));
         this.nota.setCellValueFactory(new PropertyValueFactory("nota"));
          this.libro1.setCellValueFactory(new PropertyValueFactory("libro1"));
           this.libro2.setCellValueFactory(new PropertyValueFactory("libro2"));
            this.libro3.setCellValueFactory(new PropertyValueFactory("libro3"));
             // Cargo los servicios en la tabla
        Usuario s = new Usuario();
        ObservableList<Usuario> items = s.getUsuario();
        this.tablitaa.setItems(items);
        
        
        
        //tabla libro
        
            this.codigo.setCellValueFactory(new PropertyValueFactory("codigo"));
         this.titulo.setCellValueFactory(new PropertyValueFactory("titulo"));
         this.autor.setCellValueFactory(new PropertyValueFactory("autor"));
          this.estado.setCellValueFactory(new PropertyValueFactory("estado"));
           this.fechaprestamo.setCellValueFactory(new PropertyValueFactory("fecha"));
   
             // Cargo los servicios en la tabla
        Libro s2 = new Libro();
        ObservableList<Libro> items2 = s2.getLibro();
        this.tabla2.setItems(items2);
        
    }    

    @FXML
    private void filtrar_nombre(ActionEvent event) {
        filtrarusuario();
    }

    @FXML
    private void filtrar_dni(ActionEvent event) {
        filtrarusuario();
    }

    @FXML
    private void filtrar_autor(ActionEvent event) {
        filtrarusuario();
    }

    @FXML
    private void filtrar_codigo(ActionEvent event) {
        filtrarusuario();
    }
    
    private void filtrarusuario(){
            // cojo los datos de filtro
       String nombre = this.nombretext.getText();
       String dni = this.dni_text.getText();
      

        Usuario s = new Usuario();

        // Filtramos
        ObservableList<Usuario> items = s.getUsuario2(nombre, dni);
        this.tablitaa.setItems(items);
     
    }

    @FXML
    private void dale(ActionEvent event) {
        
        
         Usuario p = this.tablitaa.getSelectionModel().getSelectedItem();
         
            if (p == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar una persona");
            alert.showAndWait();
        } else {
         
          try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXML_Prestamo.fxml"));

            // Cargo la ventana
            Parent root = loader.load();

            // Cojo el controlador
            FXML_PrestamoController controlador = loader.getController();
              controlador.initAttributtes(usuarios,p);
            // Creo el Scene
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            //persona devuelta
  Usuario aux = controlador.getUsuario();
                if (aux != null) {
                    this.tablitaa.refresh();
                }
        
         
            } catch (IOException ex) {
            Logger.getLogger(FXML_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

            }
        }

    @FXML
    private void dardealta(ActionEvent event) {
        

    
    }
      private void cargarusuarios() {
          // Cargo los aeropuertos privados
          Usuario ap = new Usuario();
          ObservableList<Usuario> obs = ap.getUsuario();
          this.tablitaa.setItems(obs);
          
          // Muestro la columna de socios
          //this.colSocios.setVisible(true);
          
          //Oculto las columnas de financiacion y discapacitados
          // this.colFinanciacion.setVisible(false);
          //  this.colDiscapacitados.setVisible(false);

    }

    }





    

