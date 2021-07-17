package org.javiercabrera.bean;


public class Templeado {
    
    private int codigoTempleado;
    private String descripcion;
    
    public Templeado(){
    }
    
    public Templeado(int codigoTempleado, String descripcion){
        this.codigoTempleado = codigoTempleado;
        this.descripcion = descripcion;
        
    }

    public int getCodigoTempleado() {
        return codigoTempleado;
    }

    public void setCodigoTempleado(int codigoTempleado) {
        this.codigoTempleado = codigoTempleado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String toString(){
        
        return getCodigoTempleado() + " | " + getDescripcion();
        
    }
    
}
