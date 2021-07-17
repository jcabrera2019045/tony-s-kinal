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
import org.javiercabrera.bean.Empleados;
import org.javiercabrera.bean.Templeado;
import org.javiercabrera.system.Principal;

public class EmpleadosController implements Initializable{
    
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList <Empleados> listaEmpleados;
    private ObservableList <Templeado> listaTempleado;
    @FXML private Label lblCodigoEmpleado;
    @FXML private Label lblTempleado;
    @FXML private TextField txtCodigoEmpleado;
    @FXML private TextField txtApellidoEmpleado;
    @FXML private TextField txtNombreEmpleado;
    @FXML private TextField txtDireccionEmpleado;
    @FXML private TextField txtTelefonoContacto;
    @FXML private TextField txtGradoCocinero;
    @FXML private ComboBox  cmbTempleado;
    @FXML private TableView tblEmpleados;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colApellidoEmpleado;
    @FXML private TableColumn colNombreEmpleado;
    @FXML private TableColumn colDireccionEmpleado;
    @FXML private TableColumn colTelefonoContacto;
    @FXML private TableColumn colGradoCocinero;
    @FXML private TableColumn colTempleado;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    public void desactivarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtApellidoEmpleado.setEditable(false);
        txtNombreEmpleado.setEditable(false);
        txtDireccionEmpleado.setEditable(false);
        txtTelefonoContacto.setEditable(false);
        txtGradoCocinero.setEditable(false);
        cmbTempleado.setDisable(true);
        lblCodigoEmpleado.setDisable(false);
        lblTempleado.setDisable(false);
    }
     
    public void activarControles(){
        txtApellidoEmpleado.setEditable(true);
        txtNombreEmpleado.setEditable(true);
        txtDireccionEmpleado.setEditable(true);
        txtTelefonoContacto.setEditable(true);
        txtGradoCocinero.setEditable(true);
        cmbTempleado.setDisable(false);
        lblCodigoEmpleado.setDisable(true);
    }
        
    public void limpiarControles(){
        txtApellidoEmpleado.setText("");
        txtNombreEmpleado.setText("");
        txtDireccionEmpleado.setText("");
        txtTelefonoContacto.setText("");
        txtGradoCocinero.setText("");
        txtCodigoEmpleado.setText("");
        cmbTempleado.getSelectionModel().clearSelection();
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

    public void activarID(){
        txtCodigoEmpleado.setVisible(true);
        lblCodigoEmpleado.setVisible(true);
    }

    public void desactivarID(){
        txtCodigoEmpleado.setVisible(false);
        lblCodigoEmpleado.setVisible(false);
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
                desactivarID();
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
                activarID();
                tipoDeOperacion = operaciones.NINGUNO;
                activarBotones();
                cargarDatos();
                break;
        }
    }
        

        public void Guardar(){
           Empleados empleadoNuevo = new Empleados();
           
           
           
           empleadoNuevo.setApellidoEmpleado(txtApellidoEmpleado.getText());
           empleadoNuevo.setNombreEmpleado(txtNombreEmpleado.getText());
           empleadoNuevo.setDireccionEmpleado(txtDireccionEmpleado.getText());
           empleadoNuevo.setTelefonoContacto(txtTelefonoContacto.getText());
           empleadoNuevo.setGradoCocinero(txtGradoCocinero.getText());
           empleadoNuevo.setCodigoTempleado(((Templeado)cmbTempleado.getSelectionModel().getSelectedItem()).getCodigoTempleado());
           try{
                PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleado(?,?,?,?,?,?)}");
                sp.setString(1,empleadoNuevo.getApellidoEmpleado());
                sp.setString(2,empleadoNuevo.getNombreEmpleado());
                sp.setString(3,empleadoNuevo.getDireccionEmpleado());
                sp.setString(4,empleadoNuevo.getTelefonoContacto());
                sp.setString(5,empleadoNuevo.getGradoCocinero());
                sp.setInt(6,empleadoNuevo.getCodigoTempleado());
                sp.execute();
                listaEmpleados.add(empleadoNuevo);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
            
        public void Eliminar(){
            switch (tipoDeOperacion){
                case GUARDAR:
                activarBotones();
                activarID();
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
                    if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Eliminar registro?", "Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if(respuesta == JOptionPane.YES_OPTION){
                               try{
                                   PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleado(?)}");
                                   sp.setInt(1, ((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                                   sp.execute();
                                   listaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedIndex());
                                   limpiarControles();
                                   JOptionPane.showMessageDialog(null, "Empleado eliminado exitosamente");
                               }catch(Exception e){
                                   e.printStackTrace();
                               }
                            }
                }else{
                        JOptionPane.showMessageDialog(null, "Seleccione el empleado a eliminar");
                        
                    }
                    
               }
        }
        
        public void Editar(){
            switch(tipoDeOperacion){
                case NINGUNO:
                    if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
                        btnEditar.setText("Actualizar");
                        btnReporte.setText("Cancelar");
                        btnNuevo.setDisable(true);
                        btnEliminar.setDisable(true);
                        activarControles();
                        cmbTempleado.setDisable(true);
                        lblTempleado.setDisable(true);
                        tipoDeOperacion = operaciones.ACTUALIZAR;
                    }else{
                        JOptionPane.showMessageDialog(null,"Selecione el empleado a actualizar");
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
        
        public void Actualizar(){
            try{
                PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarEmpleado(?,?,?,?,?,?,?)}");
                Empleados registro = ((Empleados)tblEmpleados.getSelectionModel().getSelectedItem());
                registro.setApellidoEmpleado(txtApellidoEmpleado.getText());
                registro.setNombreEmpleado(txtNombreEmpleado.getText());
                registro.setDireccionEmpleado(txtDireccionEmpleado.getText());
                registro.setTelefonoContacto(txtTelefonoContacto.getText());
                registro.setGradoCocinero(txtGradoCocinero.getText());
                registro.setCodigoTempleado(((Templeado)cmbTempleado.getSelectionModel().getSelectedItem()).getCodigoTempleado());
                sp.setInt(1, registro.getCodigoEmpleado());
                sp.setString(2, registro.getApellidoEmpleado());
                sp.setString(3, registro.getNombreEmpleado());
                sp.setString(4, registro.getDireccionEmpleado());
                sp.setString(5, registro.getTelefonoContacto());
                sp.setString(6, registro.getGradoCocinero());
                sp.setInt(7, registro.getCodigoTempleado());
                sp.execute();   
                JOptionPane.showMessageDialog(null, "Datos Actualizados");
                cargarDatos();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        
        public void Reporte(){
            switch(tipoDeOperacion){
                case ACTUALIZAR:
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    tipoDeOperacion = operaciones.NINGUNO;
                    cargarDatos();
                break;
                    
                    
            }
        }
        
        
            

    public void seleccionarElementos(){
        if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
        txtCodigoEmpleado.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtApellidoEmpleado.setText((((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidoEmpleado()));
        txtNombreEmpleado.setText((((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getNombreEmpleado()));
        txtDireccionEmpleado.setText((((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getDireccionEmpleado()));
        txtTelefonoContacto.setText((((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getTelefonoContacto()));
        txtGradoCocinero.setText((((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getGradoCocinero()));
        cmbTempleado.getSelectionModel().select(buscarTempleado(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoTempleado()));
        }else{
            
        }
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbTempleado.setItems(getTempleado());
        
    }
    
    public void cargarDatos(){
        tblEmpleados.setItems(getEmpleado());
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer> ("codigoEmpleado"));
        colApellidoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String> ("apellidoEmpleado"));
        colNombreEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String> ("nombreEmpleado"));
        colDireccionEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String> ("direccionEmpleado"));
        colTelefonoContacto.setCellValueFactory(new PropertyValueFactory<Empleados, String> ("telefonoContacto"));
        colGradoCocinero.setCellValueFactory(new PropertyValueFactory<Empleados, String> ("gradoCocinero"));
        colTempleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoTempleado"));
        desactivarControles();
    }
    
    
    
     public ObservableList<Empleados> getEmpleado(){
        ArrayList<Empleados> lista = new ArrayList<Empleados>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados}");   
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                                      resultado.getString("apellidosEmpleado"),
                                      resultado.getString("nombreEmpleado"),
                                      resultado.getString("direccionEmpleado"),
                                      resultado.getString("telefonoContacto"),
                                      resultado.getString("gradoCocinero"),
                                      resultado.getInt("codigoTempleado")));
                                                            
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaEmpleados = FXCollections.observableArrayList(lista);
    }
     
     public Templeado buscarTempleado(int codigoTempleado){
         
         Templeado resultado = null;
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarTempleado(?)}");
             procedimiento.setInt(1, codigoTempleado);
             ResultSet registro = procedimiento.executeQuery();
             while(registro.next()){
                 
                 resultado = new Templeado(registro.getInt("codigoTempleado"),
                                 registro.getString("descripcion"));
                 
             }
             
         }catch(Exception e){
             e.printStackTrace();
         }
         
         return resultado;
         
     }
     
     
    public ObservableList<Templeado> getTempleado() {
        ArrayList<Templeado> lista = new ArrayList<Templeado>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTempleado}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Templeado(resultado.getInt("codigoTempleado"),
                                        resultado.getString("descripcion")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        };
        
        return listaTempleado = FXCollections.observableArrayList(lista);
        
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }  
    
    public void ventanaTempleado(){
        escenarioPrincipal.ventanaTempleado();
    }
}
    

