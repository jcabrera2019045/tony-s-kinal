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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javax.swing.JOptionPane;
import org.javiercabrera.bd.Conexion;
import org.javiercabrera.bean.Productos;
import org.javiercabrera.system.Principal;

public class ProductosController implements Initializable{
    
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList <Productos> listaProductos;
    @FXML private Label lblCodigo;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCantidad;
    @FXML private TableView tblProductos;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colCantidad;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    public void desactivarControles(){
        txtCodigo.setEditable(false);
        txtNombre.setEditable(false);
        txtCantidad.setEditable(false);
        lblCodigo.setDisable(false);
    }
     
    public void activarControles(){
        txtNombre.setEditable(true);
        txtCantidad.setEditable(true);
        lblCodigo.setDisable(true);
    }
        
    public void limpiarControles(){
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCantidad.setText("");
    }
        
    public void activarBotones(){
        btnNuevo.setVisible(true);
        btnEliminar.setVisible(true);
        btnEditar.setVisible(true);
        btnReporte.setVisible(true);

        btnNuevo.setDisable(false);
        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
        btnReporte.setDisable(false);
    }

    public void desactivarBotones(){
        btnNuevo.setVisible(false);
        btnEliminar.setVisible(false);
        btnEditar.setVisible(false);
        btnReporte.setVisible(false);
    }
        
        public void Nuevo(){
        switch (tipoDeOperacion){
            case NINGUNO:
                activarControles();
                desactivarBotones();
                limpiarControles();
                btnNuevo.setVisible(true);
                btnNuevo.setText("Guardar");
                btnEliminar.setVisible(true);
                btnEliminar.setText("Cancelar");
                tipoDeOperacion = operaciones.GUARDAR;
                break;

            case GUARDAR:
                Guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(true);
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.NINGUNO;
                activarBotones();
                cargarDatos();
                break;
        }
    }
        

        public void Guardar(){
           Productos registro = new Productos();
           registro.setNombreProducto(txtNombre.getText());
           registro.setCantidad(Integer.valueOf(txtCantidad.getText()));
           try{
                PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProductos(?,?)}");
                sp.setString(1,registro.getNombreProducto());
                sp.setInt(2, registro.getCantidad());
                sp.execute();
                listaProductos.add(registro);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
            
        public void Eliminar(){
            switch (tipoDeOperacion){
                case GUARDAR:
                activarBotones();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnNuevo.setDisable(false);
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(false);
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
                default:
                    if(tblProductos.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Eliminar registro?", "Eliminar Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if(respuesta == JOptionPane.YES_OPTION){
                               try{
                                   PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProducto(?)}");
                                   sp.setInt(1, ((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
                                   sp.execute();
                                   listaProductos.remove(tblProductos.getSelectionModel().getSelectedIndex());
                                   limpiarControles();
                                   JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente");
                               }catch(Exception e){
                                   e.printStackTrace();
                               }
                            }
                }else{
                        JOptionPane.showMessageDialog(null, "Seleccione el producto a eliminar");
                        
                    }
                    
               }
        }
        
        public void Editar(){
            switch(tipoDeOperacion){
                case NINGUNO:
                    if(tblProductos.getSelectionModel().getSelectedItem() != null){
                        btnEditar.setText("Actualizar");
                        btnReporte.setText("Cancelar");
                        btnNuevo.setDisable(true);
                        btnEliminar.setDisable(true);
                        activarControles();
                        tipoDeOperacion = operaciones.ACTUALIZAR;
                    }else{
                        JOptionPane.showMessageDialog(null,"Selecione el producto a actualizar");
                    }
                break;
                case ACTUALIZAR:
                    Actualizar();
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    tipoDeOperacion = operaciones.NINGUNO;
                    cargarDatos();
                break;
            }
        }
        
         public void Reporte(){
            switch(tipoDeOperacion){
                case ACTUALIZAR:
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    tipoDeOperacion = ProductosController.operaciones.NINGUNO;
                    cargarDatos();
                break;
                    
                    
            }
        }
        
        public void Actualizar(){
            try{
                PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarProductos(?,?,?)}");
                Productos registro = ((Productos)tblProductos.getSelectionModel().getSelectedItem());
                registro.setNombreProducto(txtNombre.getText());
                registro.setCantidad(Integer.valueOf(txtCantidad.getText()));
                sp.setInt(1, registro.getCodigoProducto());
                sp.setString(2, registro.getNombreProducto());
                sp.setInt(3, registro.getCantidad());
                sp.execute();
                JOptionPane.showMessageDialog(null, "Datos Actualizados");
                cargarDatos();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
    public void seleccionarElementos(){
        if(tblProductos.getSelectionModel().getSelectedItem() !=null){
        txtCodigo.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        txtNombre.setText((((Productos)tblProductos.getSelectionModel().getSelectedItem()).getNombreProducto()));
        txtCantidad.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCantidad()));
        }else{
            
        }
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblProductos.setItems(getProductos());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Productos, Integer> ("codigoProducto"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Productos, String> ("nombreProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<Productos, String> ("cantidad"));
        desactivarControles();
    }
    
     public ObservableList<Productos> getProductos(){
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos}");   
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Productos(resultado.getInt("codigoProducto"),
                                      resultado.getString("nombreProducto"),
                                      resultado.getInt("cantidad")));
                                                            
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaProductos = FXCollections.observableArrayList(lista);
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
    
    public void ventanaModulo(){
        escenarioPrincipal.ventanaModulo();
    }
    
}