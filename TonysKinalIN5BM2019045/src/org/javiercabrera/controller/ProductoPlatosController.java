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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.javiercabrera.bean.Plato;
import org.javiercabrera.bean.Productos;
import org.javiercabrera.bean.ProductoPlatos;
import org.javiercabrera.bd.Conexion;
import org.javiercabrera.system.Principal;

public class ProductoPlatosController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones{NINGUNO, AGREGAR, GUARDAR, ELIMINAR, CANCELAR, EDITAR, ACTUALIZAR}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    ObservableList<ProductoPlatos> listaPp;
    ObservableList<Productos> listaProducto;
    ObservableList<Plato> listaPlato;
    @FXML private ComboBox cmbProducto;
    @FXML private ComboBox cmbPlato;
    @FXML private TableView tblPp;
    @FXML private TableColumn colProducto;
    @FXML private TableColumn colPlato;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        limpiarControles();
        cargarDatos();
        cmbProducto.setItems(getProducto());
        cmbPlato.setItems(getPlato());
        
    }
    
    public void seleccionarElementos(){
        if(tblPp.getSelectionModel().getSelectedItem() !=null){
        cmbProducto.getSelectionModel().select(buscarProducto(((Productos)tblPp.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        cmbPlato.getSelectionModel().select(buscarPlato(((Plato)tblPp.getSelectionModel().getSelectedItem()).getCodigoPlato()));
        }else{
            
        }
    }  
    
    public Productos buscarProducto(int codigoProducto){
        Productos resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProducto(?)}");
            procedimiento.setInt(1, codigoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Productos(registro.getInt("codigoProducto"),
                                            registro.getString("nombreProducto"),
                                            registro.getInt("cantidad"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Plato buscarPlato(int codigoPlato){
        Plato resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscaPlato(?)}");
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
    
    public void cargarDatos(){
        tblPp.setItems(getProductoPlatos());
        colProducto.setCellValueFactory(new PropertyValueFactory<ProductoPlatos, Integer>("codigoProducto"));
        colPlato.setCellValueFactory(new PropertyValueFactory<ProductoPlatos, Integer>("codigoPlato"));
    }
    
    public ObservableList<ProductoPlatos> getProductoPlatos(){
        ArrayList<ProductoPlatos> lista = new ArrayList<ProductoPlatos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPplatos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new ProductoPlatos(resultado.getInt("codigoProducto"),
                                             resultado.getInt("codigoPlato") ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaPp = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Productos> getProducto(){
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Productos(resultado.getInt("codigoProducto"),
                                        resultado.getString("nombreProducto"),
                                        resultado.getInt("cantidad")));   
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return listaProducto = FXCollections.observableArrayList(lista);
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
        cmbProducto.getSelectionModel().clearSelection();
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
