/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Conexion.Conexion;
import static View.ListaFactura.TablaDetalle;
import static View.ListaFactura.TablaFacturas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 201611277427
 */
public class Factura {

    private int codigo;
    private String cliente;
    private String fecha;
    private String hora;
    private String empleado;
    private float total;

    public Factura() {

    }

    public Factura(int codigo, String cliente, String fecha, String hora, String empleado, float total) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.fecha = fecha;
        this.hora = hora;
        this.empleado = empleado;
        this.total = total;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    //Instancia Conexion BD
    Conexion conexion = new Conexion();
    Connection cn = conexion.conexion();

    public void Insertar() {
        try {
            PreparedStatement pst = cn.prepareStatement("INSERT INTO factura "
                    + "(cod_factura,cliente,fecha,hora,empleado,total)"
                    + "VALUES (?,?,?,?,?,?)");

            pst.setInt(1, getCodigo());
            pst.setString(2, getCliente());
            pst.setString(3, getFecha());
            pst.setString(4, getHora());
            pst.setString(5, getEmpleado());
            pst.setFloat(6, getTotal());
            pst.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    public void DetalleFactura(int codigo[], int cantidades[]) {
        for (int i = 0; i < codigo.length; i++) {
            try {
                PreparedStatement pst = cn.prepareStatement("INSERT INTO detalle_factura "
                        + "(cod_factura,cod_producto,cantidad,total)"
                        + "VALUES (?,?,?,?)");

                pst.setInt(1, getCodigo());
                pst.setInt(2, codigo[i]);
                pst.setInt(3, cantidades[i]);
                pst.setFloat(4, getTotal());
                pst.executeUpdate();

            } catch (Exception e) {
                System.out.println("DetalleFactura() Error" + e);
            }
        }

    }

    public void ObtenerDetalles(int cod_factura) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("SubTotal");
        TablaDetalle.setModel(modelo);
        int[] anchos = {20, 90, 20, 20, 20};
        for (int k = 0; k < TablaDetalle.getColumnCount(); k++) {
            TablaDetalle.getColumnModel().getColumn(k).setPreferredWidth(anchos[k]);
        }
        TablaDetalle.setRowHeight(20);
        String sql = "SELECT productos.cod_producto,descripcion,cantidad,productos.precio  FROM  productos "
                + " JOIN detalle_factura ON detalle_factura.cod_producto = productos.cod_producto "
                + " WHERE cod_factura = '" + cod_factura + "'";
        String[] datos = new String[5];
        try {
            Statement consult = cn.createStatement();
            ResultSet rs = consult.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                int cant = Integer.parseInt(rs.getString(3));
                int precio = Integer.parseInt(rs.getString(4));
                int subtotal = cant * precio;
                datos[4] = String.valueOf(subtotal);
                modelo.addRow(datos);
            }
            TablaDetalle.setModel(modelo);
        } catch (SQLException ex) {
            System.out.println(" ObtenerDetalle() Error : " + ex.getMessage());
        }
    }
    public void MenosProducto(int codigo[], int cantidades[]) {
        int cant = codigo.length;
        for (int i = 0; i < cant; i++) {
            try {
                PreparedStatement pst = cn.prepareStatement("UPDATE productos  "
                        + " SET   stock = stock - '" + cantidades[i] + "'"
                        + " WHERE cod_producto ='" + codigo[i] + "' ");
                pst.executeUpdate();
            } catch (Exception e) {
                System.out.println("Error" + e);
            }
        }
    }

    public void Buscar(int codigo) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Cliente");
        modelo.addColumn("Fecha");
        modelo.addColumn("Hora");
        modelo.addColumn("Empleado");
        modelo.addColumn("Total");
        TablaFacturas.setModel(modelo);

        int[] anchos = {20, 90, 20, 90, 20, 20};
        for (int i = 0; i < TablaFacturas.getColumnCount(); i++) {
            TablaFacturas.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        TablaFacturas.setRowHeight(20);

        String filtro = "" + codigo + "_%";
        String sql = "SELECT * FROM factura WHERE cod_factura LIKE " + '"' + filtro + '"';

        try {
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            String[] datos = new String[6];
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                modelo.addRow(datos);
            }
            TablaFacturas.setModel(modelo);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }
    
    public void TablaFactura() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Cliente");
        modelo.addColumn("Fecha");
        modelo.addColumn("Hora");
        modelo.addColumn("Empleado");
        modelo.addColumn("Total");
        TablaFacturas.setModel(modelo);
        int[] anchos = {20, 80, 30, 30, 80,20};
        for (int i = 0; i < TablaFacturas.getColumnCount(); i++) {
            TablaFacturas.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        TablaFacturas.setRowHeight(20);
        String sql = "SELECT * FROM factura ORDER BY fecha  ASC";
        
        String[] datos = new String[6];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                modelo.addRow(datos);
            }
            TablaFacturas.setModel(modelo);
        } catch (SQLException ex) {
            System.out.println("Error" + ex);
        }
    }

    public void EnviarFactura(String correo, String productos[][]) {
        DecimalFormat formato = new DecimalFormat("###,###.##");
        String tabla = "";
        String Body;
        String Head;
        String Footer;
        String Mensaje;
        Empresa empresa = new Empresa();
        String Asunto = "FACTURA DE COMPRA N° " + getCodigo();
        String titulos[] = {"CODIGO", "PRODUCTO", "CANTIDAD", "PRECIO", "SUBTOTAL"};
        Head = "<head>"
                + "<style type='text/css'>"
                + "table {font-family: arial, sans-serif;border-collapse: collapse;width: 70%;} "
                + "td,th {border: 1px solid #dddddd;text-align: left; padding: 8px;} "
                + "tr:nth-child(even) {background-color: #dcebec;} "
                + "</style>"
                + "</head>";

        tabla += "<center><table><tr>";
        for (int i = 0; i < productos[0].length; i++) {
            tabla += "<th>" + titulos[i] + "</th>";
        }
        tabla += "</tr>";

        for (int i = 0; i < productos.length; i++) {
            tabla += "<tr>";
            for (int j = 0; j < productos[0].length; j++) {
                tabla += "<td>" + productos[i][j] + "</td>";
            }
            tabla += "</tr>";
        }
        tabla += "</table></center>";
        Body = "<body>"
                + empresa.DatosEmpresa()
                + "<br><p>Señor(a) <b>" + getCliente().toUpperCase() + "</b> usted compró </p>"
                + "<br>" + tabla + "<br>"
                + "<p> Total a Pagar : <b>$ " + formato.format(getTotal()) + "</b> Pesos </p> "
                + "<p>Fecha : " + getFecha() + "<p>"
                + "<p>Hora : " + getHora() + "<p>"
                + "<p>Atendido por : " + getEmpleado() + " </p>"
                + "</body> ";
        Footer = "<footer>"
                + "<p style='margin-bottom: -12px'>"
                + "<small>"
                + "<i>En caso de tener inconveniente con la factura acercarse a nuestras Oficinas o Enviar Correo a " + empresa.getCorreo()
                + "</i></small></p>"
                + "</footer>";
        Mensaje = Head + Body + Footer;

        EnviarEmail enviar = new EnviarEmail(correo, Asunto, Mensaje, "Factura Enviada");
        enviar.Enviar();
    }

}
