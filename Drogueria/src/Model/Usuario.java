/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Conexion.Conexion;
import static Controllers.Multilista.lista;
import static Model.Password.Descriptar;
import NodosMultilista.NodoDrogueria;
import NodosMultilista.NodoHijoDrogueria;
import static View.Configuracion.TablaUsuario;
import static View.VentasUsuarios.TUsuarioVenta;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 201611277427
 */
public class Usuario {

    private String nombre;
    private String user;
    private String password;
    private int tipo;
    private boolean valida = false;
    private int id;

    private NodoHijoDrogueria hijo;
    private NodoDrogueria padre;

    public Usuario() {
        padre = new NodoDrogueria();
        hijo = new NodoHijoDrogueria();
    }

    public Usuario(String nombre, String user, String password, int tipo) {
        this.nombre = nombre;
        this.user = user;
        this.password = password;
        this.tipo = tipo;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    //Instancia Conexion BD
    Conexion conexion = new Conexion();
    Connection cn = conexion.conexion();

    public int ObtenerId(String user) {
        
        NodoDrogueria buscar = lista.BuscarPadre(3);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                if (q.user.equalsIgnoreCase(user)) {
                    id = q.id_usuario;
                    break;
                }
                q = q.sig;
            }
        }
        return id;
    }

    public void Insertar() {
        try {
            PreparedStatement pst = cn.prepareStatement("INSERT INTO usuarios "
                    + " (nombres,user,pass,tipo)"
                    + " VALUES (?,?,?,?)");

            pst.setString(1, getNombre());
            pst.setString(2, getUser());
            pst.setString(3, getPassword());
            pst.setInt(4, getTipo());
            pst.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    public void Eliminar(int id) {
        try {
            PreparedStatement pst = cn.prepareStatement("DELETE FROM usuarios "
                    + " WHERE id_usuario ='" + id + "' ");
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    public void Actualizar(int id) {
        try {
            PreparedStatement pst = cn.prepareStatement("UPDATE usuarios  "
                    + " SET   nombres ='" + getNombre() + "',"
                    + " user ='" + getUser() + "',"
                    + " pass ='" + getPassword() + "',"
                    + " tipo ='" + getTipo() + "' "
                    + " WHERE id_usuario ='" + id + "' ");
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    public boolean Validar(String user, String pass) {
        String contra = "";
        NodoDrogueria buscar = lista.BuscarPadre(3);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                if (q.user.equalsIgnoreCase(user)) {
                    contra = q.password;
                    break;
                }
                q = q.sig;
            }
        }
        if (!contra.equals("") && Password(user).equals(pass)) {
            valida = true;
        } else {
            valida = false;
        }
        return valida;
    }

    public String Password(String user) {
        String contra = "";
        NodoDrogueria buscar = lista.BuscarPadre(3);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                if (q.user.equalsIgnoreCase(user)) {
                    contra = q.password;
                    break;
                }
                q = q.sig;
            }
        }
        contra = Descriptar(contra);
        return contra;
    }

    public int TipoUsuario(String user) {
        int usertipo = 0;
        NodoDrogueria buscar = lista.BuscarPadre(3);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                if (q.user.equalsIgnoreCase(user)) {
                    usertipo = q.tipo;
                    break;
                }
                q = q.sig;
            }
        }
        return usertipo;
    }

    public boolean HayUsuario() {
        int cant = 0;
        NodoDrogueria buscar = lista.BuscarPadre(3);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                cant++;
                q = q.sig;
            }
        }
        if (cant > 0) {
            valida = true;
        } else {
            valida = false;
        }
        return valida;
    }

    public String NombreEmpleado(String user) {
        String nombres = "";
        NodoDrogueria buscar = lista.BuscarPadre(3);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                if (q.user.equalsIgnoreCase(user)) {
                    nombres = q.nombre;
                    break;
                }
                q = q.sig;
            }
        }
        return nombres;
    }

    public void TablaUsuario() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre");
        modelo.addColumn("Usuario");
        modelo.addColumn("Password");
        modelo.addColumn("Tipo");
        TablaUsuario.setModel(modelo);
        int[] anchos = {90, 90, 30, 20};
        for (int i = 0; i < TablaUsuario.getColumnCount(); i++) {
            TablaUsuario.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        TablaUsuario.setRowHeight(20);
        String[] datos = new String[4];

        NodoDrogueria buscar = lista.BuscarPadre(3);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                datos[0] = q.nombre;
                datos[1] = q.user;
                datos[2] = q.password;
                datos[3] = String.valueOf(q.tipo);
                modelo.addRow(datos);
                q = q.sig;
            }
            TablaUsuario.setModel(modelo);
        }
    }
    
    public void TablaUsuarioVentas() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre Empleado");
        modelo.addColumn("Total Vendido");
        TUsuarioVenta.setModel(modelo);
        int[] anchos = {90, 90};
        for (int i = 0; i < TUsuarioVenta.getColumnCount(); i++) {
            TUsuarioVenta.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        TUsuarioVenta.setRowHeight(20);
        String[] datos = new String[2];

        NodoDrogueria buscar = lista.BuscarPadre(3);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                datos[0] = q.nombre;
                datos[1] = String.valueOf(TotalVendio(q.nombre));
                modelo.addRow(datos);
                q = q.sig;
            }
            TUsuarioVenta.setModel(modelo);
        }
    }
    
    public int TotalVendio(String nombre){
        int Total = 0;
        NodoDrogueria buscar = lista.BuscarPadre(5);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                if (q.empleado.equalsIgnoreCase(nombre)) {
                    Total += q.total; 
                }
                q = q.sig;
            }
        }
        return Total;
    }
}
