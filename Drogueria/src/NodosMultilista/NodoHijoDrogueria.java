/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Miguel
 */
public class NodoHijoDrogueria {

    public NodoHijoDrogueria sig;
    //Clientes
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
    //Usuario
    public String password;
    //Sistema
    public float iva;
    public int descuento;
    public float porcentaje;
    //Empresa
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
        nombre = r.nombre;
        apellido = r.apellido;
        identificacion = r.identificacion;
        correo = r.correo;
        telefono = r.telefono;
        codigo = r.codigo;
        cantidad = r.cantidad;
        precio = r.precio;
        estado = r.estado;
        password = r.password;
        iva = r.iva;
        descuento = r.descuento;
        porcentaje = r.porcentaje;
        nit = r.nit;
        direccion = r.direccion;
        cliente = r.cliente;
        fecha = r.fecha;
        hora = r.hora;
        empleado = r.empleado;
        total = r.total;
    }
}
