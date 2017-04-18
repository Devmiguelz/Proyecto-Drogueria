/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Conexion.Conexion;
import static View.ListaCliente.TablaCliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 201611277427
 */
public class Cliente {

    private String nombre;
    private String apellido;
    private long identificacion;
    private String Correo;
    private long telefono;

    public Cliente() {

    }

    public Cliente(String nombre, String apellido, long identificacion, String Correo, long telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
        this.Correo = Correo;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setSpellido(String spellido) {
        this.apellido = spellido;
    }

    public long getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(long identificacion) {
        this.identificacion = identificacion;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    //Instancia Conexion BD
    Conexion conexion = new Conexion();
    Connection cn = conexion.conexion();
    
    
    public void Insertar() {
        try {
            PreparedStatement pst = cn.prepareStatement("INSERT INTO clientes "
                    + "(nombre,apellido,identificacion,correo,telefono) "
                    + "VALUES (?,?,?,?,?)");
            
            pst.setString(1,getNombre());
            pst.setString(2,getApellido());
            pst.setLong(3,getIdentificacion());
            pst.setString(4,getCorreo());
            pst.setLong(5,getTelefono());
            pst.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Error"+ e);
        }
        System.out.println("Agregado Correctamente");
    }

    public void Eliminar(int id) {
        try {
            PreparedStatement pst = cn.prepareStatement("DELETE FROM clientes "
                    + " WHERE identificacion ='" + id + "' ");
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    public void Actualizar(long id) {
        try {
            PreparedStatement pst = cn.prepareStatement("UPDATE clientes  "
                    + " SET   nombre ='" + getNombre() + "',"
                    + " apellido ='" + getApellido() + "',"
                    + " identificacion ='" + getIdentificacion() + "',"
                    + " correo ='" + getCorreo() + "' ,"
                    + " telefono ='" + getTelefono() + "' "
                    + " WHERE identificacion ='" + id + "' ");
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    public void Buscar(String nombre) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Identificacion");
        modelo.addColumn("Correo");
        modelo.addColumn("Telefono");
        TablaCliente.setModel(modelo);
        int[] anchos = {30, 30, 30, 100, 20};
        for (int i = 0; i < TablaCliente.getColumnCount(); i++) {
            TablaCliente.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        TablaCliente.setRowHeight(20);
        String filtro = "" + nombre + "_%";
        String sql = "SELECT * FROM clientes WHERE nombre LIKE " + '"' + filtro + '"';

        String[] datos = new String[5];
        try {
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(2);
                datos[1] = rs.getString(3);
                datos[2] = rs.getString(4);
                datos[3] = rs.getString(5);
                datos[4] = rs.getString(6);
                modelo.addRow(datos);
            }
            TablaCliente.setModel(modelo);
        } catch (SQLException ex) {
            System.out.println("Error"+ ex); 
        }
    }
    
    public  int CantidadCliente(){
        int cant = 0;
        String sql = "SELECT * FROM  clientes ";
         try {
            Statement consult = cn.createStatement();
            ResultSet rs = consult.executeQuery(sql);
            while(rs.next()){
                cant++;
            }
         }catch (Exception ex){
            System.out.println("Error"+ ex);    
        }
        return cant;
    }
    
    public  void TablaCliente() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Identificacion");
        modelo.addColumn("Correo");
        modelo.addColumn("Telefono");
        TablaCliente.setModel(modelo);
        int[] anchos = {30, 30, 30, 100, 20};
        for (int i = 0; i < TablaCliente.getColumnCount(); i++) {
            TablaCliente.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        TablaCliente.setRowHeight(20);
        String sql = "SELECT * FROM clientes ORDER BY nombre";

        String[] datos = new String[5];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(2);
                datos[1] = rs.getString(3);
                datos[2] = rs.getString(4);
                datos[3] = rs.getString(5);
                datos[4] = rs.getString(6);
                modelo.addRow(datos);
            }
            TablaCliente.setModel(modelo);
        } catch (SQLException ex) {
            System.out.println("Error"+ ex); 
        }
    }

}
