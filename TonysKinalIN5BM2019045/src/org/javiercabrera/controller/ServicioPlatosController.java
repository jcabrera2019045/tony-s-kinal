package org.javiercabrera.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.javiercabrera.bean.Plato;
import org.javiercabrera.bean.Servicios;
import org.javiercabrera.bean.ServicioPlatos;
import org.javiercabrera.bd.Conexion;
import org.javiercabrera.system.Principal;


public class ServicioPlatosController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NINGUNO, GUARDAR, EDITAR, ACTUALIZAR, CANCELAR, ELIMINAR, AGREGAR}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    ObservableList<ServicioPlatos> listaSP;
    ObservableList<Servicios> listaServicio;
    ObservableList<Plato> listaPlato;
    
    @FXML private ComboBox cmbServicio;
    @FXML private ComboBox cmbPlato;
    @FXML private TableView tblSP;
    @FXML private TableColumn colServicio;
    @FXML private TableColumn colPlato;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        limpiarControles();
        cargarDatos();
        cmbServicio.setItems(getServicio());
        cmbPlato.setItems(getPlato());
    }
    
    public void seleccionarElementos(){
        if(tblSP.getSelectionModel().getSelectedItem() !=null){
        cmbServicio.getSelectionModel().select(buscarServicio(((Servicios)tblSP.getSelectionModel().getSelectedItem()).getCodigoServicio()));
        cmbPlato.getSelectionModel().select(buscarPlato(((Plato)tblSP.getSelectionModel().getSelectedItem()).getCodigoPlato()));
        }else{
            
        }
    }
    
    public void cargarDatos(){
        tblSP.setItems(getServicioPlatos());
        colServicio.setCellValueFactory(new PropertyValueFactory <ServicioPlatos, Integer>("codigoServicio"));
        colPlato.setCellValueFactory(new PropertyValueFactory<ServicioPlatos, Integer>("codigoPlato"));
    }
    
    public Servicios buscarServicio(int codigoServicio){
        Servicios resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarServicio(?)}");
            procedimiento.setInt(1, codigoServicio);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Servicios(registro.getInt("codigoServicio"),
                                            registro.getDate("fechaServicio"),
                                            registro.getString("tipoServicio"),
                                            registro.getString("horaServicio"),
                                            registro.getString("lugarServicio"),
                                            registro.getString("telefonoContacto"),
                                            registro.getInt("codigoEmpresa"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Plato buscarPlato(int codigoPlato){
        Plato resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarPlatos(?)}");
            procedimiento.setInt(1, codigoPlato);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Plato(registro.getInt("codigoPlato"),
                                        registro.getInt("cantidad"),
                                        registro.getString("nombrePlato"),
                                        registro.getString("descripcionPlato"),
                                        registro.getDouble("precioPlato"),
                                        registro.getInt("codigoTplato"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    
    
    
    
    public ObservableList<ServicioPlatos> getServicioPlatos(){
        ArrayList<ServicioPlatos> lista = new ArrayList<ServicioPlatos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarSplatos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new ServicioPlatos(resultado.getInt("codigoServicio"),
                                             resultado.getInt("codigoPlato")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaSP = FXCollections.observableArrayList(lista);
    }
    
     public ObservableList<Servicios> getServicio(){
        ArrayList<Servicios> lista = new ArrayList<Servicios>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarServicios}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Servicios(resultado.getInt("codigoServicio"),
                                        resultado.getDate("fechaServicio"),
                                        resultado.getString("tipoServicio"),
                                        resultado.getString("horaServicio"),
                                        resultado.getString("lugarServicio"),
                                        resultado.getString("telefonoContacto"),
                                        resultado.getInt("codigoEmpresa")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaServicio = FXCollections.observableArrayList(lista);
    }
     
     public ObservableList<Plato> getPlato(){
        ArrayList<Plato> lista = new ArrayList<Plato>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPlatos}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Plato(resultado.getInt("codigoPlato"),
                                        resultado.getInt("cantidad"),
                                        resultado.getString("nombrePlato"),
                                        resultado.getString("descripcionPlato"),
                                        resultado.getDouble("precioPlato"),
                                        resultado.getInt("codigoTplato")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaPlato = FXCollections.observableArrayList(lista);
    }
    
    public void limpiarControles(){
        cmbServicio.getSelectionModel().clearSelection();
        cmbPlato.getSelectionModel().clearSelection();
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
    
    public void ventanaUnion(){
        escenarioPrincipal.ventanaUnion();
    }
    
   
}