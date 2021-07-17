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
import javax.swing.JOptionPane;
import org.javiercabrera.bd.Conexion;
import org.javiercabrera.bean.Templeado;
import org.javiercabrera.system.Principal;

public class TempleadoController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList <Templeado> listaTempleado;
    @FXML private Label lblCodigoTempleado;
    @FXML private TextField txtCodigoTempleado;
    @FXML private TextField txtDescripcion;
    @FXML private TableView tblTempleado;
    @FXML private TableColumn colCodigoTempleado;
    @FXML private TableColumn colDescripcion;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private Button btnEmpleados;
    
        public void desactivarControles(){
        txtCodigoTempleado.setEditable(false);
        txtDescripcion.setEditable(false);
    }
     
    public void activarControles(){
        txtCodigoTempleado.setEditable(false);
        txtDescripcion.setEditable(true);
    }
        
    public void limpiarControles(){
        txtCodigoTempleado.setText("");
        txtDescripcion.setText("");
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
        txtCodigoTempleado.setVisible(true);
        lblCodigoTempleado.setVisible(true);
    }

    public void desactivarID(){
        txtCodigoTempleado.setVisible(false);
        lblCodigoTempleado.setVisible(false);
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
                tipoDeOperacion = TempleadoController.operaciones.GUARDAR;
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
                tipoDeOperacion = TempleadoController.operaciones.NINGUNO;
                activarBotones();
                cargarDatos();
                break;
        }
    }
    
    public void Guardar(){
           Templeado TempleadoNuevo = new Templeado();
           TempleadoNuevo.setDescripcion(txtDescripcion.getText());
           try{
                PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTempleado(?)}");
                sp.setString(1,TempleadoNuevo.getDescripcion());
                sp.execute();
                listaTempleado.add(TempleadoNuevo);
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
                tipoDeOperacion = TempleadoController.operaciones.NINGUNO;
                break;
                default:
                    if(tblTempleado.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Eliminar registro?", "Eliminar Tipo De Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if(respuesta == JOptionPane.YES_OPTION){
                               try{
                                   PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTempleado(?)}");
                                   sp.setInt(1, ((Templeado)tblTempleado.getSelectionModel().getSelectedItem()).getCodigoTempleado());
                                   sp.execute();
                                   listaTempleado.remove(tblTempleado.getSelectionModel().getSelectedIndex());
                                   limpiarControles();
                                   JOptionPane.showMessageDialog(null, "Tipo de empleado eliminado exitosamente");
                               }catch(Exception e){
                                   e.printStackTrace();
                               }
                            }
                }else{
                    JOptionPane.showMessageDialog(null, "Seleccione el dato a eliminar");
                }
        }
    }
    
public void Editar(){
    switch(tipoDeOperacion){
        case NINGUNO:
            if(tblTempleado.getSelectionModel().getSelectedItem() != null){
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                activarControles();
                tipoDeOperacion = TempleadoController.operaciones.ACTUALIZAR;
            }else{
                JOptionPane.showMessageDialog(null,"Selecione el tipo de empleado a actualizar");
            }
            break;
        case ACTUALIZAR:
            Actualizar();
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            tipoDeOperacion = TempleadoController.operaciones.NINGUNO;
            cargarDatos();
            break;
            }
}
        
        public void Actualizar(){
            try{
                PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarTempleado(?,?)}");
                Templeado registro = ((Templeado)tblTempleado.getSelectionModel().getSelectedItem());
                registro.setDescripcion(txtDescripcion.getText());
                sp.setInt(1, registro.getCodigoTempleado());
                sp.setString(2, registro.getDescripcion());
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
                    tipoDeOperacion = TempleadoController.operaciones.NINGUNO;
                    cargarDatos();
                    limpiarControles();
                break;
            }
        }
        
        
         public void seleccionarElementos(){
             if(tblTempleado.getSelectionModel().getSelectedItem() !=null){
        txtCodigoTempleado.setText(String.valueOf(((Templeado)tblTempleado.getSelectionModel().getSelectedItem()).getCodigoTempleado()));
        txtDescripcion.setText((((Templeado)tblTempleado.getSelectionModel().getSelectedItem()).getDescripcion()));
             }else{
                 
             }
        }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblTempleado.setItems(getTempleado());
        colCodigoTempleado.setCellValueFactory(new PropertyValueFactory<Templeado, Integer> ("codigoTempleado"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Templeado, String> ("Descripcion"));
        desactivarControles();
    }
    
     public ObservableList<Templeado> getTempleado(){
        ArrayList<Templeado> lista = new ArrayList<Templeado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTempleado}");   
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Templeado(resultado.getInt("codigoTempleado"),
                                        resultado.getString("Descripcion")));
                                                            
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaTempleado = FXCollections.observableArrayList(lista);
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }  
    
    public void ventanaEmpleado(){
    escenarioPrincipal.ventanaEmpleado();
    }
    
     public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
     
     public void ventanaModulo(){
        escenarioPrincipal.ventanaModulo();
    }
    
}

