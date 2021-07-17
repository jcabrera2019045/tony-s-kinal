package org.javiercabrera.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.javiercabrera.system.Principal;


public class ModuloController  implements Initializable{

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
    
   public void ventanaTempleado(){
        escenarioPrincipal.ventanaTempleado();
    }
   
   public void ventanaTplato(){
        escenarioPrincipal.ventanaTplato();
    }
    
    public void ventanaProductos(){
        escenarioPrincipal.ventanaProductos();
    }
    
     public void ventanaEmpresa(){
        escenarioPrincipal.ventanaEmpresa();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


}
