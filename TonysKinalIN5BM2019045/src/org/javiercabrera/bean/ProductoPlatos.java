package org.javiercabrera.bean;

public class ProductoPlatos {
    private int codigoProducto;
    private int codigoPlato;
    
    public ProductoPlatos(){
        
    }

    public ProductoPlatos(int codigoProducto, int codigoPlato) {
        this.codigoProducto = codigoProducto;
        this.codigoPlato = codigoPlato;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getCodigoPlato() {
        return codigoPlato;
    }

    public void setCodigoPlato(int codigoPlato) {
        this.codigoPlato = codigoPlato;
    }
    
    
}
