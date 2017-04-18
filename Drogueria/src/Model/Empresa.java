/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author 201611277427
 */
public class Empresa {
    
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private String correo;
    
    public Empresa(){
        
    }
    
    public Empresa(String nombre, String nit, String direccion, String telefono, String correo) {
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    //Instancia Conexion BD
    Conexion conexion = new Conexion();
    Connection cn = conexion.conexion();

    public void Insertar() {
        try {
            PreparedStatement pst = cn.prepareStatement("INSERT INTO empresa "
                    + "(nombre,nit,direccion,telefono,correo)"
                    + "VALUES (?,?,?,?,?)");
            
            pst.setString(1,getNombre());
            pst.setString(2,getNit());
            pst.setString(3,getDireccion());
            pst.setString(4,getTelefono());
            pst.setString(5,getCorreo());
            pst.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Error"+ e);
        }
    }

    public void Actualizar(int id) {
        try {
            PreparedStatement pst = cn.prepareStatement("UPDATE empresa "
                    + " SET   nombre ='" + getNombre() + "',"
                    + " nit ='" + getNit() + "',"
                    + " direccion ='" + getDireccion()+ "',"
                    + " telefono ='" + getTelefono()+ "' ,"
                    + " correo ='" + getCorreo() + "' "
                    + " WHERE id_empresa ='" + id + "' ");
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }
    
    public boolean RegistroEmpresa(){
        boolean valida = false;
        int cant = 0;
        String sql = " SELECT * FROM  empresa ";
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
    
    public String DatosEmpresa(){
        String sql = "SELECT * FROM empresa";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                nombre = rs.getString(2);
                nit = rs.getString(3);
                direccion = rs.getString(4);
                telefono = rs.getString(5);
                correo = rs.getString(6);
            }
        } catch (SQLException ex) {
            System.out.println("Error :" + ex);
        }
        String Datos = "<p>Ferreteria : "+ getNombre() + "</p>"
                     + "<p>Nit : " +getNit()+"</p>"
                     + "<p>Direccion : "+ getDireccion() + "</p>"
                     + "<p>Telefono : " +getTelefono()+"</p>"
                     + "<p>Correo : " + getCorreo() + "</p>" ;
        return Datos;
    }
}
