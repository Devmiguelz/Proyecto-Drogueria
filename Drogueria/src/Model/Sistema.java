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
import static View.Historial.TablaHistorial;
import View.Login;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 201611277427
 */
public class Sistema {

    private float iva;
    private int descuento;
    private float porcentaje;

    private NodoHijoDrogueria hijo;
    private NodoDrogueria padre;

    public Sistema() {
        hijo = new NodoHijoDrogueria();
        padre = new NodoDrogueria();
    }

    public Sistema(float iva, int descuento, float porcentaje) {
        this.iva = iva;
        this.descuento = descuento;
        this.porcentaje = porcentaje;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }

    //Instancia Conexion BD
    Conexion conexion = new Conexion();
    Connection cn = conexion.conexion();

    public void Insertar() {
        try {
            PreparedStatement pst = cn.prepareStatement("INSERT INTO configuracion "
                    + "(iva,des,por)"
                    + "VALUES (?,?,?)");

            pst.setFloat(1, getIva());
            pst.setInt(2, getDescuento());
            pst.setFloat(3, getPorcentaje());
            pst.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    public void Actualizar(int id) {
        try {
            PreparedStatement pst = cn.prepareStatement("UPDATE configuracion "
                    + " SET   iva ='" + getIva() + "',"
                    + " des ='" + getDescuento() + "',"
                    + " por ='" + getPorcentaje() + "' "
                    + " WHERE id ='" + id + "' ");
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    public boolean RegistroVenta() {
        boolean valida = false;
        NodoDrogueria buscar = lista.BuscarPadre(4);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                valida = true;
                q = q.sig;
            }
        }
        return valida;
    }

    public Float ObtenerIva() {
        float Iva = 0;
        NodoDrogueria buscar = lista.BuscarPadre(4);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                Iva = q.iva;
                q = q.sig;
            }
        }
        return Iva;
    }

    public void InsertarHistorial(String mensaje) {
        Calendar date;
        int dia, mes, año, dias;
        String fecha, hora, usuario;
        date = Calendar.getInstance();
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat hour = new SimpleDateFormat("hh:mm:ss a");
        año = date.get(Calendar.YEAR);
        mes = date.get(Calendar.MONTH) + 1;
        dia = date.get(Calendar.DAY_OF_MONTH);

        hora = hour.format(now);
        fecha = dia + "/" + mes + "/" + año;
        dias = CompararFechas.CompararDate(fecha);
        usuario = Login.nombreUsuario;

        try {
            PreparedStatement pst = cn.prepareStatement("INSERT INTO historial "
                    + "(usuario,accion,hora,fecha,dias)"
                    + "VALUES (?,?,?,?,?)");

            pst.setString(1, usuario);
            pst.setString(2, mensaje);
            pst.setString(3, hora);
            pst.setString(4, fecha);
            pst.setInt(5, dias);
            pst.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error" + e);
        }

    }

    public void ObtenerDias() {
        String fechas[] = new String[CantRegistros()];
        int Dias[] = new int[CantRegistros()];
        int id[] = new int[CantRegistros()];
        int i = 0;
        String sql = " SELECT * FROM  historial ";
        try {
            Statement consult = cn.createStatement();
            ResultSet rs = consult.executeQuery(sql);
            while (rs.next()) {
                id[i] = rs.getInt("id");
                fechas[i] = rs.getString("fecha");
                i++;
            }
        } catch (Exception ex) {
            System.out.println("Error :" + ex);
        }
        for (int j = 0; j < fechas.length; j++) {
            Dias[j] = CompararFechas.CompararDate(fechas[j]);
        }
        ActualizarDias(id, Dias);
    }

    public void ActualizarDias(int id[], int day[]) {
        for (int i = 0; i < day.length; i++) {
            try {
                PreparedStatement pst = cn.prepareStatement("UPDATE historial "
                        + " SET   dias = '" + day[i] + "' "
                        + " WHERE id = '" + id[i] + "' ");
                pst.executeUpdate();
            } catch (Exception e) {
                System.out.println("Error" + e);
            }
        }
    }

    public int CantRegistros() {
        int cant = 0;
        String sql = " SELECT COUNT(*) as total FROM  historial ";
        try {
            Statement consult = cn.createStatement();
            ResultSet rs = consult.executeQuery(sql);
            if (rs.next()) {
                cant = rs.getInt("total");
            }
        } catch (Exception ex) {
            System.out.println("Error :" + ex);
        }
        return cant;
    }

    public void TablaHistorial() {
        DefaultTableModel modelo = (DefaultTableModel) TablaHistorial.getModel();
        int[] anchos = {30, 100, 20, 20, 10};
        for (int i = 0; i < TablaHistorial.getColumnCount(); i++) {
            TablaHistorial.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        TablaHistorial.setRowHeight(20);
        String sql = "SELECT * FROM historial ORDER BY id DESC";

        String[] datos = new String[5];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(2);
                datos[1] = rs.getString(3);
                datos[2] = rs.getString(4);
                datos[3] = rs.getString(5);
                datos[4] = rs.getString(6);
                modelo.addRow(datos);
            }
            TablaHistorial.setModel(modelo);
        } catch (SQLException ex) {
            System.out.println("Error :" + ex);
        }
    }

}
