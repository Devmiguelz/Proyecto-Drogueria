/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author 201611277427
 */
public class TablaInventario extends JTable{
    
    @Override
    public Component prepareRenderer(TableCellRenderer renderer , int fila , int columna){
        Component component = super.prepareRenderer(renderer, fila, columna);
        String cantidad = getValueAt(fila,3).toString();
        String estado = getValueAt(fila,5).toString();
        if (!cantidad.equals("") && !estado.equals("")) {
            int cant = Integer.parseInt(cantidad);
            int esta = Integer.parseInt(estado);
            if (cant > 15 && esta == 1) {
                component.setBackground(Color.WHITE);
                component.setForeground(Color.BLACK);
            }
            if (esta == 0) {
                component.setBackground(Color.decode("#D2D2D2"));
                component.setForeground(Color.decode("#2E2E2E"));
            }
            if (cant == 15 && esta == 1) {
                component.setBackground(Color.decode("#FFF8B5"));
                component.setForeground(Color.BLACK);
            }
            if (cant < 15 && esta == 1) {
                component.setBackground(Color.decode("#FEC9C9"));
                component.setForeground(Color.BLACK);
            }
        }
        return component;
    }
}
