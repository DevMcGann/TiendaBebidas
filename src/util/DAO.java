
package util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Gab
 */
public class DAO {
  public void Agregar_Producto(Bebidas vo){
        DBCon conec = new DBCon();
        String sql = "INSERT INTO BEBIDAS (id,producto,precio,stock,cantidadcarro) VALUES(?,?,?,?,?);";
        PreparedStatement ps = null;
        try{
            ps = conec.Connect().prepareStatement(sql);
            ps.setInt(1, vo.getId());
            ps.setString(2, vo.getProducto());
            ps.setInt(3, vo.getPrecio());
            ps.setInt(4, vo.getStock());
            ps.setInt(5, vo.getCantidadCarrito());
           
            ps.executeUpdate();
             JOptionPane.showMessageDialog(null, "Los datos se han guardado correctamente", 
                                  "Éxito en la operación", JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException ex){
            
             JOptionPane.showMessageDialog(null, "Error al agregar Datos", 
                                  ex.getMessage(), JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error al agregar Datos", 
                                  ex.getMessage(), JOptionPane.INFORMATION_MESSAGE);
        }finally{
            try{
                ps.close();
                conec.desconectar();
            }catch(Exception ex){}
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    

    
    
/*Metodo Eliminar*//////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void Eliminar_Producto(Bebidas vo){
        DBCon conec = new DBCon();
        String sql = "DELETE FROM BEBIDAS WHERE Id = ?;";
        PreparedStatement ps = null;
        try{
            ps = conec.Connect().prepareStatement(sql);
            ps.setInt(1, vo.getId());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Entrada Eliminadade la BD", 
                                  "Producto Eliminado" + vo.getProducto(), JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al Eliminar", 
                                  ex.getMessage(), JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error al Eliminar", 
                                  ex.getMessage(), JOptionPane.INFORMATION_MESSAGE);
        }finally{
            try{
                ps.close();
                conec.desconectar();
            }catch(Exception ex){}
        }
    }
 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
    
    
public boolean esta_En_DB(String producto) {
    DBCon conec = new DBCon();
    String sql = "SELECT producto FROM BEBIDAS WHERE producto = ?";
    PreparedStatement ps = null;
    int esta = 0;
    
    
        try {
            ps = conec.Connect().prepareStatement(sql);
            ps.setString(1, producto);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) {
                esta = 1; //si no esta
            } else {
                esta = 2; // si esta
            }
        } catch(SQLException e) {
            Logger.getLogger(DAO.class.getName())
                    .log(Level.SEVERE, "Error en consulta", e);
        }
if (esta == 1){
    return false;
}else {
    return true;}
}    
    

    public void Agregar_Producto_Carrito(Bebidas vo){
        DBCon conec = new DBCon();
        String sql = "INSERT INTO ESTA_COMPRA (producto,precio,cantidad) VALUES(?,?,?);";
        PreparedStatement ps = null;
        try{
            ps = conec.Connect().prepareStatement(sql);
            ps.setString(1, vo.getProducto());
            ps.setInt(2, vo.getPrecio());
            ps.setInt(3, vo.getCantidadCarrito());
            ps.executeUpdate();
            
        }catch(SQLException ex){
            //System.out.println(ex.getMessage());
             JOptionPane.showMessageDialog(null, "Error al agregar Datos", 
                                  ex.getMessage(), JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error al agregar Datos", 
                                  ex.getMessage(), JOptionPane.INFORMATION_MESSAGE);
        }finally{
            try{
                ps.close();
                conec.desconectar();
            }catch(Exception ex){}
        }
    }
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
public boolean esta_En_Carrito(String producto) {
    DBCon conec = new DBCon();
    String sql = "SELECT producto FROM ESTA_COMPRA WHERE producto = ?";
    PreparedStatement ps = null;
    int esta = 0;
    
    
        try {
            ps = conec.Connect().prepareStatement(sql);
            ps.setString(1, producto);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) {
                esta = 1; //si no esta
            } else {
                esta = 2; // si esta
            }
        } catch(SQLException e) {
            Logger.getLogger(DAO.class.getName())
                    .log(Level.SEVERE, "Error en consulta", e);
        }
if (esta == 1){
    return false;
}else {
    return true;}
}


