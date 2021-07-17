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
import org.javiercabrera.bean.Tplato;
import org.javiercabrera.system.Principal;

public class TplatoController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList <Tplato> listaTplato;
    @FXML private Label lblCodigo;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtDescripcion;
    @FXML private TableView tblTplato;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colDescripcion;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private Button btnPlatos;
    
        public void desactivarControles(){
        txtCodigo.setEditable(false);
        txtDescripcion.setEditable(false);
        lblCodigo.setDisable(false);
    }
     
    public void activarControles(){
        txtCodigo.setEditable(true);
        txtDescripcion.setEditable(true);
        lblCodigo.setDisable(true);
    }
        
    public void limpiarControles(){
        txtCodigo.setText("");
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
                tipoDeOperacion = TplatoController.operaciones.GUARDAR;
                break;

            case GUARDAR:
                Guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                tipoDeOperacion = TplatoController.operaciones.NINGUNO;
                activarBotones();
                cargarDatos();
                break;
        }
    }
    
    public void Guardar(){
           Tplato registro = new Tplato();
           registro.setDescripcion(txtDescripcion.getText());
           try{
                PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTplato(?)}");
                sp.setString(1,registro.getDescripcion());
                sp.execute();
                listaTplato.add(registro);
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
                btnEliminar.setText("Eliminar");
                tipoDeOperacion = TplatoController.operaciones.NINGUNO;
                break;
                default:
                    if(tblTplato.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Eliminar registro?", "Eliminar Tipo De Plato", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if(respuesta == JOptionPane.YES_OPTION){
                               try{
                                   PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTplato(?)}");
                                   sp.setInt(1, ((Tplato)tblTplato.getSelectionModel().getSelectedItem()).getCodigoTplato());
                                   sp.execute();
                                   listaTplato.remove(tblTplato.getSelectionModel().getSelectedIndex());
                                   limpiarControles();
                                   JOptionPane.showMessageDialog(null, "Tipo de plato eliminado exitosamente");
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
            if(tblTplato.getSelectionModel().getSelectedItem() != null){
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                activarControles();
                tipoDeOperacion = TplatoController.operaciones.ACTUALIZAR;
            }else{
                JOptionPane.showMessageDialog(null,"Selecione el tipo de plato a actualizar");
            }
            break;
        case ACTUALIZAR:
            Actualizar();
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            tipoDeOperacion = TplatoController.operaciones.NINGUNO;
            cargarDatos();
            break;
            }
}
        
        public void Actualizar(){
            try{
                PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarTplato(?,?)}");
                Tplato registro = ((Tplato)tblTplato.getSelectionModel().getSelectedItem());
                registro.setDescripcion(txtDescripcion.getText());
                sp.setInt(1, registro.getCodigoTplato());
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
                    tipoDeOperacion = TplatoController.operaciones.NINGUNO;
                    cargarDatos();
                    limpiarControles();
                break;
            }
        }
        
        
         public void seleccionarElementos(){
             if(tblTplato.getSelectionModel().getSelectedItem() !=null){
                txtCodigo.setText(String.valueOf(((Tplato)tblTplato.getSelectionModel().getSelectedItem()).getCodigoTplato()));
                txtDescripcion.setText((((Tplato)tblTplato.getSelectionModel().getSelectedItem()).getDescripcion()));
             }else{
                 
             }
        }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblTplato.setItems(getTplato());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Tplato, Integer> ("codigoTplato"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Tplato, String> ("Descripcion"));
        desactivarControles();
    }
    
    public ObservableList<Tplato> getTplato(){
        ArrayList<Tplato> lista = new ArrayList<Tplato>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTplato}");   
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Tplato(resultado.getInt("codigoTplato"),
                                        resultado.getString("Descripcion")));
                                                            
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
     
     public void ventanaPlatos(){
         escenarioPrincipal.ventanaPlatos();
     }
     
     public void ventanaModulo(){
        escenarioPrincipal.ventanaModulo();
    }
    
}