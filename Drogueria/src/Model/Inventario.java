/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Conexion.Conexion;
import NodosMultilista.NodoDrogueria;
import NodosMultilista.NodoHijoDrogueria;
import static View.ListaInventario.TablaInventario;
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
public class Inventario extends Producto{
    
    private NodoHijoDrogueria hijo;
    private NodoDrogueria padre;
   
    public Inventario() {
        hijo = new NodoHijoDrogueria();
        padre = new NodoDrogueria();
    }
    
    //Instancia Conexion BD
    Conexion conexion = new Conexion();
    Connection cn = conexion.conexion();
    
    public void BuscarPorCodigo(int codigo){
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Stan");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Estado");
        TablaInventario.setModel(modelo);
        int[] anchos = {80, 80,20, 20, 20,20};
        for (int i = 0; i < TablaInventario.getColumnCount(); i++) {
            TablaInventario.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        TablaInventario.setRowHeight(20);
        
        String filtro = "" + codigo + "_%";
        String sql = "SELECT * FROM productos WHERE cod_producto LIKE " + '"' + filtro + '"';

        String[] datos = new String[6];
        try {
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(6);
                datos[3] = rs.getString(3);
                datos[4] = rs.getString(4);
                datos[5] = rs.getString(5);
                modelo.addRow(datos);
            }
            TablaInventario.setModel(modelo);
        } catch (SQLException ex) {
            System.out.println("Error" + ex);
        }
    }
    
    public void BuscarPorNombre(String nombre){
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Stan");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Estado");
        TablaInventario.setModel(modelo);
        int[] anchos = {80, 80,20, 20, 20,20};
        for (int i = 0; i < TablaInventario.getColumnCount(); i++) {
            TablaInventario.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        TablaInventario.setRowHeight(20);
        
        String filtro = "" + nombre + "_%";
        String sql = "SELECT * FROM productos WHERE descripcion LIKE " + '"' + filtro + '"';

        String[] datos = new String[6];
        try {
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(6);
                datos[3] = rs.getString(3);
                datos[4] = rs.getString(4);
                datos[5] = rs.getString(5);
                modelo.addRow(datos);
            }
            TablaInventario.setModel(modelo);
        } catch (SQLException ex) {
            System.out.println("Error" + ex);
        }
    }
    
    public boolean CantidadProducto(int codigo,int cantidad){
        boolean valida = false;
        int cant = 0;
        String sql = "SELECT stock FROM  productos "
                + " WHERE cod_producto ='" + codigo+ "' ";
        try {
            Statement consult = cn.createStatement();
            ResultSet rs = consult.executeQuery(sql);
            if (rs.next()) {
                cant = rs.getInt("stock");
            }
            if (cant >= cantidad) {
                valida = true;
            }else{
                valida = false;
            }
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
        }
        return valida;
    }
    
    public int ObtenerCantidad(int codigo){
        int cant = 0;
        String sql = "SELECT stock FROM  productos "
                + " WHERE cod_producto ='" + codigo+ "' ";
        try {
            Statement consult = cn.createStatement();
            ResultSet rs = consult.executeQuery(sql);
            if (rs.next()) {
                cant = rs.getInt("stock");
            }
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
        }
        return cant;
    }    
    
    public void TablaInventario(String orden) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Stan");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Estado");
        TablaInventario.setModel(modelo);
        int[] anchos = {80, 80, 20, 20, 20, 20};
        for (int i = 0; i < TablaInventario.getColumnCount(); i++) {
            TablaInventario.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        TablaInventario.setRowHeight(20);
        String sql;
        if (orden.equals("")) {
            sql = "SELECT * FROM productos ";
        }else{
            sql = "SELECT * FROM productos ORDER BY "+orden+" ASC";
        }
        
        String[] datos = new String[6];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(6);
                datos[3] = rs.getString(3);
                datos[4] = rs.getString(4);
                datos[5] = rs.getString(5);
                modelo.addRow(datos);
            }
            TablaInventario.setModel(modelo);
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
        }
    }
}
