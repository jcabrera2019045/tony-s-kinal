package org.javiercabrera.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.javiercabrera.system.Principal;

public class UnionController implements Initializable{
    
    private Principal escenarioPrincipal;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
    public void ventanaServicioPlatos(){
        escenarioPrincipal.ventanaServicioPlatos();
    }
    
    public void ventanaProductoPlatos(){
        escenarioPrincipal.ventanaProductoPlatos();
    }
    
    public void ventanaServicioEmpleados(){
        escenarioPrincipal.ventanaServicioEmpleados();
    }
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
}
