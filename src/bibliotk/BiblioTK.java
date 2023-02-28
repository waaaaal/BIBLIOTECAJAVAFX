/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author ENRIQUE
 */
public class BiblioTK extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/FXML_Principal.fxml"));
        stage.getIcons().add(new Image("/img/123.png"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/vista/dark.css").toExternalForm());

         stage.setResizable(true);
          stage.setFullScreen(true); // Establece la ventana en modo no-pantalla completa
        stage.sizeToScene(); // Ajusta el tamaño de la ventana al tamaño del contenido
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
