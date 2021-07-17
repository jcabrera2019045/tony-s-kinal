package org.javiercabrera.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.javiercabrera.system.Principal;


public class MenuPrincipalController implements Initializable {
    private Principal escenarioPrincipal;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
   
    public void ventanaEmpresa(){
        escenarioPrincipal.ventanaEmpresa();
    }
    
    public void ventanaTempleado(){
        escenarioPrincipal.ventanaTempleado();
    }
    
    public void ventanaEmpleado(){
        escenarioPrincipal.ventanaEmpleado();
    }
    
    public void ventanaServicios(){
        escenarioPrincipal.ventanaServicios();
    }
    
    public void ventanaTplato(){
        escenarioPrincipal.ventanaTplato();
    }
    
    public void ventanaProductos(){
        escenarioPrincipal.ventanaProductos();
    }
    
    public void ventanaPlatos(){
        escenarioPrincipal.ventanaPlatos();
    }
    
    public void ventanaModulo(){
        escenarioPrincipal.ventanaModulo();
    }
    
    public void ventanaAcercaDe(){
        escenarioPrincipal.ventanaAcercaDe();
    }
    
    public void ventanaServicioPlatos(){
        escenarioPrincipal.ventanaServicioPlatos();
    }
    
    public void ventanaUnion(){
        escenarioPrincipal.ventanaUnion();
    }
    
    public void ventanaProductoPlatos(){
        escenarioPrincipal.ventanaProductoPlatos();
    }
    
    public void ventanaServicioEmpleados(){
        escenarioPrincipal.ventanaServicioEmpleados();
    }
       
}
