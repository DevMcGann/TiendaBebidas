/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package venta_bebidas;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

        
        
        

/**
 *
 * @author Gab
 */
public class launcher extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
    
     FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/vista/VistaPrincipal.fxml"));
        Parent root = loader.load();


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sistema de Venta de Bebidas");
        stage.show();
    }
    
}