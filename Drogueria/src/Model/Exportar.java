/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.JTable;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author 201611277427
 */
public class Exportar {
    private File file;
    private List<JTable> tabla;
    private List<String> nom_file;

    public Exportar(File file,List<JTable> tabla,List<String> nom_file) throws Exception{
        this.file = file;
        this.tabla = tabla;
        this.nom_file = nom_file;
        if (nom_file.size() != tabla.size()) {
            throw new Exception("Error");
        }
    }
    public boolean export(){
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
            WritableWorkbook w = Workbook.createWorkbook(out);
            for (int index = 0; index < tabla.size() ; index++) {
                JTable table = tabla.get(index);
                WritableSheet s = w.createSheet(nom_file.get(index), 0);
                for (int k = 0; k < table.getColumnCount(); k++) {
                    s.addCell(new Label(k, 0, table.getColumnName(k)));
                }
                for (int i = 0; i < table.getColumnCount(); i++) {
                    for (int j = 0; j < table.getRowCount(); j++) {
                        Object obj = table.getValueAt(j, i);
                        s.addCell(new Label(i,j,String.valueOf(obj)));
                    }
                }
            }
            w.write();
            w.close();
            return true;
        } catch (IOException | WriteException e) {
            return false;
        }
    }
}
