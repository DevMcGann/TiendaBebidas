/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Gab
 */
public class Bebidas {
    
private int id;
private String producto;
private int precio;
private int stock;
private int cantidadCarrito;


    public Bebidas(){
        
    }

    public Bebidas(int id, String producto, int precio, int stock, int cantidadCarrito) {
        this.id = id;
        this.producto = producto;
        this.precio = precio;
        this.stock = stock;
        this.cantidadCarrito = cantidadCarrito;
    }

    public Bebidas(  String producto, int precio, int stock, int cantidadCarrito) {
        
        this.producto = producto;
        this.precio = precio;
        this.stock = stock;
        this.cantidadCarrito = cantidadCarrito;
    }
    public Bebidas( String producto, int precio, int cantidadCarrito) {
        
        this.producto = producto;
        this.precio = precio;
        this.cantidadCarrito = cantidadCarrito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCantidadCarrito() {
        return cantidadCarrito;
    }

    public void setCantidadCarrito(int cantidadCarrito) {
        this.cantidadCarrito = cantidadCarrito;
    }


    
}
