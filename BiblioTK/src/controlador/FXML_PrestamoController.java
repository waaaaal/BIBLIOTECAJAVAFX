/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class FXML_PrestamoController implements Initializable {

    @FXML
    private TextField nombre;
    @FXML
    private TextField dni;
    @FXML
    private TextField libro1;
    @FXML
    private TextField libro2;
    @FXML
    private TextField libro3;
    @FXML
    private Button aceptar;
    @FXML
    private Button cancelar;
    private Usuario usuario;
      private ObservableList<Usuario> Usuarios;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
        
        
        
        
        
        
    }    
     public void initAttributtes(ObservableList<Usuario> Usuarios) {
        this.Usuarios = Usuarios;
    }
     
         public void initAttributtes(ObservableList<Usuario> Usuarios, Usuario p) {
        this.Usuarios = Usuarios;
        this.usuario = p;
        // cargo los datos de la persona
        this.nombre.setText(p.getNombre());
        this.dni.setText(p.getDni());
        this.nombre.setEditable(false);
         this.dni.setEditable(false);
        if(p.getLibro1()!=null){
            this.libro1.setDisable(true);
            this.libro1.setText("ya has cogido un primer libro");
        }if(p.getLibro2()!=null){
             this.libro2.setDisable(true);
            this.libro2.setText("ya has cogido un segundolibro");
        }if(p.getLibro3()!=null){
             this.libro3.setDisable(true);
            this.libro3.setText("ya has cogido un tercerlibro");
        }
      
        
    }

    public Usuario getUsuario() {
        return usuario;
    }
    
}
