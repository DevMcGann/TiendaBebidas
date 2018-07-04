/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import util.Bebidas;
import util.DAO;

/**
 * FXML Controller class
 *
 * @author GMnotebok
 */
public class NuevoController implements Initializable {

    
@FXML
private TextField tprodNuevo;
@FXML
private TextField tprecioNuevo;
@FXML
private TextField tstockNuevo;

@FXML
private Button b_ingresar;
@FXML
private Button b_cancelar;


    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
@FXML
void agregar_Producto(){
    if ( (tprodNuevo.getText().isEmpty()) || (tprecioNuevo.getText().isEmpty()) || (tstockNuevo.getText().isEmpty())  ){
     JOptionPane.showMessageDialog(null, "Debes llenar los tres campos!", "Llena todos los campos!", JOptionPane.INFORMATION_MESSAGE);   
    }  else{
    DAO d = new DAO();
    String producto = tprodNuevo.getText();
    int precio = Integer.parseInt(tprecioNuevo.getText());
    int stockinicial = Integer.parseInt(tstockNuevo.getText());
    int cantidad = 0;
    if (d.esta_En_DB(producto)){
     JOptionPane.showMessageDialog(null, "Ya existe el producto", "Ya existe el producto", JOptionPane.INFORMATION_MESSAGE);    
    }else{
    Bebidas be = new Bebidas(producto,precio,stockinicial,cantidad);
    d.Agregar_Producto(be);
    }
    }
}


    
    
}
