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
import java.sql.Connection;
import java.sql.PreparedStatement;

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
    
    private NodoHijoDrogueria hijo;
    private NodoDrogueria padre;

    public Empresa() {
        
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

            pst.setString(1, getNombre());
            pst.setString(2, getNit());
            pst.setString(3, getDireccion());
            pst.setString(4, getTelefono());
            pst.setString(5, getCorreo());
            pst.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    public void Actualizar(int id) {
        try {
            PreparedStatement pst = cn.prepareStatement("UPDATE empresa "
                    + " SET   nombre ='" + getNombre() + "',"
                    + " nit ='" + getNit() + "',"
                    + " direccion ='" + getDireccion() + "',"
                    + " telefono ='" + getTelefono() + "' ,"
                    + " correo ='" + getCorreo() + "' "
                    + " WHERE id_empresa ='" + id + "' ");
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    public boolean RegistroEmpresa() {
        boolean valida = false;
        int cant = 0;
        NodoDrogueria buscar = lista.BuscarPadre(6);
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

    public String DatosEmpresa() {
        NodoDrogueria buscar = lista.BuscarPadre(6);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                nombre = q.nombre;
                nit = q.nit;
                direccion = q.direccion;
                telefono = String.valueOf(q.telefono);
                correo = q.correo;
                q = q.sig;
            }
        }
        String Datos = "<p>Ferreteria : " + getNombre() + "</p>"
                + "<p>Nit : " + getNit() + "</p>"
                + "<p>Direccion : " + getDireccion() + "</p>"
                + "<p>Telefono : " + getTelefono() + "</p>"
                + "<p>Correo : " + getCorreo() + "</p>";
        return Datos;
    }
}
