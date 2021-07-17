package org.javiercabrera.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import org.javiercabrera.bean.Empresa;
import org.javiercabrera.report.GenerarReporte;
import org.javiercabrera.system.Principal;

public class EmpresasController implements Initializable{
    
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList <Empresa> listaEmpresa;
    @FXML private Label lblCodigoEmpresa;
    @FXML private TextField txtCodigoEmpresa;
    @FXML private TextField txtNombreEmpresa;
    @FXML private TextField txtDireccionEmpresa;
    @FXML private TextField txtTelefonoEmpresa;
    @FXML private TableView tblEmpresas;
    @FXML private TableColumn colCodigoEmpresa;
    @FXML private TableColumn colNombreEmpresa;
    @FXML private TableColumn colDireccionEmpresa;
    @FXML private TableColumn colTelefonoEmpresa;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    public void desactivarControles(){
        txtCodigoEmpresa.setEditable(false);
        txtNombreEmpresa.setEditable(false);
        txtDireccionEmpresa.setEditable(false);
        txtTelefonoEmpresa.setEditable(false);
    }
     
    public void activarControles(){
        txtNombreEmpresa.setEditable(true);
        txtDireccionEmpresa.setEditable(true);
        txtTelefonoEmpresa.setEditable(true);
    }
        
    public void limpiarControles(){
        txtCodigoEmpresa.setText("");
        txtNombreEmpresa.setText("");
        txtDireccionEmpresa.setText("");
        txtTelefonoEmpresa.setText("");
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
        txtCodigoEmpresa.setVisible(true);
        lblCodigoEmpresa.setVisible(true);
    }

    public void desactivarID(){
        txtCodigoEmpresa.setVisible(false);
        lblCodigoEmpresa.setVisible(false);
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
           Empresa empresaNueva = new Empresa();
           empresaNueva.setNombreEmpresa(txtNombreEmpresa.getText());
           empresaNueva.setDireccion(txtDireccionEmpresa.getText());
           empresaNueva.setTelefono(txtTelefonoEmpresa.getText());
           try{
                PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpresa(?,?,?)}");
                sp.setString(1,empresaNueva.getNombreEmpresa());
                sp.setString(2,empresaNueva.getDireccion());
                sp.setString(3,empresaNueva.getTelefono());
                sp.execute();
                listaEmpresa.add(empresaNueva);
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
                    if(tblEmpresas.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Eliminar registro?", "Eliminar empresa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if(respuesta == JOptionPane.YES_OPTION){
                               try{
                                   PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpresa(?)}");
                                   sp.setInt(1, ((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
                                   sp.execute();
                                   listaEmpresa.remove(tblEmpresas.getSelectionModel().getSelectedIndex());
                                   limpiarControles();
                                   JOptionPane.showMessageDialog(null, "Empresa eliminada exitosamente");
                               }catch(Exception e){
                                   e.printStackTrace();
                               }
                            }
                }else{
                        JOptionPane.showMessageDialog(null, "Seleccione la empresa a eliminar");
                        
                    }
                    
               }
        }
        
        public void Editar(){
            switch(tipoDeOperacion){
                case NINGUNO:
                    if(tblEmpresas.getSelectionModel().getSelectedItem() != null){
                        btnEditar.setText("Actualizar");
                        btnReporte.setText("Cancelar");
                        btnNuevo.setDisable(true);
                        btnEliminar.setDisable(true);
                        activarControles();
                        tipoDeOperacion = operaciones.ACTUALIZAR;
                    }else{
                        JOptionPane.showMessageDialog(null,"Selecione la empresa a actualizar");
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
                PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarEmpresa(?,?,?,?)}");
                Empresa registro = ((Empresa)tblEmpresas.getSelectionModel().getSelectedItem());
                registro.setNombreEmpresa(txtNombreEmpresa.getText());
                registro.setDireccion(txtDireccionEmpresa.getText());
                registro.setTelefono(txtTelefonoEmpresa.getText());
                sp.setInt(1, registro.getCodigoEmpresa());
                sp.setString(2, registro.getNombreEmpresa());
                sp.setString(3, registro.getDireccion());
                sp.setString(4, registro.getTelefono());
                sp.execute();
                JOptionPane.showMessageDialog(null, "Datos Actualizados");
                cargarDatos();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
    public void seleccionarElementos(){
        if(tblEmpresas.getSelectionModel().getSelectedItem() !=null){
        txtCodigoEmpresa.setText(String.valueOf(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresa()));
        txtNombreEmpresa.setText((((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getNombreEmpresa()));
        txtDireccionEmpresa.setText((((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getDireccion()));
        txtTelefonoEmpresa.setText((((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getTelefono()));
        }else{
            
        }
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblEmpresas.setItems(getEmpresa());
        colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, Integer> ("codigoEmpresa"));
        colNombreEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String> ("nombreEmpresa"));
        colDireccionEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String> ("direccion"));
        colTelefonoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String> ("telefono"));
        desactivarControles();
    }
    
     public ObservableList<Empresa> getEmpresa(){
        ArrayList<Empresa> lista = new ArrayList<Empresa>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpresas}");   
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Empresa(resultado.getInt("codigoEmpresa"),
                                      resultado.getString("nombreEmpresa"),
                                      resultado.getString("direccion"),
                                      resultado.getString("telefono")));
                                                            
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaEmpresa = FXCollections.observableArrayList(lista);
    }
     
     public void Reporte(){
         switch(tipoDeOperacion){
             case NINGUNO:
                 imprimirReporte();
                 break;
            case ACTUALIZAR:
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    tipoDeOperacion = operaciones.NINGUNO;
                    cargarDatos();
                    limpiarControles();
                break;     
         }
     }
     
     public void imprimirReporte(){
         Map parametros = new HashMap();
         parametros.put("codigoEmpresa", null);
         GenerarReporte.mostrarReporte("ReporteEmpresas.jasper", "Reporte De Empresas", null);
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
    
     public void ventanaServicios(){
        escenarioPrincipal.ventanaServicios();
    }
     
    public void ventanaPresupuesto(){
        escenarioPrincipal.ventanaPresupuesto();
    }
}

 