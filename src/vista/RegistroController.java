/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import util.Bebidas;
import util.DAO;
import util.DBCon;
 

public class RegistroController implements Initializable {

     @FXML
    public TableView<Bebidas> tabla_Registro;

    @FXML
    private TableColumn<Bebidas, String> colProductoR;
    @FXML
    private TableColumn<Bebidas, Integer> colCantidadR;
    @FXML
    private TableColumn<Bebidas, Integer> colPrecioR;
    

    int total = 0;

    ObservableList AgregaralCarroReg = FXCollections.observableArrayList();
    private ObservableList<Bebidas> dataComprasReg;
    
    
    @FXML
    private TextField tTotal; 
    @FXML
    private Button bExportar;
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       loadTablaRegistro();
       if(! dataComprasReg.isEmpty()){
       total();
       }
    }    

    
    
    public void loadTablaRegistro() {
        DBCon con = new DBCon();
        try {
            Connection conn = con.Connect();
            dataComprasReg = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM VENTAS_HOY");
            while (rs.next()) {
                dataComprasReg.add(new Bebidas(rs.getString(1), rs.getInt(2), rs.getInt(3)));
            }
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }
        colProductoR.setCellValueFactory(new PropertyValueFactory<>("producto"));
        colPrecioR.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCantidadR.setCellValueFactory(new PropertyValueFactory<>("cantidadCarrito"));
        
        tabla_Registro.setItems(null);
        tabla_Registro.setItems(dataComprasReg);
        
con.desconectar();


    }  
  @FXML
  void wipear_Venta(){      //exportar la venta del dia a algo!! 
    DAO d = new DAO();
    d.wipear_Ventas_Hoy();
    loadTablaRegistro();
    tTotal.setText("");
  }

  
  
void total(){

int ttotal=0;    
int precio=0;
int canti=0;
int indice=0;
int size = dataComprasReg.size();
Iterator it = dataComprasReg.iterator();
    
 tabla_Registro.getSelectionModel().selectFirst(); //selecciona el primer item de la tabla carrito. 
System.out.println("prod "+tabla_Registro.getSelectionModel().getSelectedItem().getProducto() +
                    " precio " + tabla_Registro.getSelectionModel().getSelectedItem().getPrecio()
                      + " cantidad "+ tabla_Registro.getSelectionModel().getSelectedItem().getCantidadCarrito());

while(it.hasNext()){
    indice +=1;   
     precio=tabla_Registro.getSelectionModel().getSelectedItem().getPrecio();
    canti=tabla_Registro.getSelectionModel().getSelectedItem().getCantidadCarrito();
    ttotal += precio * canti;
    tabla_Registro.getSelectionModel().selectBelowCell();
    if (indice == size){
        break;
    }
}

tTotal.setText(String.valueOf(ttotal));
    
    /*
    int total = 0;
    Iterator it = dataComprasReg.iterator();


    DAO d = new DAO();
    tabla_Registro.getSelectionModel().selectFirst(); //selecciona el primer item de la tabla carrito. 
    while (it.hasNext()) {
         Bebidas be = tabla_Registro.getSelectionModel().getSelectedItem();      
        total = d.total_Ventashoy(be, be.getProducto());
         tTotal.setText(String.valueOf(total + be.getPrecio()));
        //dataComprasReg.remove(be);
        
        tabla_Registro.getSelectionModel().selectBelowCell();
    }
      
  */

    
}
  
}
