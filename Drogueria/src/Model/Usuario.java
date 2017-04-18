/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Conexion.Conexion;
import static Model.Password.Descriptar;
import static View.Configuracion.TablaUsuario;
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

    public Usuario() {

    }

    public Usuario(String nombre, String user ,  String password, int tipo) {
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
        
        String sql = "SELECT id_usuario FROM  usuarios "
                + " WHERE user ='" + user + "' ";
        try {
            Statement consult = cn.createStatement();
            ResultSet rs = consult.executeQuery(sql);
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
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
        String sql = "SELECT pass FROM  usuarios "
                + " WHERE user ='" + user + "' ";
        try {
            Statement consult = cn.createStatement();
            ResultSet rs = consult.executeQuery(sql);
            if (rs.next()) {
                contra = rs.getString("pass");
            }
            if (!contra.equals("") && Password(user).equals(pass)) {
                valida = true;
            }else{
                valida = false;
            }
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
        }

        return valida;
    }
    
    public String Password(String user){
        String contra = "";
        String sql = "SELECT pass FROM  usuarios "
                + " WHERE user ='" + user + "' ";
        try {
            Statement consult = cn.createStatement();
            ResultSet rs = consult.executeQuery(sql);
            if (rs.next()) {
                contra = rs.getString("pass");
            }
            contra = Descriptar(contra);
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
        }
        
        return contra;
    }
    public int TipoUsuario(String user) {
        int usertipo = 0;
        String sql = "SELECT tipo FROM  usuarios "
                + " WHERE user ='" + user + "' ";
        try {
            Statement consult = cn.createStatement();
            ResultSet rs = consult.executeQuery(sql);
            if (rs.next()) {
                usertipo = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
        }
        return usertipo;
    }
    
    public boolean HayUsuario() {
        int cant = 0;
        String sql = " SELECT * FROM  usuarios ";
        try {
            Statement consult = cn.createStatement();
            ResultSet rs = consult.executeQuery(sql);
            while (rs.next()) {
                cant++;                
            }
            if (cant > 0) {
                valida = true;
            }else{
                valida = false;
            }
        } catch (Exception ex) {
            System.out.println("Error :" + ex);
        }
        return valida;
    }
    
    public String NombreEmpleado(String user) {
        String nombres = "";
        String sql = " SELECT nombres FROM  usuarios "
                   + " WHERE user = '"+ user +"' ";
        try {
            Statement consult = cn.createStatement();
            ResultSet rs = consult.executeQuery(sql);
            if (rs.next()) {
                nombres = rs.getString(1);
            }
        } catch (Exception ex) {
            System.out.println("Error :" + ex);
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
        String sql = "SELECT * FROM usuarios";

        String[] datos = new String[4];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(2);
                datos[1] = rs.getString(3);
                datos[2] = rs.getString(4);
                datos[3] = rs.getString(5);
                modelo.addRow(datos);
            }
            TablaUsuario.setModel(modelo);
        } catch (SQLException ex) {
            System.out.println("Error :" + ex);
        }
    }
}
