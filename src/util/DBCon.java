/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gab
 */
public class DBCon {
    Connection conn = null;
 public Connection Connect() {
        try {
            //Your database url string,ensure it is correct
            String url = "jdbc:ucanaccess://D:/VENTA_BEBIDAS2.mdb";
            
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection(url);
            //System.out.print("Conexion exitosa con la DB");
            return conn;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBCon.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    } 
 
 public void desconectar(){
        conn = null;
    }    
}