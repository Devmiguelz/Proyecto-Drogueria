/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.Facturar;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;

/**
 *
 * @author Miguel
 */
public class ComboProducto implements ItemListener {

    @Override
    public void itemStateChanged(ItemEvent evt) {
        JComboBox cb = (JComboBox) evt.getSource();

        Object item = evt.getItem();
        
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            Facturar.SpinnerCant.requestFocus();
            Facturar.SpinnerCant.requestFocus(true);
        }
    }
}
