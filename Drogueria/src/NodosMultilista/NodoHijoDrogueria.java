/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NodosMultilista;

/**
 *
 * @author Miguel
 */
public class NodoHijoDrogueria {

    public NodoHijoDrogueria sig;
    //Clientes
    public int id_cliente;
    public String nombre;
    public String apellido;
    public long identificacion;
    public String correo;
    public long telefono;
    //Productos
    public int codigo;
    public int cantidad;
    public float precio;
    public int estado;
    public String stan;
    //Usuario
    public int id_usuario;
    public String user;
    public String password;
    public int tipo;
    //Sistema
    public int id_venta;
    public float iva;
    public int descuento;
    public float porcentaje;
    //Empresa
    public int id_empresa;
    public String nit;
    public String direccion;
    //Factura
    public String cliente;
    public String fecha;
    public String hora;
    public String empleado;
    public float total;

    public NodoHijoDrogueria() {
        sig = null;
    }

    public NodoHijoDrogueria(NodoHijoDrogueria r) {
        r.sig = null;
        id_cliente = r.id_cliente;
        nombre = r.nombre;
        apellido = r.apellido;
        identificacion = r.identificacion;
        correo = r.correo;
        telefono = r.telefono;
        codigo = r.codigo;
        cantidad = r.cantidad;
        precio = r.precio;
        estado = r.estado;
        id_usuario = r.id_usuario;
        user = r.user;
        stan = r.stan;
        password = r.password;
        tipo = r.tipo;
        id_venta= r.id_venta;
        iva = r.iva;
        descuento = r.descuento;
        porcentaje = r.porcentaje;
        id_empresa = r.id_empresa;
        nit = r.nit;
        direccion = r.direccion;
        cliente = r.cliente;
        fecha = r.fecha;
        hora = r.hora;
        empleado = r.empleado;
        total = r.total;
    }
}
