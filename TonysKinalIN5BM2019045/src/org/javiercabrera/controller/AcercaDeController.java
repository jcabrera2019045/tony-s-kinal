package org.javiercabrera.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.javiercabrera.system.Principal;
import javafx.fxml.Initializable;

public class AcercaDeController  implements Initializable{
    
    private Principal escenarioPrincipal;
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
}
