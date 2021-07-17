package org.javiercabrera.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.javiercabrera.bean.Empleados;
import org.javiercabrera.bean.Servicios;
import org.javiercabrera.bean.ServicioEmpleados;
import org.javiercabrera.bd.Conexion;
import org.javiercabrera.system.Principal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ServicioEmpleadosController implements Initializable{
    
    private Principal escenarioPrincipal;
    private enum operaciones{NINGUNO, AGREGAR,GUARDAR, ELIMINAR, CANCELAR, EDITAR, ACTUALIZAR}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    ObservableList<ServicioEmpleados> listaSE;
    ObservableList<Servicios> listaServicio;
    ObservableList<Empleados> listaEmpleado;
    private DatePicker fechaE;
    @FXML private GridPane grpFecha;
    @FXML private TextField txtHora;
    @FXML private TextField txtLugar;
    @FXML private TextField txtCodigo;
    @FXML private Label lblServicio;
    @FXML private Label lblEmpleado;
    @FXML private Label lblCodigo;
    @FXML private ComboBox cmbServicio;
    @FXML private ComboBox cmbEmpleado;
    @FXML private TableView tblSE;
    @FXML private TableColumn colFecha;
    @FXML private TableColumn colHora;
    @FXML private TableColumn colLugar;
    @FXML private TableColumn colServicio;
    @FXML private TableColumn colEmpleado;
    @FXML private TableColumn colCodigo;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        fechaE = new DatePicker(Locale.ENGLISH);
        fechaE.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fechaE.getCalendarView().todayButtonTextProperty().set("Today");
        fechaE.getStylesheets().add("/org/javiercabrera/resource/DatePicker.css");
        grpFecha.add(fechaE, 0, 0);
        cmbServicio.setItems(getServicio());
        cmbEmpleado.setItems(getEmpleado());
    }
    
    public void seleccionarElemento(){
        if(tblSE.getSelectionModel().getSelectedItem() !=null){
            txtCodigo.setText(String.valueOf(((ServicioEmpleados)tblSE.getSelectionModel().getSelectedItem()).getCodigoSempleados()));
            fechaE.selectedDateProperty().set(((ServicioEmpleados)tblSE.getSelectionModel().getSelectedItem()).getFechaEvento());
            txtHora.setText(String.valueOf(((ServicioEmpleados)tblSE.getSelectionModel().getSelectedItem()).getHoraEvento()));
            txtLugar.setText(((ServicioEmpleados)tblSE.getSelectionModel().getSelectedItem()).getLugarEvento());
            cmbServicio.getSelectionModel().select(buscarServicios(((ServicioEmpleados)tblSE.getSelectionModel().getSelectedItem()).getCodigoServicio()));
            cmbEmpleado.getSelectionModel().select(buscarEmpleado(((ServicioEmpleados)tblSE.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        }else{
            
        }
    }
    
    public Servicios buscarServicios(int codigoServicio){
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
                                            registro.getInt("codigoEmpresa") );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Empleados buscarEmpleado(int codigoEmpleado){
        Empleados resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpleado(?)}");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Empleados(registro.getInt("codigoEmpleado"),
                                      registro.getString("apellidosEmpleado"),
                                      registro.getString("nombreEmpleado"),
                                      registro.getString("direccionEmpleado"),
                                      registro.getString("telefonoContacto"),
                                      registro.getString("gradoCocinero"),
                                      registro.getInt("codigoTempleado"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public void cargarDatos(){
        tblSE.setItems(getServicioEmpleados());
        colCodigo.setCellValueFactory(new PropertyValueFactory<ServicioEmpleados, Integer>("codigoSempleados"));
        colFecha.setCellValueFactory(new PropertyValueFactory<ServicioEmpleados, Date>("fechaEvento"));
        colHora.setCellValueFactory(new PropertyValueFactory<ServicioEmpleados, String>("horaEvento"));
        colLugar.setCellValueFactory(new PropertyValueFactory<ServicioEmpleados, String>("lugarEvento"));
        colServicio.setCellValueFactory(new PropertyValueFactory<ServicioEmpleados, Integer>("codigoServicio"));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<ServicioEmpleados, Integer>("codigoEmpleado"));
        desactivarControles();
    }
    
    public ObservableList<ServicioEmpleados> getServicioEmpleados(){
        ArrayList<ServicioEmpleados> lista = new ArrayList<ServicioEmpleados>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarSempleado}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new ServicioEmpleados(resultado.getInt("codigoSempleados"),
                                                resultado.getDate("fechaEvento"),
                                                resultado.getString("horaEvento"),
                                                resultado.getString("lugarEvento"), 
                                                resultado.getInt("codigoServicio"),
                                                resultado.getInt("codigoEmpleado")));
                                                        
                                                        
                                                        
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaSE = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Servicios> getServicio(){
        toString();
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
    
    public ObservableList<Empleados> getEmpleado(){
        toString();
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
        
        return listaEmpleado = FXCollections.observableArrayList(lista);
    }
    
    public void Nuevo(){
        switch (tipoDeOperacion){
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setDisable(false);
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                btnEditar.setVisible(false);
                btnReporte.setVisible(false);
                tipoDeOperacion = operaciones.GUARDAR;
            break;
            case GUARDAR:
                    Guardar();
                    limpiarControles();
                    desactivarControles();
                    activarBotones();
                    btnNuevo.setText("Nuevo");
                    btnEliminar.setText("Eliminar");
                    btnEliminar.setDisable(false);
                    btnEditar.setDisable(false);
                    btnReporte.setDisable(false);
                    tipoDeOperacion = operaciones.NINGUNO;
                    cargarDatos();
            break;
        }
    }
    
    public void Guardar(){
        ServicioEmpleados registro = new ServicioEmpleados();
        registro.setCodigoServicio(((Servicios)cmbServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
        registro.setCodigoEmpleado(((Empleados)cmbEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
        registro.setFechaEvento(fechaE.getSelectedDate());
        registro.setHoraEvento(txtHora.getText());
        registro.setLugarEvento(txtLugar.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarSempleado(?,?,?,?,?)}");
            procedimiento.setDate(1, new java.sql.Date(registro.getFechaEvento().getTime()));
            procedimiento.setString(2, registro.getHoraEvento());
            procedimiento.setString(3, registro.getLugarEvento());
            procedimiento.setInt(4, registro.getCodigoServicio());
            procedimiento.setInt(5, registro.getCodigoEmpleado());
            procedimiento.execute();
            listaSE.add(registro);
            cargarDatos();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void Eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles(); 
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnNuevo.setDisable(false);
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(false);
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                btnEditar.setVisible(true);
                btnReporte.setVisible(true);
                tipoDeOperacion = operaciones.NINGUNO;
            break;
            default:
                if(tblSE.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"¿Esta seguro que quiere eliminar el registro?","Eliminar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION){
                    try{
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarSempleado(?)}");
                        procedimiento.setInt(1,((ServicioEmpleados)tblSE.getSelectionModel().getSelectedItem()).getCodigoSempleados());
                        procedimiento.execute();
                        listaSE.remove(tblSE.getSelectionModel().getSelectedIndex());
                        limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Debe seleccionar un elemento primero"); 
            }
            break;    
        }
    }
    
    public void Editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblSE.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    cmbEmpleado.setDisable(true);
                    cmbServicio.setDisable(true);
                    lblEmpleado.setDisable(true);
                    lblServicio.setDisable(true);
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Seleccione el dato a editar");
                }
            break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarSempleado(?,?,?,?)}");
            ServicioEmpleados registro = (ServicioEmpleados)tblSE.getSelectionModel().getSelectedItem();
            registro.setFechaEvento(fechaE.getSelectedDate());
            registro.setHoraEvento(txtHora.getText());
            registro.setLugarEvento(txtLugar.getText());
            procedimiento.setDate(1, new java.sql.Date(registro.getFechaEvento().getTime()));
            procedimiento.setString(2, registro.getHoraEvento());
            procedimiento.setString(3, registro.getLugarEvento());
            procedimiento.setInt(4, registro.getCodigoSempleados());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void Reporte(){
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                limpiarControles();
                desactivarControles();
                activarBotones();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                tipoDeOperacion = operaciones.NINGUNO;

        }
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
    
    
    public void activarControles(){
        grpFecha.setDisable(false);
        lblCodigo.setDisable(true);
        txtHora.setEditable(true);
        txtLugar.setEditable(true);
        cmbServicio.setDisable(false);
        cmbEmpleado.setDisable(false);
    }
    
    public void desactivarControles(){
        lblEmpleado.setDisable(false);
        lblServicio.setDisable(false);
        lblCodigo.setDisable(false);
        grpFecha.setDisable(true);
        txtCodigo.setEditable(false);
        txtHora.setEditable(false);
        txtLugar.setEditable(false);
        cmbServicio.setDisable(true);
        cmbEmpleado.setDisable(true);
    }
    
    public void limpiarControles(){
        fechaE.selectedDateProperty().set(null);
        txtHora.setText("");
        txtLugar.setText("");
        cmbServicio.getSelectionModel().clearSelection();
        cmbEmpleado.getSelectionModel().clearSelection();
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
