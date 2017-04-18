/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;

/**
 *
 * @author Miguel
 */
public class ComboInventario implements ItemListener {

    @Override
    public void itemStateChanged(ItemEvent evt) {
        JComboBox cb = (JComboBox) evt.getSource();

        Object item = evt.getItem();
        
        

        if (evt.getStateChange() == ItemEvent.SELECTED) {
            int pos = cb.getSelectedIndex();
            
            if (pos > 0) {
                Inventario inventario = new Inventario();
                switch (pos) {
                    case 1:
                        inventario.TablaInventario("cod_producto");
                        break;
                    case 2:
                        inventario.TablaInventario("descripcion");
                        break;
                    case 3:
                        inventario.TablaInventario("stock");
                        break;
                    case 4:
                        inventario.TablaInventario("precio");
                        break;
                    case 5:
                        inventario.TablaInventario("estado");
                        break;

                }
            }
        }
    }
}
