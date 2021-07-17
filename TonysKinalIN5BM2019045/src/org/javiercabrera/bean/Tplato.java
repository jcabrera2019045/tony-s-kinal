package org.javiercabrera.bean;

public class Tplato {
    private int codigoTplato;
    private String descripcion;
    
    public Tplato() {
    }

    public Tplato(int codigoTplato, String descripcion) {
        this.codigoTplato = codigoTplato;
        this.descripcion = descripcion;
    }

    public int getCodigoTplato() {
        return codigoTplato;
    }

    public void setCodigoTplato(int codigoTplato) {
        this.codigoTplato = codigoTplato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String toString(){
        return getCodigoTplato() + " | " + getDescripcion();
    }
    
}
