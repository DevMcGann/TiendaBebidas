/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.Bebidas;
import util.DAO;
import util.DBCon;
/**
 * FXML Controller class
 *
 * @author Gab
 */
public class VistaPrincipalController implements Initializable {

   ////////////////////////// armado de tabla de bebibas  ////////////////////////////////////  
    @FXML
    public TableView<Bebidas> tabla_bebidas;

    @FXML
    private TableColumn<Bebidas, Integer> colId;
    @FXML
    private TableColumn<Bebidas, String> colProducto;
    @FXML
    private TableColumn<Bebidas, Integer> colPrecio;
    @FXML
    private TableColumn<Bebidas, Integer> colStock;
    @FXML
    private TableColumn<Bebidas, Integer> colCantidadCarrito1;

    private ObservableList<Bebidas> dataBebidas;
///////////////////////////////fin tabla bebidas/////////////////////////////////////////////   

//////////////////////// Tabla Carrito////////////////////////////////////////////////////////    
    @FXML
    public TableView<Bebidas> tabla_Carrito;

    @FXML
    private TableColumn<Bebidas, String> colProductoCarrito;
    @FXML
    private TableColumn<Bebidas, Integer> colCantidadCarrito;
    @FXML
    private TableColumn<Bebidas, Integer> colPrecioCarrito;
   

    int total = 0;

    ObservableList AgregaralCarroLista = FXCollections.observableArrayList();
    private ObservableList<Bebidas> dataCompras;
    public ObservableList<Bebidas> dataVentas;
    ///////////////////////////////////////////////////////////////////////////////////////////   

    @FXML
    TextField tproducto;
    
    @FXML
    TextField tprecio;
    @FXML
    TextField tstock;
    @FXML
    TextField tbuscar;
    @FXML
    TextField tTotal;
    @FXML
    TextField tcantidadcarrito;

    @FXML
    private Button agregarCarrito;
    @FXML
    private Button confirmarCompra;
    @FXML
    private Button cancelarCompra;
    @FXML
    private Button restock;
    @FXML
    private Button b_ingresar;
    @FXML
    private Button b_updateProducto;
    @FXML
    private Button b_ingresarStock;
      @FXML
    private Button b_ticket;

    @FXML
    private Label lblStock;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDatabaseBebidas();
        loadTablaCompras();
    }

    
void limpia_txt(){
 tproducto.setText("");
 tstock.setText("");
 tprecio.setText("");
 tTotal.setText("");
 tcantidadcarrito.setText("");
}    
    
