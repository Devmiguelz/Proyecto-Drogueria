/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Factura;
import Model.Usuario;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Miguel
 */
public class VentasUsuarios extends javax.swing.JFrame {

    /**
     * Creates new form VentasUsuarios
     */
    public VentasUsuarios() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("FACTURACION");
        Cerrar();
        setIconImage(new ImageIcon(getClass().getResource("/Img_Ventanas/user.png")).getImage());
        user.TablaUsuarioVentas();
    }
    
    Usuario user = new Usuario();
    Factura factura = new Factura();
    
    private void Cerrar() {
        try {
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    comfirmar();
                }
            });
            this.setVisible(true);
        } catch (Exception e) {
        }
    }

    public void comfirmar() {
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TUsuarioVenta = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaFacturaVentas = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        TablaDetalleVentas = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TUsuarioVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre Empleado", "Total Vendido"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TUsuarioVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TUsuarioVentaMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(TUsuarioVenta);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 34, 427, 90));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Empleado");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/error.png"))); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 465, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Detalle Factura");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 298, -1, -1));

        TablaFacturaVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Cliente", "Fecha", "Hora", "Total"
            }
        ));
        TablaFacturaVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TablaFacturaVentasMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(TablaFacturaVentas);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 585, 110));

        TablaDetalleVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion", "Cantidad", "Precio", "SubTotal"
            }
        ));
        jScrollPane4.setViewportView(TablaDetalleVentas);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 322, 585, 129));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Facturas");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 147, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TablaFacturaVentasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaFacturaVentasMousePressed
        int fila = TablaFacturaVentas.getSelectedRow();
        if (fila >= 0) {
            int codigo = Integer.parseInt(TablaFacturaVentas.getValueAt(fila,0).toString());
            factura.ObtenerDetallesVentas(codigo);
        }
    }//GEN-LAST:event_TablaFacturaVentasMousePressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void TUsuarioVentaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TUsuarioVentaMousePressed
        int fila = TUsuarioVenta.getSelectedRow();
        if (fila >= 0) {
            String nombre = TUsuarioVenta.getValueAt(fila,0).toString();
            factura.TablaFacturaVentas(nombre);
        }
    }//GEN-LAST:event_TUsuarioVentaMousePressed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Cliente");
        modelo.addColumn("Fecha");
        modelo.addColumn("Hora");
        modelo.addColumn("Total");
        TablaFacturaVentas.setModel(modelo);
        
        DefaultTableModel modelo2 = new DefaultTableModel();
        modelo2.addColumn("Codigo");
        modelo2.addColumn("Descripcion");
        modelo2.addColumn("Cantidad");
        modelo2.addColumn("Precio");
        modelo2.addColumn("SubTotal");
        TablaDetalleVentas.setModel(modelo2);
        TablaFacturaVentas.clearSelection();
        TablaDetalleVentas.clearSelection();
        TUsuarioVenta.clearSelection();
    }//GEN-LAST:event_formMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentasUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentasUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentasUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentasUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentasUsuarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTable TUsuarioVenta;
    public static javax.swing.JTable TablaDetalleVentas;
    public static javax.swing.JTable TablaFacturaVentas;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables
}
