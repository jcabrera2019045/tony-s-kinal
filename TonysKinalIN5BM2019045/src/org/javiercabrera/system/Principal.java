package org.javiercabrera.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.javiercabrera.controller.AcercaDeController;
import org.javiercabrera.controller.EmpleadosController;
import org.javiercabrera.controller.EmpresasController;
import org.javiercabrera.controller.MenuPrincipalController;
import org.javiercabrera.controller.ModuloController;
import org.javiercabrera.controller.PlatoController;
import org.javiercabrera.controller.PresupuestoController;
import org.javiercabrera.controller.ProductoPlatosController;
import org.javiercabrera.controller.ProductosController;
import org.javiercabrera.controller.ServicioEmpleadosController;
import org.javiercabrera.controller.ServicioPlatosController;
import org.javiercabrera.controller.ServiciosController;
import org.javiercabrera.controller.TempleadoController;
import org.javiercabrera.controller.TplatoController;
import org.javiercabrera.controller.UnionController;

public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/javiercabrera/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        escenarioPrincipal.setResizable(false);
       this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Tony's Kinal App");
        escenarioPrincipal.getIcons().add(new Image("/org/javiercabrera/img/Icono.png"));
        menuPrincipal();
        escenarioPrincipal.show();
    }

    public void menuPrincipal(){
        try{
            MenuPrincipalController menuPrincipal = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",600,400);
            menuPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }  
    
     public void ventanaEmpresa(){
        try{
           EmpresasController empresa = (EmpresasController)cambiarEscena("EmpresaView.fxml",727,567);
            empresa.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     
    public void ventanaTempleado(){
        try{
           TempleadoController templeado = (TempleadoController)cambiarEscena("TipoEmpleadoView.fxml",696,499);
            templeado.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
     public void ventanaEmpleado(){
        try{
           EmpleadosController empleado = (EmpleadosController)cambiarEscena("EmpleadosView.fxml",915,732);
           empleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     
     public void ventanaServicios(){
         try{
             ServiciosController servicios = (ServiciosController) cambiarEscena("ServiciosView.fxml",822,555);
             servicios.setEscenarioPrincipal(this);
         }catch(Exception e){
             e.printStackTrace();
         }
     }
     
    public void ventanaPresupuesto(){
        try{
            PresupuestoController presupuesto = (PresupuestoController) cambiarEscena("PresupuestoView.fxml",696,549);
            presupuesto.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaTplato(){
        try{
            TplatoController tplato = (TplatoController) cambiarEscena("TipoPlatoView.fxml",696,518);
            tplato.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProductos(){
        try{
            ProductosController productos = (ProductosController) cambiarEscena("ProductosView.fxml", 696, 518);
            productos.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaPlatos(){
        try{
            PlatoController plato = (PlatoController) cambiarEscena("PlatoView.fxml", 696, 626);
            plato.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaModulo(){
        try{
            ModuloController modulo = (ModuloController) cambiarEscena("ModulosView.fxml", 700, 600);
            modulo.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
            
        }
    }
    
    public void ventanaAcercaDe(){
        try{
            AcercaDeController acercaDe = (AcercaDeController) cambiarEscena("AcercaDeView.fxml", 570, 400);
            acercaDe.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaServicioPlatos(){
        try{
          ServicioPlatosController splatos = (ServicioPlatosController) cambiarEscena("ServicioPlatoView.fxml", 696, 435);
          splatos.setEscenarioPrincipal(this);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ventanaUnion(){
        try{
            UnionController union = (UnionController) cambiarEscena("UnionView.fxml", 700, 503);
            union.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProductoPlatos(){
        try{
            ProductoPlatosController pplato = (ProductoPlatosController) cambiarEscena("ProductoPlatosView.fxml", 696, 435);
            pplato.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaServicioEmpleados(){
        try{
            ServicioEmpleadosController sempleados = (ServicioEmpleadosController) cambiarEscena("ServicioEmpleadosView.fxml", 822, 554);
            sempleados.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public Initializable cambiarEscena (String fxml,int ancho, int alto) throws Exception{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();        
        return resultado;
    }
}