///////////////// Mostrar DB en la tabla//////////////////////////////////////////////    
    public void loadDatabaseBebidas() {
        DBCon con = new DBCon();
        try {
            Connection conn = con.Connect();
            dataBebidas = FXCollections.observableArrayList();
            // Execute query and store result in a resultset
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM BEBIDAS");
            while (rs.next()) {
                //get string from db,whichever way 
                dataBebidas.add(new Bebidas(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
            }

        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));  //como figuran en la entidad
        colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
       // colCantidadCarrito1.setCellValueFactory(new PropertyValueFactory<>("cantidadCarrito"));

        tabla_bebidas.setItems(null);
        tabla_bebidas.setItems(dataBebidas);

    }
/////////////////////////////////////////////////////////////////////////////////////////////    

//seleccionar de la tabla    
    @FXML
    private void selecciona() {
        tabla_bebidas.refresh();

        if (!tabla_bebidas.getSelectionModel().isEmpty()) {
            Bebidas bebida = tabla_bebidas.getSelectionModel().getSelectedItem();
            tproducto.setText(bebida.getProducto());
            tproducto.setStyle("-fx-text-fill: green;-fx-background-color: black;");
            int precio = bebida.getPrecio();
            String valorprecio = String.valueOf(precio);
            tprecio.setText(valorprecio);
            tprecio.setStyle("-fx-text-fill: green;-fx-background-color: black;");
            tcantidadcarrito.setText(String.valueOf(bebida.getCantidadCarrito()));
            tcantidadcarrito.setStyle("-fx-text-fill: green;-fx-background-color: black;");
            String valorstock = String.valueOf(bebida.getStock());
            tstock.setText(valorstock);
            tstock.setStyle("-fx-text-fill: green;-fx-background-color: black;");
            if (bebida.getStock() == 0) {
                lblStock.setVisible(true);
                agregarCarrito.setDisable(true);
            } else {
                lblStock.setVisible(false);
                agregarCarrito.setDisable(false);
            }
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////

    
    
////////////BUSCAR PROD////////////////////
@FXML    
void buscar_Producto(){    
  FilteredList<Bebidas> filteredData = new FilteredList<>(dataBebidas, p -> true); 

        
       tbuscar.textProperty().addListener((observable, oldValue, newValue) -> {
       tbuscar.setStyle("-fx-text-fill: green;-fx-background-color: black;");
       
           filteredData.setPredicate(Bebidas -> {
                
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(Bebidas.getProducto()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                    
                } 
                return false; 
            });
        });

        //  Envuelve la filteredList en una SortedList 
        SortedList<Bebidas> sortedData = new SortedList<>(filteredData);
        // Relaciona el comparador de la sortedlist con la tabla de bebidas
        sortedData.comparatorProperty().bind(tabla_bebidas.comparatorProperty());
        //  y añade los elementos filtrados a la tabla de bebidas
        tabla_bebidas.setItems(sortedData);
}



//////////////////// AGREGAR PRODUCTO A CARRITO////////////////////////////    
  int cantidad = 0;
    @FXML
    private void agregarCarro2() {
     
        
         DAO dao = new DAO();
        if (!tabla_bebidas.getSelectionModel().isEmpty()) {
            Bebidas bebida = tabla_bebidas.getSelectionModel().getSelectedItem();
            String produ = tproducto.getText();
            int precio = Integer.parseInt(tprecio.getText());

            if (Integer.parseInt(tstock.getText()) > 1) {
                int stockactual = bebida.getStock();
                int nuevoStock = stockactual - 1;
                cantidad = bebida.getCantidadCarrito() + 1;
                bebida.setCantidadCarrito(cantidad);
                tcantidadcarrito.setText(String.valueOf(cantidad));
                tstock.setText(String.valueOf(nuevoStock));
                bebida.setStock(nuevoStock);
                tabla_bebidas.refresh();
            } else {
                lblStock.setVisible(true);
                agregarCarrito.setDisable(true);
            }
            total = total + precio;
            tTotal.setText(String.valueOf(total));
            
            
            if (dao.esta_En_Carrito(produ)) {
                cantidad = bebida.getCantidadCarrito();
                dao.update_Stock(bebida);    //stock - cantidad (100 - 15)
                bebida.setCantidadCarrito(cantidad);
                dao.update_Cantidad(bebida);//si esta en el carrito suma cantidad
                dao.update_Cantidad_Carro(bebida);
                loadTablaCompras();
                tabla_bebidas.refresh();

            } else {
                dao.update_Stock(bebida);
                bebida.setCantidadCarrito(1);
                cantidad = 1;
                dao.Agregar_Producto_Carrito(bebida);  //agregar a la db ESTA COMPRA
                dao.update_Cantidad(bebida);
                dao.update_Cantidad_Carro(bebida);
                loadTablaCompras();
                tabla_bebidas.refresh();

            }
        }//tablabebidas
    }//agregar carrito 2
////////////////////////////////////////////////////////////////////////
    
  

///////////////// LLENAR tabla compras//////////////////
    public void loadTablaCompras() {
        DBCon con = new DBCon();
        try {
            Connection conn = con.Connect();
            dataCompras = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM ESTA_COMPRA");
            while (rs.next()) {
                dataCompras.add(new Bebidas(rs.getString(1), rs.getInt(2), rs.getInt(3)));
            }
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }
        colProductoCarrito.setCellValueFactory(new PropertyValueFactory<>("producto"));
        colPrecioCarrito.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCantidadCarrito.setCellValueFactory(new PropertyValueFactory<>("cantidadCarrito"));
        
        tabla_Carrito.setItems(null);
        tabla_Carrito.setItems(dataCompras);

    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML //eliminar todo lo que haya en la tabla carrito
    void wipe_tablaCompra() {
        if (! dataCompras.isEmpty()){
        cancelar_todo();
        }
}
    
 void cancelar_todo(){
     
 int size = tabla_Carrito.getItems().size();
 DAO d = new DAO();
 tabla_Carrito.getSelectionModel().selectFirst(); //selecciona el primer item de la tabla carrito. 
 
Iterator it = dataCompras.iterator();
 
while (it.hasNext()) {
     Bebidas be = tabla_Carrito.getSelectionModel().getSelectedItem();      
    int stock = d.Stock_Bebida(be, be.getProducto());
    be.setStock(stock + be.getCantidadCarrito());
    d.update_Stock(be);
    dataCompras.remove(be);
    d.Eliminar_Producto_Carrito(be);
    tabla_Carrito.getSelectionModel().selectBelowCell();
    
}  

limpia_txt();
d.wipear_CantidadCarrito();
d.wipear_EstaCombra();
loadDatabaseBebidas();
loadTablaCompras();
 }   
    
 
 
 
    @FXML   //llenar stock a 1000, solo para probar
    void refill_Stock(){
        DAO d = new DAO();
        d.wipear_EstaCombra();
        d.wipear_CantidadCarrito();
        d.refill_Stockprueba();
        loadTablaCompras();
        loadDatabaseBebidas();
        total = 0;
        cantidad = 0;
        tTotal.setText("");
    }
 
    
    
    
    
    
    
    
    
    
    
    
    
    
 /////////////////////////////////////////////////////////////////////EVENTOS KEY Y OTROS///////////////////////////////////////////////////////////////////////////////////////   
    //// desactivar el boton de agregar al carro si hacemos click en la tabla carro para evitar bug
    @FXML
    public void clickTablaCarrito(MouseEvent event) {
        agregarCarrito.setDisable(true);
    }
    /// Enter y actualizar y eliminar  /f5 f6
    @FXML
    void keyEnter(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER){
    agregarCarro2();
    tabla_Carrito.refresh();
        loadTablaCompras();    
    } else if (!tabla_bebidas.getSelectionModel().isEmpty() && (event.getCode() == KeyCode.DELETE) ) {
            Bebidas bebida = tabla_bebidas.getSelectionModel().getSelectedItem();
            DAO d = new DAO();
            int n = JOptionPane.showOptionDialog(new JFrame(), "Seguro que desea eliminar " + bebida.getProducto() + " de la BD??", 
        "Eliminar Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
        null, new Object[] {"SI", "NO"}, JOptionPane.YES_OPTION);

        if (n == JOptionPane.YES_OPTION) {
               d.Eliminar_Producto(bebida);
            loadDatabaseBebidas();
         }
   }  else if (!tabla_bebidas.getSelectionModel().isEmpty() && (event.getCode() == KeyCode.F6) ) {
            Bebidas bebida = tabla_bebidas.getSelectionModel().getSelectedItem();
            DAO d = new DAO();
            String respuesta = JOptionPane.showInputDialog(null,
             "Ingresar Stock",
             "Cuantas unidades de "+bebida.getProducto()+ " desea ingresar?",
            JOptionPane.QUESTION_MESSAGE);
            if (!respuesta.isEmpty()) {
            int rta = Integer.parseInt(respuesta);
            int stocknuevo = bebida.getStock() + rta;
            bebida.setStock(stocknuevo);
            d.update_Stock(bebida);
            loadDatabaseBebidas();
            }
            
        } else if (!tabla_bebidas.getSelectionModel().isEmpty() && (event.getCode() == KeyCode.F5) ) {
            Bebidas bebida = tabla_bebidas.getSelectionModel().getSelectedItem();
            DAO d = new DAO();
            String respuesta = JOptionPane.showInputDialog(null,
             "Ingresar Precio",
             "Cual es el nuevo precio de  "+bebida.getProducto(),
            JOptionPane.QUESTION_MESSAGE);
            if (!respuesta.isEmpty()) {
            int rta = Integer.parseInt(respuesta); 
            bebida.setPrecio(rta);
            d.update_Precio(bebida);
            loadDatabaseBebidas();
            }
            
        } 
    
    
    }// KeyEnter
    
    
    @FXML //on key released para seleccionar items en la tabla con las flechitas
    void keyUpDown (KeyEvent event){
    if ((event.getCode() == KeyCode.DOWN) || (event.getCode() == KeyCode.UP)){
        selecciona();    
    }
    }
    
    /////teclas MENOS carrito
    @FXML
    void keyMenos(KeyEvent event) {
        tabla_Carrito.refresh();
        if (!tabla_Carrito.getSelectionModel().isEmpty() && (event.getCode() == KeyCode.SUBTRACT) ) {
            Bebidas bebida = tabla_Carrito.getSelectionModel().getSelectedItem();
            DAO d = new DAO();
 
            int cantidad = bebida.getCantidadCarrito();
            int total1 = Integer.parseInt(tTotal.getText());
            int stockactual = bebida.getStock();
            
                if (bebida.getCantidadCarrito() < 1){
                    d.Eliminar_Producto_Carrito(bebida);
                    d.update_Cantidad_Carro(bebida);
                    d.update_Cantidad(bebida);
                    tabla_Carrito.refresh();
                    tcantidadcarrito.setText(String.valueOf(bebida.getCantidadCarrito()));
                    loadTablaCompras();
                    
             }else{
            
                cantidad -= 1;
                bebida.setCantidadCarrito(cantidad);
                d.update_Cantidad_Carro(bebida);
                d.update_Cantidad(bebida);
                int stock = d.Stock_Bebida(bebida, bebida.getProducto());
                bebida.setStock(stock + 1);
                d.update_Stock(bebida);
                 total1 = total1 - bebida.getPrecio();
                 tabla_bebidas.refresh();
                 loadDatabaseBebidas();
                 tTotal.setText(String.valueOf(total1));
                 total = total1;
                 tcantidadcarrito.setText(String.valueOf(cantidad));
     
            }
            }
         }//key -
    
    //////////////////////////////////////  ELIMINAR PRODUCTO //////////////////////////////////////////
    @FXML
    void eliminar_Producto(KeyEvent event){
         if (!tabla_bebidas.getSelectionModel().isEmpty() && (event.getCode() == KeyCode.DELETE) ) {
            Bebidas bebida = tabla_bebidas.getSelectionModel().getSelectedItem();
            DAO d = new DAO();
            int n = JOptionPane.showOptionDialog(new JFrame(), "Seguro que desea eliminar " + bebida.getProducto() + " de la BD??", 
        "Eliminar Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
        null, new Object[] {"SI", "NO"}, JOptionPane.YES_OPTION);

        if (n == JOptionPane.YES_OPTION) {
               d.Eliminar_Producto(bebida);
            loadDatabaseBebidas();
        }   
         }
    }
  
 ////////////////////////////////////////////////////////////////////////////////////////// 
@FXML//// 
void venta_Concretada(){
// sumar a la tabla VENTAS_HOY los productos vendidos durante el dia. 

 DBCon con = new DBCon();
        try {
            Connection conn = con.Connect(); 
            DAO d = new DAO();
            Bebidas beb = new Bebidas();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM ESTA_COMPRA");
            while (rs.next()) {
                String producto = rs.getString(1);
                int precio = rs.getInt(2);
                int canti = rs.getInt(3);
                
                if (!d.esta_En_Ventas_Hoy(producto)){
                 beb.setProducto(producto);
                 beb.setPrecio(precio);
                 beb.setCantidadCarrito(canti);    
                 d.Agregar_Producto_Comprahoy(beb);
                 d.Eliminar_Producto_Carrito(beb);
                 loadTablaCompras();
                }               
                else {   //Si esta en la DB ventas   
                    canti +=d.Cantidad_Ventas(beb,producto);
                    d.update_Cantidad_Ventas(producto,canti);
                    d.wipear_EstaCombra();
                    loadTablaCompras();
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error en Control" + ex);
        }
        
con.desconectar();
tTotal.setText("");
total =0;
}

 /////////////////////////////////////////////////////////////


    
/////////////////////////////////   Ventanas ////////////////////////
@FXML
void ventanaNuevo () throws Exception{
 FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/vista/Nuevo.fxml"));
Parent root1= (Parent)fxmlLoader.load();
Stage stage= new Stage(); 
stage.setScene(new Scene(root1));
stage.setTitle("Nuevo Producto");
stage.show();
  
}

@FXML
void ventanaRegistro () throws Exception{
 FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/vista/Registro.fxml"));
Parent root1= (Parent)fxmlLoader.load();
Stage stage= new Stage();
stage.setScene(new Scene(root1));
stage.setTitle("Registro de Ventas");
stage.show();


}

@FXML
void ventanaAyuda () throws Exception{
 FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/vista/Ayuda.fxml"));
Parent root1= (Parent)fxmlLoader.load();
Stage stage= new Stage();
stage.setScene(new Scene(root1));
stage.setTitle("Ayuda");
stage.show();


}

//////////////REPORT  TICKET
@FXML
void reporte()throws Exception{
    
if(! dataCompras.isEmpty()){
           
 DBCon con = new DBCon();
 Connection conn = con.Connect(); 
InputStream archivo = getClass().getResourceAsStream("/reportes/ticket.jrxml");
JasperDesign dise = JRXmlLoader.load (archivo);
JasperReport jr = JasperCompileManager.compileReport(dise);
 JasperPrint jp = JasperFillManager.fillReport(jr,null,conn);
 JasperViewer.viewReport(jp,false);
 con.desconectar();
}
}

}
