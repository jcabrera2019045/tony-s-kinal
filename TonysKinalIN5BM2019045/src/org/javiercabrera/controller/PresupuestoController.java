package org.javiercabrera.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.javiercabrera.bean.Empresa;
import org.javiercabrera.bean.Presupuesto;
import org.javiercabrera.bd.Conexion;
import org.javiercabrera.report.GenerarReporte;
import org.javiercabrera.system.Principal;

public class PresupuestoController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO }
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <Presupuesto>listaPresupuesto;
    private ObservableList<Empresa>listaEmpresa;
    private DatePicker fechaSolicitud;
            
    private Principal escenarioPrincipal;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    
    @FXML private TableView tblPresupuestos;
    
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colFecha;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colEmpresa;
    
    @FXML private TextField txtCodigo;
    @FXML private TextField txtCantidad;
    
    @FXML private ComboBox  cmbEmpresa;
    @FXML private GridPane grpFecha;
    @FXML private Label lblCodigo;
    @FXML private Label lblEmpresa;
    
     //Metodo para cargar los datos a la vista
    public void cargarDatos(){
        
        tblPresupuestos.setItems(getPresupuesto());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Presupuesto, Integer>("codigoPresupuesto"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Presupuesto,Date >("fechaSolicitud"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<Presupuesto, Double>("cantidadPresupuesto"));
        colEmpresa.setCellValueFactory(new PropertyValueFactory<Presupuesto, Integer>("codigoEmpresa"));
        desactivarControles();
    }
    
    //Metodo para ejecutar el procedimmiento almacenado y llenar el observablelist
    public ObservableList<Presupuesto> getPresupuesto(){
        
        ArrayList<Presupuesto> lista = new ArrayList<Presupuesto>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarPresupuesto()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next()){
                
                lista.add(new Presupuesto(resultado.getInt("codigoPresupuesto"),
                        resultado.getDate("fechaSolicitud"),
                        resultado.getDouble("cantidadPresupuesto"),
                        resultado.getInt("codigoEmpresa")));
                
            }
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
        return listaPresupuesto = FXCollections.observableArrayList(lista);
        
    }
   
     public ObservableList<Empresa> getEmpresa(){
        
        ArrayList<Empresa> lista = new ArrayList<Empresa>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpresas()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next()){
                lista.add(new Empresa(resultado.getInt("codigoEmpresa"),resultado.getString("nombreEmpresa"),resultado.getString("direccion"),resultado.getString("telefono")));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaEmpresa = FXCollections.observableArrayList(lista);
    }
     
     public Empresa buscarEmpresa (int codigoEmpresa){
        Empresa resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpresa(?)}");
            procedimiento.setInt(1, codigoEmpresa);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
            resultado = new Empresa (registro.getInt("codigoEmpresa"),
                                     registro.getString("nombreEmpresa"),
                                     registro.getString("direccion"),
                                     registro.getString("telefono"));
        }
        }catch (Exception e){
            e.printStackTrace();
        }

        return resultado;
    }
    

    public void seleccionarElemento(){
        if(tblPresupuestos.getSelectionModel().getSelectedItem() !=null){
        txtCodigo.setText(String.valueOf(((Presupuesto)tblPresupuestos.getSelectionModel().getSelectedItem()).getCodigoPresupuesto()));
        fechaSolicitud.selectedDateProperty().set(((Presupuesto)tblPresupuestos.getSelectionModel().getSelectedItem()).getFechaSolicitud());
        txtCantidad.setText(String.valueOf(((Presupuesto)tblPresupuestos.getSelectionModel().getSelectedItem()).getCantidadPresupuesto()));
        cmbEmpresa.getSelectionModel().select(buscarEmpresa(((Presupuesto)tblPresupuestos.getSelectionModel().getSelectedItem()).getCodigoEmpresa()));
        }else{

        }
    }
     
     
     
     
     // Metodo para Nuevo
    public void Nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
           activarControles();
           limpiarControles();
           btnNuevo.setText("Guardar");
           btnEliminar.setText("Cancelar");
           btnEditar.setDisable(true);
           btnReporte.setDisable(true);
           tipoDeOperacion = operaciones.GUARDAR;
           break;
            case  GUARDAR:  
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }     
       
    }     
    
       public void guardar(){
        Presupuesto registro= new Presupuesto();
        registro.setFechaSolicitud(fechaSolicitud.getSelectedDate());
        registro.setCantidadPresupuesto(Double.parseDouble(txtCantidad.getText()));
        registro.setCodigoEmpresa(((Empresa)cmbEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarPresupuesto(?,?,?)}");
             procedimiento.setDate(1,new java.sql.Date(registro.getFechaSolicitud().getTime()));
             procedimiento.setDouble(2, registro.getCantidadPresupuesto());
             procedimiento.setInt(3, registro.getCodigoEmpresa());
             procedimiento.execute();
             listaPresupuesto.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
       
    public void  Eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = PresupuestoController.operaciones.NUEVO;
                break;
            default:
                if(tblPresupuestos.getSelectionModel().getSelectedItem() != null ){
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?","Eliminar Presupuesto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_OPTION){
                try{
                   PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarPresupuesto(?)}");
                   sp.setInt(1,((Presupuesto)tblPresupuestos.getSelectionModel().getSelectedItem()).getCodigoPresupuesto());
                   sp.execute(); 
                   listaPresupuesto.remove(tblPresupuestos.getSelectionModel().getSelectedIndex());
                   limpiarControles();
                   JOptionPane.showMessageDialog(null,"Presupuesto eliminado correctamente");
                   cargarDatos();
                }catch(Exception e){
                    e.printStackTrace();

                }

                }
                }else{
                JOptionPane.showMessageDialog(null,"Debe Selecionar el registro a eliminar");

                }
        }
  }
    
   public void Editar(){
    switch(tipoDeOperacion){
        case NINGUNO:
            if(tblPresupuestos.getSelectionModel().getSelectedItem() != null){
                activarControles();
                cmbEmpresa.setDisable(true);
                lblEmpresa.setDisable(true);
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                tipoDeOperacion = PresupuestoController.operaciones.ACTUALIZAR;
            }else{
                JOptionPane.showMessageDialog(null, "Debes seleccionar un elemento!");
            }
        break;
        case ACTUALIZAR:
            actualizar();
            limpiarControles();
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            tipoDeOperacion = PresupuestoController.operaciones.NINGUNO;
            cargarDatos();
        break;
    }
}

    public void Reporte(){
         switch(tipoDeOperacion){
              
             case NINGUNO:
                    if(tblPresupuestos.getSelectionModel().getSelectedItem() != null){
                    imprimirReporte();
                    }else{
                        JOptionPane.showMessageDialog(null, "Seleccione un presupuesto");
                    }
                 break;
            case ACTUALIZAR:
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    tipoDeOperacion = PresupuestoController.operaciones.NINGUNO;
                    cargarDatos();
                    limpiarControles();
                break;     
         }
     }
     
     public void imprimirReporte(){
         Map parametros = new HashMap();
         int var = ((Empresa)cmbEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa();
         parametros.put("codEmpresa", var);
         GenerarReporte.mostrarReporte("ReportePresupuesto.jasper", "Reporte De Presupuestos", parametros);
         }
    
        
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarPresupuesto(?,?,?,?)}");
            Presupuesto registro = (Presupuesto)tblPresupuestos.getSelectionModel().getSelectedItem();
            registro.setFechaSolicitud(fechaSolicitud.getSelectedDate());
            registro.setCantidadPresupuesto(Double.parseDouble(txtCantidad.getText()));
            registro.setCodigoEmpresa(((Empresa)cmbEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
            procedimiento.setDate(1,new java.sql.Date(registro.getFechaSolicitud().getTime()));
            procedimiento.setDouble(2, registro.getCantidadPresupuesto());
            procedimiento.setInt(3, registro.getCodigoEmpresa());
            procedimiento.setInt(4, registro.getCodigoPresupuesto());
            procedimiento.execute();
            JOptionPane.showMessageDialog(null, "Datos Actualizados");
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
     
          
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    fechaSolicitud = new DatePicker(Locale.ENGLISH);
        fechaSolicitud.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
         fechaSolicitud.getCalendarView().todayButtonTextProperty().set("Today");
         fechaSolicitud.getCalendarView().todayButtonTextProperty().set("Today");
         grpFecha.add(fechaSolicitud,0,0);    
         fechaSolicitud.getStylesheets().add("/org/javiercabrera/resource/DatePicker.css");
         cmbEmpresa.setItems(getEmpresa());
    }
    
    public void desactivarControles(){
    txtCodigo.setEditable(false);
    txtCantidad.setEditable(false);
    grpFecha.setDisable(true);
    cmbEmpresa.setDisable(true);
    lblCodigo.setDisable(false);
    lblEmpresa.setDisable(false);
    }
    
    public void activarControles(){
    txtCodigo.setEditable(false);
    txtCantidad.setEditable(true);
    grpFecha.setDisable(false);
    cmbEmpresa.setDisable(false); 
    lblCodigo.setDisable(true);
    }
    
    public void limpiarControles(){
    txtCodigo.setText("");
    txtCantidad.setText("");
    cmbEmpresa.getSelectionModel().clearSelection();   
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
     public void ventanaEmpresa(){
        escenarioPrincipal.ventanaEmpresa();
    }
    
}