/////////update stock DB Bebidas///////////
public void update_Stock(Bebidas be){
    
    DBCon conec = new DBCon();
   String sql= "UPDATE BEBIDAS SET  stock=?" + "WHERE producto=?";
    PreparedStatement ps = null; 
    try {
            ps = conec.Connect().prepareStatement(sql);
            ps.setInt(1, be.getStock());
            ps.setString(2, be.getProducto());
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
conec.desconectar();
}

public int Stock_Bebida(Bebidas be,String prod){
    int salida=0;
   DBCon conec = new DBCon();
   String sql= "SELECT stock,producto FROM BEBIDAS" + " WHERE producto LIKE"+"'"+prod+"'";
    PreparedStatement ps = null; 
    try {
           ps = conec.Connect().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
   
           while ( rs.next() ) {
                salida = rs.getInt("stock");
                
                
            }
} catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
conec.desconectar();
return salida;

}
//////// Update precio 
public void update_Precio(Bebidas be){
    
    DBCon conec = new DBCon();
   String sql= "UPDATE BEBIDAS SET  Precio=?" + "WHERE producto=?";
    PreparedStatement ps = null; 
    try {
            ps = conec.Connect().prepareStatement(sql);
            ps.setInt(1, be.getPrecio());
            ps.setString(2, be.getProducto());
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

}


//buscar DB Bebidas
public void buscar_Producto (Bebidas vo, String producto){
    DBCon conec = new DBCon();
    String sql = "SELECT producto FROM BEBIDAS WHERE producto = ?";
    PreparedStatement ps = null;
    int esta = 0;
    
    
        try {
            ps = conec.Connect().prepareStatement(sql);
            ps.setString(1, producto);
            ResultSet rs = ps.executeQuery();
            
        } catch(SQLException e) {
            Logger.getLogger(DAO.class.getName())
                    .log(Level.SEVERE, "Error en consulta", e);
        }
    
}




/////////update cantidad en DB esta_compra///////////
public void update_Cantidad(Bebidas vo){
   DBCon conec = new DBCon();
   String sql= "UPDATE BEBIDAS SET  cantidadcarro=?" + "WHERE producto=?";
    PreparedStatement ps = null; 
    try {
            ps = conec.Connect().prepareStatement(sql);
            ps.setInt(1, vo.getCantidadCarrito() );
            ps.setString(2, vo.getProducto());
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

}


/////////update cantidad en DB esta_compra///////////
public void update_Cantidad_Carro(Bebidas vo){
   DBCon conec = new DBCon();
   String sql= "UPDATE ESTA_COMPRA SET  cantidad=?" + "WHERE producto=?";
    PreparedStatement ps = null; 
    try {
            ps = conec.Connect().prepareStatement(sql);
            ps.setInt(1, vo.getCantidadCarrito() );
            ps.setString(2, vo.getProducto());
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

}

/////wipear Esta_Compra
public void wipear_EstaCombra(){
   DBCon conec = new DBCon();
    String sql = "DELETE * from ESTA_COMPRA;"; 
    PreparedStatement ps = null;
        try {
            ps = conec.Connect().prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
}

public void wipear_CantidadCarrito(){
   DBCon conec = new DBCon();
    String sql = "UPDATE BEBIDAS SET  cantidadcarro=0" ; 
    PreparedStatement ps = null;
        try {
            ps = conec.Connect().prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
}

//setear stock en 1000 para probar
public void refill_Stockprueba(){
   DBCon conec = new DBCon();
    String sql = "UPDATE BEBIDAS SET  stock=1000" ; 
    PreparedStatement ps = null;
        try {
            ps = conec.Connect().prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
}


 public void Eliminar_Producto_Carrito(Bebidas vo){
        DBCon conec = new DBCon();
        String sql = "DELETE FROM ESTA_COMPRA WHERE producto = ?;";
        PreparedStatement ps = null;
        try{
            ps = conec.Connect().prepareStatement(sql);
            ps.setString(1, vo.getProducto());
            ps.executeUpdate();
           
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al Eliminar", 
                                  ex.getMessage(), JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error al Eliminar", 
                                  ex.getMessage(), JOptionPane.INFORMATION_MESSAGE);
        }finally{
            try{
                ps.close();
                conec.desconectar();
            }catch(Exception ex){}
        }
    }

 
 
 
 
 
 
 
 
 ///////////////////////  TABLA VENTAS_HOY/////////////////////////////////////
 
  public void Agregar_Producto_Comprahoy(Bebidas vo){
        DBCon conec = new DBCon();
        String sql = "INSERT INTO VENTAS_HOY (producto,precio,cantidad) VALUES(?,?,?);";
        PreparedStatement ps = null;
        try{
            ps = conec.Connect().prepareStatement(sql);
            ps.setString(1, vo.getProducto());
            ps.setInt(2, vo.getPrecio());
            ps.setInt(3, vo.getCantidadCarrito());
            ps.executeUpdate();
            
        }catch(SQLException ex){
            //System.out.println(ex.getMessage());
             JOptionPane.showMessageDialog(null, ex.getMessage(), 
                                  "Error al Agregar Datos DAO", JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), 
                                  "Error Exception DAO", JOptionPane.INFORMATION_MESSAGE);
        }finally{
            try{
                ps.close();
                conec.desconectar();
            }catch(Exception ex){}
        }
    }
  
 public void wipear_Ventas_Hoy(){
   DBCon conec = new DBCon();
    String sql = "DELETE * from VENTAS_HOY;"; 
    PreparedStatement ps = null;
        try {
            ps = conec.Connect().prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
}
 
 
  public boolean esta_En_Ventas_Hoy(String producto) {
    DBCon conec = new DBCon();
    String sql = "SELECT producto FROM VENTAS_HOY WHERE producto = ?";
    PreparedStatement ps = null;
    int esta = 0;
    
    
        try {
            ps = conec.Connect().prepareStatement(sql);
            ps.setString(1, producto);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) {
                esta = 1; //si no esta
            } else {
                esta = 2; // si esta
            }
        } catch(SQLException e) {
            Logger.getLogger(DAO.class.getName())
                    .log(Level.SEVERE, "Error en consulta", e);
        }
if (esta == 1){
    return false;
}else {
    return true;}
}
 
 
 
 
/////////update cantidad en DB VENTAS HOY///////////**************************************  
public void update_Cantidad_Ventas(String producto, int cantidadenelcarro){
   DBCon conec = new DBCon();
   String sql= "UPDATE VENTAS_HOY SET  cantidad=?" +  " WHERE producto LIKE"+"'"+producto+"'";
    PreparedStatement ps = null; 
    try {
            ps = conec.Connect().prepareStatement(sql);
            ps.setInt(1, cantidadenelcarro );
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
conec.desconectar();
}
 
public int Cantidad_Ventas(Bebidas be,String prod){
    int salida=0;
   DBCon conec = new DBCon();
   String sql= "SELECT cantidad,producto FROM VENTAS_HOY" + " WHERE producto LIKE"+"'"+prod+"'";
    PreparedStatement ps = null; 
    try {
           ps = conec.Connect().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
   
           while ( rs.next() ) {
                salida = rs.getInt("cantidad");
                
                
            }
} catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
conec.desconectar();
return salida;

}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////ASUHD)(ADAWYDAW(YDP)AYWDP(AWYDP)AWYDP)(AWYDP)(AWYDP)AYDW




public int total_Ventashoy(Bebidas be,String prod){
    int salida=0;
   DBCon conec = new DBCon();
   String sql= "SELECT precio,producto FROM VENTAS_HOY" + " WHERE producto LIKE"+"'"+prod+"'";
    PreparedStatement ps = null; 
    try {
           ps = conec.Connect().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
   
           while ( rs.next() ) {
                salida = rs.getInt("precio");
                
                
            }
} catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
conec.desconectar();
return salida;

}






 
}//class
