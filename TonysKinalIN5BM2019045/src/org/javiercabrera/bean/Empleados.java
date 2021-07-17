package org.javiercabrera.bean;


public class Empleados {
    private int codigoEmpleado;
    private String apellidoEmpleado;
    private String nombreEmpleado;
    private String direccionEmpleado;
    private String telefonoContacto;
    private String gradoCocinero;
    private int codigoTempleado;
    
    public Empleados(){
    }

    public Empleados(int codigoEmpleado, String apellidoEmpleado, String nombreEmpleado, String direccionEmpleado, String telefonoContacto, String gradoCocinero, int codigoTempleado) {
        this.codigoEmpleado = codigoEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.direccionEmpleado = direccionEmpleado;
        this.telefonoContacto = telefonoContacto;
        this.gradoCocinero = gradoCocinero;
        this.codigoTempleado = codigoTempleado;
    }
    
    

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getDireccionEmpleado() {
        return direccionEmpleado;
    }

    public void setDireccionEmpleado(String direccionEmpleado) {
        this.direccionEmpleado = direccionEmpleado;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getGradoCocinero() {
        return gradoCocinero;
    }

    public void setGradoCocinero(String gradoCocinero) {
        this.gradoCocinero = gradoCocinero;
    }

    public int getCodigoTempleado() {
        return codigoTempleado;
    }

    public void setCodigoTempleado(int codigoTempleado) {
        this.codigoTempleado = codigoTempleado;
    }

    public String toString(){
        return getCodigoEmpleado() + " | " + getNombreEmpleado();
    }
       
}

