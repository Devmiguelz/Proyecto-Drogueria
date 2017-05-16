/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Conexion.Conexion;
import static Controllers.Multilista.lista;
import NodosMultilista.NodoDrogueria;
import NodosMultilista.NodoHijoDrogueria;
import static View.ListaProducto.TablaProducto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 201611277427
 */
public class Producto {

    private int codigo;
    private String descripcion;
    private String stan;
    private int cantidad;
    private float precio;
    private int estado;
    private NodoHijoDrogueria hijo;
    private NodoDrogueria padre;

    public Producto() {
        hijo = new NodoHijoDrogueria();
        padre = new NodoDrogueria();
    }

    public Producto(int codigo, String descripcion, String stan, int cantidad, float precio, int estado) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.stan = stan;
        this.cantidad = cantidad;
        this.precio = precio;
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    //Instancia Conexion BD
    Conexion conexion = new Conexion();
    Connection cn = conexion.conexion();

    public void Insertar() {
        try {
            PreparedStatement pst = cn.prepareStatement("INSERT INTO productos "
                    + "(cod_producto,descripcion,stock,precio,estado,stan)"
                    + "VALUES (?,?,?,?,?,?)");

            pst.setInt(1, getCodigo());
            pst.setString(2, getDescripcion());
            pst.setInt(3, getCantidad());
            pst.setFloat(4, getPrecio());
            pst.setFloat(5, getEstado());
            pst.setString(6, getStan());
            pst.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    public void Eliminar(int codigo) {
        try {
            PreparedStatement pst = cn.prepareStatement("DELETE FROM productos "
                    + " WHERE cod_producto ='" + codigo + "' ");
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        System.out.println("Produto Eliminado");
    }

    public void Actualizar(int codigo) {
        try {
            PreparedStatement pst = cn.prepareStatement("UPDATE productos  "
                    + " SET   cod_producto ='" + getCodigo() + "',"
                    + " descripcion ='" + getDescripcion() + "',"
                    + " stock ='" + getCantidad() + "',"
                    + " precio ='" + getPrecio() + "', "
                    + " estado ='" + getEstado() + "', "
                    + " stan ='" + getStan()+ "' "
                    + " WHERE cod_producto ='" + codigo + "' ");
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    public void Buscar(String nombre) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Stan");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        TablaProducto.setModel(modelo);
        int[] anchos = {20, 90, 20, 20, 20};
        for (int i = 0; i < TablaProducto.getColumnCount(); i++) {
            TablaProducto.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        TablaProducto.setRowHeight(20);

        String filtro = "" + nombre + "_%";
        String sql = "SELECT * FROM productos WHERE descripcion LIKE " + '"' + filtro + '"';

        String[] datos = new String[5];
        try {
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(6);
                datos[3] = rs.getString(3);
                datos[4] = rs.getString(4);
                modelo.addRow(datos);
            }
            TablaProducto.setModel(modelo);
        } catch (SQLException ex) {
            System.out.println("Error" + ex);
        }
    }

    public int EstadoProducto(int codigo) {
        NodoDrogueria buscar = lista.BuscarPadre(1);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                if (q.codigo == codigo) {
                    estado = q.estado;
                    break;
                }
                q = q.sig;
            }
        }
        return estado;
    }

    public void TablaProducto() {
        DefaultTableModel modelo = (DefaultTableModel) TablaProducto.getModel();
        int[] anchos = {20, 90, 30 ,20, 20};
        for (int i = 0; i < TablaProducto.getColumnCount(); i++) {
            TablaProducto.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        TablaProducto.setRowHeight(20);
        String[] datos = new String[5];
        NodoDrogueria buscar = lista.BuscarPadre(1);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                if (q.estado == 1) {
                    datos[0] = String.valueOf(q.codigo);
                    datos[1] = q.nombre;
                    datos[2] = q.stan;
                    datos[3] = String.valueOf(q.cantidad);
                    datos[4] = String.valueOf(q.precio);
                    modelo.addRow(datos);
                }
                q = q.sig;
            }
            TablaProducto.setModel(modelo);
        }
    }
}
