/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Conexion.Conexion;
import NodosMultilista.NodoDrogueria;
import NodosMultilista.NodoHijoDrogueria;
import static View.ListaCliente.TablaCliente;
import static View.VentanaPrincipal.lista;
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

    private NodoHijoDrogueria hijo;
    private NodoDrogueria padre;

    public Cliente() {
        hijo = new NodoHijoDrogueria();
        padre = new NodoDrogueria();
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

            pst.setString(1, getNombre());
            pst.setString(2, getApellido());
            pst.setLong(3, getIdentificacion());
            pst.setString(4, getCorreo());
            pst.setLong(5, getTelefono());
            pst.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error" + e);
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
        String[] datos = new String[5];
        int cant = nombre.length();

        NodoDrogueria buscar = lista.BuscarPadre(2);
        NodoHijoDrogueria q;

        if (buscar != null) {
            q = buscar.hijo;
            try {
                while (q != null) {
                    if (cant <= q.nombre.length()) {
                        if (nombre.equalsIgnoreCase(q.nombre.substring(0, cant))) {
                            datos[0] = q.nombre;
                            datos[1] = q.apellido;
                            datos[2] = String.valueOf(q.identificacion);
                            datos[3] = q.correo;
                            datos[4] = String.valueOf(q.telefono);
                            modelo.addRow(datos);
                        }
                    }
                    q = q.sig;
                }
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
            }
            TablaCliente.setModel(modelo);
        }
    }

    public int CantidadCliente() {
        int cant = 0;
        String sql = "SELECT * FROM  clientes ";
        try {
            Statement consult = cn.createStatement();
            ResultSet rs = consult.executeQuery(sql);
            while (rs.next()) {
                cant++;
            }
        } catch (Exception ex) {
            System.out.println("Error" + ex);
        }
        return cant;
    }

    public void TablaCliente() {
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

        String[] datos = new String[5];
        NodoDrogueria buscar = lista.BuscarPadre(2);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                datos[0] = q.nombre;
                datos[1] = q.apellido;
                datos[2] = String.valueOf(q.identificacion);
                datos[3] = q.correo;
                datos[4] = String.valueOf(q.telefono);
                modelo.addRow(datos);
                q = q.sig;
            }
            TablaCliente.setModel(modelo);
        }
    }

}
