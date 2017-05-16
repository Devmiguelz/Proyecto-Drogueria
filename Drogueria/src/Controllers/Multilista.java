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

    public int  UltimoHijoCliente(int padrecodigo) {
        NodoDrogueria temp = BuscarPadre(padrecodigo);
        NodoHijoDrogueria q;
        if (temp != null) {
            q = temp.hijo;
            while (q != null) {
                if (q.sig == null) {
                    return q.id_cliente;
                }
                q = q.sig;
            }
        }
        return 0;
    }

    public int  UltimoHijoUsuario(int padrecodigo) {
        NodoDrogueria temp = BuscarPadre(padrecodigo);
        NodoHijoDrogueria q;
        if (temp != null) {
            q = temp.hijo;
            while (q != null) {
                if (q.sig == null) {
                    return q.id_usuario;
                }
                q = q.sig;
            }
        }
        return 0;
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
    
    public NodoHijoDrogueria BuscarUsuario(int id_user) {
        NodoDrogueria buscar = BuscarPadre(3);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                if (q.id_usuario == id_user) {
                    return q;
                }
                q = q.sig;
            }
        }
        return null;
    }

    public NodoHijoDrogueria BuscarCliente(long ide) {
        NodoDrogueria buscar = BuscarPadre(2);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                if (q.identificacion == ide) {
                    return q;
                }
                q = q.sig;
            }
        }
        return null;
    }
    
    public NodoHijoDrogueria BuscarProducto(int codigo) {
        NodoDrogueria buscar = BuscarPadre(1);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                if (q.codigo == codigo) {
                    return q;
                }
                q = q.sig;
            }
        }
        return null;
    }

    public NodoHijoDrogueria AnteriorCliente(int padrecodigo, int ide) {
        NodoDrogueria buscar = BuscarPadre(padrecodigo);
        NodoHijoDrogueria q;
        NodoHijoDrogueria ant = null;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                if (q.identificacion == ide) {
                    return ant;
                }
                ant = q;
                q = q.sig;
            }
        }
        return null;
    }
    
    public NodoHijoDrogueria AnteriorUsuario(int padrecodigo, int ide) {
        NodoDrogueria buscar = BuscarPadre(padrecodigo);
        NodoHijoDrogueria q;
        NodoHijoDrogueria ant = null;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                if (q.id_usuario == ide) {
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

    public void EliminarCliente(int padrecodigo, int ide) {
        NodoDrogueria buscar = BuscarPadre(padrecodigo);
        NodoHijoDrogueria q = BuscarCliente(ide);
        NodoHijoDrogueria ant = AnteriorCliente(padrecodigo, ide);
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
    
    public void EliminarUsuario(int padrecodigo, int ide) {
        NodoDrogueria buscar = BuscarPadre(padrecodigo);
        NodoHijoDrogueria q = BuscarUsuario(ide);
        NodoHijoDrogueria ant = AnteriorUsuario(padrecodigo, ide);
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
    
    public void ActualizarCliente(int padrecodigo, NodoHijoDrogueria p) {
        NodoDrogueria buscar = BuscarPadre(padrecodigo);
        NodoHijoDrogueria q = BuscarCliente(p.identificacion);
        if (buscar != null) {
            q.nombre = p.nombre;
            q.apellido = p.apellido;
            q.identificacion = p.identificacion;
            q.correo = p.correo;
            q.telefono = p.telefono;
        } else {
            JOptionPane.showMessageDialog(null, "Error no existe el Padre");
        }
    }
    
    public void ActualizarUsuario(int padrecodigo, NodoHijoDrogueria p) {
        NodoDrogueria buscar = BuscarPadre(padrecodigo);
        NodoHijoDrogueria q = BuscarUsuario(p.id_usuario);
        if (buscar != null) {
            q.nombre = p.nombre;
            q.user = p.user;
            q.password = p.password;
            q.tipo = p.tipo;
        } else {
            JOptionPane.showMessageDialog(null, "Error no existe el Padre");
        }
    }
    
    public void ActualizarDatosEmpresa(NodoHijoDrogueria p) {
        NodoDrogueria buscar = BuscarPadre(6);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            q.nombre = p.nombre;
            q.nit = p.nit;
            q.direccion = p.direccion;
            q.telefono = p.telefono;
            q.correo = p.correo;
        } else {
            JOptionPane.showMessageDialog(null, "Error no existe el Padre");
        }
    }
    
    public void ActualizarDatosSistema(NodoHijoDrogueria p) {
        NodoDrogueria buscar = BuscarPadre(4);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            q.id_venta = p.id_venta;
            q.iva = p.iva;
            q.descuento = p.descuento;
            q.porcentaje = p.porcentaje;
        } else {
            JOptionPane.showMessageDialog(null, "Error no existe el Padre");
        }
    }
    
    public void ActualizarProducto(int padrecodigo, NodoHijoDrogueria p) {
        NodoDrogueria buscar = BuscarPadre(padrecodigo);
        NodoHijoDrogueria q = BuscarProducto(p.codigo);
        if (buscar != null) {
            q.nombre = p.nombre;
            q.cantidad = p.cantidad;
            q.precio = p.precio;
            q.estado = p.estado;
            q.stan = p.stan;
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
                hijo.id_venta = Integer.parseInt(rs.getString(1));
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
                hijo.nombre = rs.getString("nombre");
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

    public NodoHijoDrogueria BuscarCliente(int padrecodigo, int id) {
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

    public void ImprimirTodo() {
        String cad = "";
        NodoDrogueria p = BuscarPadre(3);
        NodoHijoDrogueria q;
        System.out.println("Padre " + p.nombre + "\n");
        q = p.hijo;
        while (q != null) {
            cad += "Codigo : " + q.codigo + " "
                    + "Nombre : " + q.nombre + "  "
                    + "Cantidad : " + q.cantidad + " "
                    + "Precio " + q.precio + "  "
                    + "Estado " + q.estado + " "
                    + "Nombre : " + q.user + "  "
                    + "Cantidad : " + q.password + " "
                    + "Precio " + q.direccion + "  "
                    + "Estado " + q.stan + " "
                    + "Cantidad : " + q.nit + " "
                    + "Precio " + q.id_venta + "  "
                    + "Estado " + q.empleado + " "
                    + "Nombre : " + q.telefono + "  "
                    + "Cantidad : " + q.total + " "
                    + "Precio " + q.cliente + "  "
                    + "Estado " + q.hora + "\n";
            cad += "============================================================================\n";
            q = q.sig;
        }
        System.out.println(cad);
    }
}
