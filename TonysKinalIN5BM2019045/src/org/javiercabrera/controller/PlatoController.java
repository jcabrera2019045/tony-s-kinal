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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.javiercabrera.bd.Conexion;
import org.javiercabrera.bean.Plato;
import org.javiercabrera.bean.Tplato;
import org.javiercabrera.system.Principal;

public class PlatoController implements Initializable{
    
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList <Plato> listaPlatos;
    private ObservableList <Tplato> listaTplato;
    @FXML private Label lblCodigo;
    @FXML private Label lblTplato;
    @FXML private ComboBox cmbTplato;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtPrecio;
    @FXML private TableView tblPlatos;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colPrecio;
    @FXML private TableColumn colTipo;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    public void desactivarControles(){
        lblCodigo.setDisable(false);
        lblTplato.setDisable(false);
        txtCodigo.setEditable(false);
        txtNombre.setEditable(false);
        txtCantidad.setEditable(false);
        txtDescripcion.setEditable(false);
        txtPrecio.setEditable(false);
        cmbTplato.setDisable(true);
    }
     
    public void activarControles(){
        lblCodigo.setDisable(true);
        txtCodigo.setEditable(false);
        txtNombre.setEditable(true);
        txtCantidad.setEditable(true);
        txtDescripcion.setEditable(true);
        txtPrecio.setEditable(true);
        cmbTplato.setDisable(false);
    }
        
    public void limpiarControles(){
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCantidad.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        cmbTplato.getSelectionModel().clearSelection();
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
           Plato registro = new Plato();
           registro.setNombrePlato(txtNombre.getText());
           registro.setCantidad(Integer.valueOf(txtCantidad.getText()));
           registro.setDescripcionPlato(txtDescripcion.getText());
           registro.setPrecioPlato(Double.parseDouble(txtPrecio.getText()));
           registro.setCodigoTplato(((Tplato)cmbTplato.getSelectionModel().getSelectedItem()).getCodigoTplato());
           try{
                PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarPlatos(?,?,?,?,?)}");
                sp.setInt(1,registro.getCantidad());
                sp.setString(2, registro.getNombrePlato());
                sp.setString(3, registro.getDescripcionPlato());
                sp.setDouble(4, registro.getPrecioPlato());
                sp.setInt(5, registro. getCodigoTplato());
                
                sp.execute();
                listaPlatos.add(registro);
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
                    if(tblPlatos.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Eliminar registro?", "Eliminar Plato", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if(respuesta == JOptionPane.YES_OPTION){
                               try{
                                   PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarPlatos(?)}");
                                   sp.setInt(1, ((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato());
                                   sp.execute();
                                   listaPlatos.remove(tblPlatos.getSelectionModel().getSelectedIndex());
                                   limpiarControles();
                                   JOptionPane.showMessageDialog(null, "Plato eliminado exitosamente");
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
                    if(tblPlatos.getSelectionModel().getSelectedItem() != null){
                        btnEditar.setText("Actualizar");
                        btnReporte.setText("Cancelar");
                        btnNuevo.setDisable(true);
                        btnEliminar.setDisable(true);
                        activarControles();
                        cmbTplato.setDisable(true);
                        lblTplato.setDisable(true);
                        tipoDeOperacion = operaciones.ACTUALIZAR;
                    }else{
                        JOptionPane.showMessageDialog(null,"Selecione el plato a actualizar");
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
                    tipoDeOperacion = PlatoController.operaciones.NINGUNO;
                    cargarDatos();
                break;
                    
                    
            }
        }
        
        public void Actualizar(){
                try{
                PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarPlatos(?,?,?,?,?,?)}");
                Plato registro = ((Plato)tblPlatos.getSelectionModel().getSelectedItem());
                registro.setNombrePlato(txtNombre.getText());
                registro.setCantidad(Integer.valueOf(txtCantidad.getText()));
                registro.setDescripcionPlato(txtDescripcion.getText());
                registro.setPrecioPlato(Double.parseDouble(txtPrecio.getText()));
                registro.setCodigoTplato(((Tplato)cmbTplato.getSelectionModel().getSelectedItem()).getCodigoTplato());
                sp.setInt(1,registro.getCantidad());
                sp.setString(2, registro.getNombrePlato());
                sp.setString(3, registro.getDescripcionPlato());
                sp.setDouble(4, registro.getPrecioPlato());
                sp.setInt(5, registro.getCodigoTplato());
                sp.setInt(6, registro.getCodigoPlato());
                sp.execute();
                JOptionPane.showMessageDialog(null, "Datos Actualizados");
                cargarDatos();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        public Tplato buscarTplato (int codigoTplato){
        Tplato resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTplato(?)}");
            procedimiento.setInt(1, codigoTplato);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
            resultado = new Tplato (registro.getInt("codigoTplato"),
                                    registro.getString("descripcion"));
        }
        }catch (Exception e){
            e.printStackTrace();
        }

        return resultado;
    }
        
    public void seleccionarElementos(){
        if(tblPlatos.getSelectionModel().getSelectedItem() !=null){
        txtCodigo.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
        txtNombre.setText((((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getNombrePlato()));
        txtCantidad.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCantidad()));
        txtDescripcion.setText((((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getDescripcionPlato()));
        txtPrecio.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getPrecioPlato()));
        cmbTplato.getSelectionModel().select(buscarTplato(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoTplato()));
        }else{
            
        }
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbTplato.setItems(getTplato());
    }
    
    public void cargarDatos(){
        tblPlatos.setItems(getPlatos());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Plato, Integer> ("codigoPlato"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<Plato, Integer> ("cantidad"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Plato, String> ("nombrePlato"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Plato, String> ("descripcionPlato"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Plato, Double> ("precioPlato"));
        colTipo.setCellValueFactory(new PropertyValueFactory<Plato, Integer> ("codigoTplato"));
        desactivarControles();
    }
    
     public ObservableList<Plato> getPlatos(){
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
        
        return listaPlatos = FXCollections.observableArrayList(lista);
    }
     
     public ObservableList<Tplato> getTplato(){
            ArrayList<Tplato> lista = new ArrayList<Tplato>();
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTplato()}");
                ResultSet resultado = procedimiento.executeQuery();
                while(resultado.next()){
                    lista.add(new Tplato(resultado.getInt("codigoTplato"),
                                                resultado.getString("descripcion")));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return listaTplato = FXCollections.observableArrayList(lista);
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
    
    public void ventanaTplato(){
        escenarioPrincipal.ventanaTplato();
    }
    
}
