/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Miguel
 */
public class CompararFechas {

    public static int CompararDate(String date) {
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día 
        java.util.Date hoy = new Date(); //Fecha de hoy 
        
        String Fecha[] = date.split("/");
        int dia = Integer.parseInt(Fecha[0]);
        int mes = Integer.parseInt(Fecha[1]);
        int año = Integer.parseInt(Fecha[2]);
        
        Calendar calendar = new GregorianCalendar(año, mes - 1, dia);
        java.sql.Date fecha = new java.sql.Date(calendar.getTimeInMillis());

        int diferencia = (int)((hoy.getTime() - fecha.getTime()) / MILLSECS_PER_DAY);
        
        return diferencia;
    }
}
