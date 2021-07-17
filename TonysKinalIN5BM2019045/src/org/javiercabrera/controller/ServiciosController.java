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
import org.javiercabrera.system.Principal;
import org.javiercabrera.bean.Servicios;
import org.javiercabrera.bean.Empresa;
import org.javiercabrera.bd.Conexion;
import org.javiercabrera.report.GenerarReporte;
public class ServiciosController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {NUEVO ,GUARDAR,CANCELAR,EDITAR,REPORTE, ACTUALIZAR, NINGUNO};
    private operaciones tipoDeOperacion =  operaciones.NINGUNO;
    private ObservableList <Servicios> listaServicio;
    private ObservableList <Empresa> listaEmpresa;
    private DatePicker fechaServicios;
    @FXML private Label lblCodigoServicios;
    @FXML private Label lblEmpresa;
    @FXML private TextField txtCodigoServicios;
    @FXML private TextField txtHoraServicios;
    @FXML private TextField txtLugarServicios;
    @FXML private TextField txtTelefonoContacto;
    @FXML private TextField txtTipoServicios;
    @FXML private GridPane grpFechaServicios;
    
    @FXML private ComboBox cmbEmpresaServicios;
     
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    @FXML private TableView tblServicios;
    @FXML private TableColumn colCodigoServicios;
    @FXML private TableColumn colHoraServicios;
    @FXML private TableColumn colLugarServicios;
    @FXML private TableColumn colTelefonoContacto;
    @FXML private TableColumn colTipoServicios;
    @FXML private TableColumn colFechaServicios;
    @FXML private TableColumn colEmpresaServicios;
    
  @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        fechaServicios = new DatePicker(Locale.ENGLISH);
        fechaServicios.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fechaServicios.getCalendarView().todayButtonTextProperty().set("Today");
        fechaServicios.getCalendarView().setShowWeeks(false);
        fechaServicios.getStylesheets().add("/org/javiercabrera/resource/DatePicker.css");
        grpFechaServicios.add(fechaServicios,0,0);
        cmbEmpresaServicios.setItems(getEmpresa());
    }
    
     //Metodo para cargar los datos a la vista
    public void cargarDatos(){
        
        tblServicios.setItems(getServicios());
        colCodigoServicios.setCellValueFactory(new PropertyValueFactory<Servicios,Integer>("codigoServicio"));
        colFechaServicios.setCellValueFactory(new PropertyValueFactory<Servicios,Date>("fechaServicio"));
        colTipoServicios.setCellValueFactory(new PropertyValueFactory<Servicios,String>("tipoServicio"));
        colHoraServicios.setCellValueFactory(new PropertyValueFactory<Servicios,String>("horaServicio"));
        colLugarServicios.setCellValueFactory(new PropertyValueFactory<Servicios,String>("lugarServicio"));
        colTelefonoContacto.setCellValueFactory(new PropertyValueFactory<Servicios,String>("telefonoContacto"));
        colEmpresaServicios.setCellValueFactory(new PropertyValueFactory<Servicios,Integer>("codigoEmpresa")); 
        desactivarControles();
    }
    public ObservableList <Servicios> getServicios(){
        ArrayList<Servicios> lista=new ArrayList <Servicios>();
        try{
           PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ListarServicios}");
           ResultSet resultado=procedimiento.executeQuery();
           while(resultado.next()){
               lista.add(new Servicios( resultado.getInt("codigoServicio"),
                                        resultado.getDate("fechaServicio"),
                                        resultado.getString("tipoServicio"),
                                        resultado.getString("horaServicio"),
                                        resultado.getString("lugarServicio"),
                                        resultado.getString("telefonoContacto"),                                                                              
                                        resultado.getInt("codigoEmpresa")
               
               ));
           }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaServicio=FXCollections.observableArrayList(lista);                               
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
         if(tblServicios.getSelectionModel().getSelectedItem() !=null){
        txtCodigoServicios.setText(String.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getCodigoServicio()));
        txtTipoServicios.setText(String.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getTipoServicio()));
        txtHoraServicios.setText(String.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getHoraServicio()));
        txtLugarServicios.setText(String.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getLugarServicio()));
        txtTelefonoContacto.setText(String.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getTelefonoContacto()));                                                                          
        cmbEmpresaServicios.getSelectionModel().select(buscarEmpresa(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getCodigoEmpresa()));
        fechaServicios.selectedDateProperty().set(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getFechaServicio());
         }else{
             
         }
    }
    
      public void desactivarControles(){
        txtCodigoServicios.setEditable(false);
        grpFechaServicios.setDisable(true);
        txtTipoServicios.setEditable(false);
        txtHoraServicios.setEditable(false);
        txtLugarServicios.setEditable(false);
        txtTelefonoContacto.setEditable(false);                                                                          
        lblCodigoServicios.setDisable(false);
        lblEmpresa.setDisable(false);
        cmbEmpresaServicios.setDisable(true);
                   
    }
    public void activarControles(){
        grpFechaServicios.setDisable(false);
        txtTipoServicios.setEditable(true);
        txtHoraServicios.setEditable(true);
        txtLugarServicios.setEditable(true);
        txtTelefonoContacto.setEditable(true);    
        cmbEmpresaServicios.setDisable(false);
        lblCodigoServicios.setDisable(true);
                   
    }
    public void limpiarControles(){
        txtCodigoServicios.setText("");
        txtTipoServicios.setText("");
        txtHoraServicios.setText("");
        txtLugarServicios.setText("");
        txtTelefonoContacto.setText("");                                                                          
        cmbEmpresaServicios.getSelectionModel().clearSelection();
    }
    
    
     public void Nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnReporte.setDisable(true);
                btnEditar.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;
            break;
            case GUARDAR:
                guardar(); 
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setDisable(false);
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void guardar(){
        Servicios registro= new Servicios();
        registro.setFechaServicio(fechaServicios.getSelectedDate());
        registro.setTipoServicio(txtTipoServicios.getText());
        registro.setHoraServicio(txtHoraServicios.getText());
        registro.setLugarServicio(txtLugarServicios.getText());
        registro.setTelefonoContacto(txtTelefonoContacto.getText());
        registro.setCodigoEmpresa(((Empresa)cmbEmpresaServicios.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarServicios(?,?,?,?,?,?)}");
             procedimiento.setDate(1,new java.sql.Date(registro.getFechaServicio().getTime()));
             procedimiento.setString(2, registro.getTipoServicio());
             procedimiento.setString(3, registro.getHoraServicio());
             procedimiento.setString(4, registro.getLugarServicio());
             procedimiento.setString(5, registro.getTelefonoContacto());
             procedimiento.setInt(6, registro.getCodigoEmpresa());
             procedimiento.execute();
             listaServicio.add(registro);
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
          tipoDeOperacion = operaciones.NUEVO;
          break;
      default:
          // Verificar que tenga selecionado un registro de la tabla
          if(tblServicios.getSelectionModel().getSelectedItem() != null ){
          int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?","Eliminar Servicio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if(respuesta == JOptionPane.YES_OPTION){
          try{
             PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarServicio(?)}");
             sp.setInt(1,((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getCodigoServicio());
             sp.execute(); 
             listaServicio.remove(tblServicios.getSelectionModel().getSelectedIndex());
             limpiarControles();
             JOptionPane.showMessageDialog(null,"Servicio eliminado correctamente");
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
                if(tblServicios.getSelectionModel().getSelectedItem() != null){
                    activarControles();
                    cmbEmpresaServicios.setDisable(true);
                    lblEmpresa.setDisable(true);
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    tipoDeOperacion = operaciones.ACTUALIZAR;
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
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
   
        public void Reporte(){
         switch(tipoDeOperacion){
             case NINGUNO:
                 if (tblServicios.getSelectionModel().getSelectedItem() != null){
                 imprimirReporte();
                 } else{
                     JOptionPane.showMessageDialog(null, "Seleccione un servicio");
                 }
                 break;
            case ACTUALIZAR:
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    tipoDeOperacion = ServiciosController.operaciones.NINGUNO;
                    cargarDatos();
                    limpiarControles();
                break;     
         }
     }
     
     public void imprimirReporte(){
         Map parametros = new HashMap();
         int var;
         var = (Integer.valueOf(txtCodigoServicios.getText()));
         parametros.put("codServicio", var);
         GenerarReporte.mostrarReporte("ReporteServicios.jasper", "Reporte De Servicios", parametros);
     }
        
    
    
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarServicios(?,?,?,?,?,?,?)}");
            Servicios registro = ((Servicios)tblServicios.getSelectionModel().getSelectedItem());
            registro.setFechaServicio(fechaServicios.getSelectedDate());
            registro.setTipoServicio(txtTipoServicios.getText());
            registro.setHoraServicio(txtHoraServicios.getText());
            registro.setLugarServicio(txtLugarServicios.getText());
            registro.setTelefonoContacto(txtTelefonoContacto.getText());
            registro.setCodigoEmpresa(((Empresa)cmbEmpresaServicios.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
            procedimiento.setDate(1,new java.sql.Date(registro.getFechaServicio().getTime()));
            procedimiento.setString(2, registro.getTipoServicio());
            procedimiento.setString(3, registro.getHoraServicio());
            procedimiento.setString(4, registro.getLugarServicio());
            procedimiento.setString(5, registro.getTelefonoContacto());
            procedimiento.setInt(6, registro.getCodigoEmpresa());
            procedimiento.setInt(7, registro.getCodigoServicio());
            
            procedimiento.execute();
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
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
    public void ventanaEmpresa(){
        escenarioPrincipal.ventanaEmpresa();
    }
  
    
    
}