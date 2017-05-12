/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Conexion.Conexion;
import NodosMultilista.NodoHijoDrogueria;
import NodosMultilista.NodoDrogueria;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Miguel
 */
public class Multilista {

    public static NodoDrogueria raiz;
    public static NodoDrogueria padre;
    public static NodoHijoDrogueria hijo;
    public static Multilista lista = new Multilista();

    public Multilista() {
        raiz = null;
        padre = new NodoDrogueria();
        hijo = new NodoHijoDrogueria();
    }

    //Instancia Conexion BD
    Conexion conexion = new Conexion();
    Connection cn = conexion.conexion();

    public NodoHijoDrogueria UltimoHijo(NodoHijoDrogueria lista) {
        NodoHijoDrogueria temp = lista;
        while (temp != null) {
            if (temp.sig == null) {
                return temp;
            }
            temp = temp.sig;
        }
        return null;
    }

    public NodoDrogueria BuscarPadre(int padrecodigo) {
        NodoDrogueria temp = raiz;
        while (temp != null) {
            if (temp.codigo == padrecodigo) {
                return temp;
            }
            temp = temp.sig;
        }
        return null;
    }

    public NodoHijoDrogueria BuscarHijo(int padrecodigo, int hijocodigo) {
        NodoDrogueria buscar = BuscarPadre(padrecodigo);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                if (q.codigo == hijocodigo) {
                    return q;
                }
                q = q.sig;
            }
        }
        return null;
    }

    public NodoHijoDrogueria Anterior(int padrecodigo, int hijocodigo) {
        NodoDrogueria buscar = BuscarPadre(padrecodigo);
        NodoHijoDrogueria q;
        NodoHijoDrogueria ant = null;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                if (q.codigo == hijocodigo) {
                    return ant;
                }
                ant = q;
                q = q.sig;
            }
        }
        return null;
    }

    public NodoDrogueria UltimoPadre() {
        NodoDrogueria temp = raiz;
        while (temp != null) {
            if (temp.sig == null) {
                return temp;
            }
            temp = temp.sig;
        }
        return null;
    }

    public void InsertarPadre(NodoDrogueria dato) {
        NodoDrogueria ultimo = UltimoPadre();
        NodoDrogueria nuevo = new NodoDrogueria(dato);
        if (ultimo != null) {
            ultimo.sig = nuevo;
            nuevo.ant = ultimo;
        } else {
            raiz = nuevo;
        }
    }

    public void InsertarHijo(int padrecodigo, NodoHijoDrogueria hijo) {
        NodoDrogueria padres = BuscarPadre(padrecodigo);
        if (padres != null) {
            NodoHijoDrogueria ultimo = UltimoHijo(padres.hijo);
            NodoHijoDrogueria nuevo = new NodoHijoDrogueria(hijo);
            if (ultimo != null) {
                ultimo.sig = nuevo;
            } else {
                padres.hijo = nuevo;
            }
        } else {
            JOptionPane.showMessageDialog(null, "No existe Padre ");
        }
    }

    public void Eliminar(int padrecodigo, int hijocodigo) {
        NodoDrogueria buscar = BuscarPadre(padrecodigo);
        NodoHijoDrogueria q = BuscarHijo(padrecodigo, hijocodigo);
        NodoHijoDrogueria ant = Anterior(padrecodigo, hijocodigo);
        if (buscar != null) {
            if (buscar.hijo == q) {
                buscar.hijo = q.sig;
            } else {
                ant.sig = q.sig;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error no existe el Padre");
        }
    }

    public void CargarPadres() {
        padre.codigo = 1;
        padre.nombre = "Productos";
        InsertarPadre(padre);

        padre.codigo = 2;
        padre.nombre = "Clientes";
        InsertarPadre(padre);

        padre.codigo = 3;
        padre.nombre = "Usuario";
        InsertarPadre(padre);

        padre.codigo = 4;
        padre.nombre = "Sistema";
        InsertarPadre(padre);

        padre.codigo = 5;
        padre.nombre = "Factura";
        InsertarPadre(padre);

        padre.codigo = 6;
        padre.nombre = "Empresa";
        InsertarPadre(padre);
    }

    public void CargarProductos() {
        String sql = "SELECT * FROM productos WHERE estado = 1 ORDER BY cod_producto";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                hijo.codigo = Integer.parseInt(rs.getString(1));
                hijo.nombre = rs.getString(2);
                hijo.cantidad = Integer.parseInt(rs.getString(3));
                hijo.precio = Float.parseFloat(rs.getString(4));
                hijo.estado = Integer.parseInt(rs.getString(5));
                hijo.stan = rs.getString(6);
                InsertarHijo(1, hijo);
            }
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
        }
    }

    public void CargarClientes() {
        String sql = "SELECT * FROM clientes ORDER BY nombre";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                hijo.id_cliente = Integer.parseInt(rs.getString(1));
                hijo.nombre = rs.getString(2);
                hijo.apellido = rs.getString(3);
                hijo.identificacion = Long.parseLong(rs.getString(4));
                hijo.correo = rs.getString(5);
                hijo.telefono = Long.parseLong(rs.getString(6));
                InsertarHijo(2, hijo);
            }
        } catch (SQLException ex) {
            System.out.println("Error" + ex.getMessage());
        }
    }

    public void CargarUsuario() {
        String sql = "SELECT * FROM usuarios";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                hijo.id_usuario = Integer.parseInt(rs.getString(1));
                hijo.nombre = rs.getString(2);
                hijo.user = rs.getString(3);
                hijo.password = rs.getString(4);
                hijo.tipo = Integer.parseInt(rs.getString(5));
                InsertarHijo(3, hijo);
            }
        } catch (SQLException ex) {
            System.out.println("Error :" + ex.getMessage());
        }
    }

    public void CargarSistema() {
        String sql = " SELECT * FROM  configuracion";
        try {
            Statement consult = cn.createStatement();
            ResultSet rs = consult.executeQuery(sql);
            if (rs.next()) {
                hijo.id_configuracion = Integer.parseInt(rs.getString(1));
                hijo.iva = Float.parseFloat(rs.getString(2));
                hijo.descuento = Integer.parseInt(rs.getString(3));
                hijo.porcentaje = Float.parseFloat(rs.getString(4));
                InsertarHijo(4, hijo);
            }
        } catch (SQLException ex) {
            System.out.println("Error :" + ex.getMessage());
        }
    }

    public void CargarFactura() {
        String sql = "SELECT * FROM factura ORDER BY fecha  ASC";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                hijo.codigo = Integer.parseInt(rs.getString(1));
                hijo.cliente = rs.getString(2);
                hijo.fecha = rs.getString(3);
                hijo.hora = rs.getString(4);
                hijo.empleado = rs.getString(5);
                hijo.total = Float.parseFloat(rs.getString(6));
                InsertarHijo(5, hijo);
            }
        } catch (SQLException ex) {
            System.out.println("Error" + ex);
        }
    }

    public void CargarEmpresa() {
        String sql = "SELECT * FROM empresa";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                hijo.id_empresa = rs.getInt("id_empresa");
                hijo.nombre =  rs.getString("nombre");
                hijo.nit = rs.getString("nit");
                hijo.direccion = rs.getString("direccion");
                hijo.telefono = Long.parseLong(rs.getString("telefono"));
                hijo.correo = rs.getString("correo");
                InsertarHijo(6, hijo);
            }
        } catch (SQLException ex) {
            System.out.println("Error :" + ex.getMessage());
        }
    }
    
    public NodoHijoDrogueria BuscarCliente(int padrecodigo, int id){
        NodoDrogueria buscar = BuscarPadre(padrecodigo);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                if (q.identificacion == id) {
                    return q;
                }
                q = q.sig;
            }
        }
        return null;
    }
    
    public void ImprimirTodo(){
        String cad = "";
        NodoDrogueria p = BuscarPadre(3);
        NodoHijoDrogueria q;
            System.out.println("Padre "+p.nombre+"\n");
            q = p.hijo;
            while (q != null) {                
                cad += "Codigo : "+q.codigo+" "
                        + "Nombre : "+q.nombre+"  "
                        + "Cantidad : "+q.cantidad+" "
                        + "Precio "+q.precio+"  "
                        + "Estado "+q.estado + " "
                        + "Nombre : "+q.user+"  "
                        + "Cantidad : "+q.password+" "
                        + "Precio "+q.direccion+"  "
                        + "Estado "+q.stan + " "
                        + "Cantidad : "+q.nit+" "
                        + "Precio "+q.id_configuracion+"  "
                        + "Estado "+q.empleado + " "
                        + "Nombre : "+q.telefono+"  "
                        + "Cantidad : "+q.total+" "
                        + "Precio "+q.cliente+"  "
                        + "Estado "+q.hora + "\n";
                cad += "============================================================================\n";
                q = q.sig;
            }
        System.out.println(cad);
    }
}
