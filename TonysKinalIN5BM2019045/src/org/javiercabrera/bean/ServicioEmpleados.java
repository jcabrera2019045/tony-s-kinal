package org.javiercabrera.bean;

import java.util.Date;

public class ServicioEmpleados {
    private int codigoSempleados;
    private Date fechaEvento;
    private String horaEvento;
    private String lugarEvento;
    private int codigoServicio;
    private int codigoEmpleado;
    
    public ServicioEmpleados(){
        
    }

    public ServicioEmpleados(int codigoSempleados, Date fechaEvento, String horaEvento, String lugarEvento, int codigoServicio, int codigoEmpleado) {
        this.codigoSempleados = codigoSempleados;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.lugarEvento = lugarEvento;
        this.codigoServicio = codigoServicio;
        this.codigoEmpleado = codigoEmpleado;
    }

    public int getCodigoSempleados() {
        return codigoSempleados;
    }

    public void setCodigoSempleados(int codigoSempleados) {
        this.codigoSempleados = codigoSempleados;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(String horaEvento) {
        this.horaEvento = horaEvento;
    }

    public String getLugarEvento() {
        return lugarEvento;
    }

    public void setLugarEvento(String lugarEvento) {
        this.lugarEvento = lugarEvento;
    }

    public int getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(int codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }
    
    
    
}
