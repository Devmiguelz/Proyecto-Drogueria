/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Producto;
import Model.ComboInventario;
import Model.Inventario;
import Model.Sistema;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author 201611277427
 */
public class ListaInventario extends javax.swing.JFrame {

    /**
     * Creates new form InventarioProductos
     */
    public ListaInventario() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("INVENTARIO");
        setIconImage(new ImageIcon(getClass().getResource("/Img_Ventanas/login.png")).getImage());
        Cerrar();
        inventario.TablaInventario("");

        BtnEditar.setEnabled(false);
        BtnEliminar.setEnabled(false);
        BtnActualizar.setVisible(false);

        ComboInventario action = new ComboInventario();
        ComboOrdenar.addItemListener(action);
    }

    Inventario inventario = new Inventario();

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

        buttonGroup1 = new javax.swing.ButtonGroup();
        TxtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaInventario = new Model.TablaInventario();
        jButton2 = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnEditar = new javax.swing.JButton();
        BtnAgregar = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Rcodigo = new javax.swing.JRadioButton();
        Rnombre = new javax.swing.JRadioButton();
        ComboOrdenar = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(629, 385));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        TxtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtBuscarKeyReleased(evt);
            }
        });

        TablaInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Producto", "Cantidad", "Precio", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TablaInventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TablaInventarioMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(TablaInventario);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/error.png"))); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButton2.setPreferredSize(new java.awt.Dimension(92, 33));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        BtnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/Eliminar.png"))); // NOI18N
        BtnEliminar.setText("Eliminar");
        BtnEliminar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        BtnEliminar.setPreferredSize(new java.awt.Dimension(90, 33));
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });

        BtnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/Editar.png"))); // NOI18N
        BtnEditar.setText("Editar");
        BtnEditar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        BtnEditar.setPreferredSize(new java.awt.Dimension(76, 33));
        BtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditarActionPerformed(evt);
            }
        });

        BtnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/mas.png"))); // NOI18N
        BtnAgregar.setText("Agregar");
        BtnAgregar.setIconTextGap(1);
        BtnAgregar.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BtnAgregar.setPreferredSize(new java.awt.Dimension(82, 33));
        BtnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAgregarActionPerformed(evt);
            }
        });

        BtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnActualizarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/busqueda.png"))); // NOI18N
        jLabel1.setText("Buscar por");

        buttonGroup1.add(Rcodigo);
        Rcodigo.setText("Codigo");

        buttonGroup1.add(Rnombre);
        Rnombre.setText("Nombre");

        ComboOrdenar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ordenar por", "Codigo", "Nombres", "Cantidad", "Precio", "Estado" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(BtnActualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(BtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(BtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(TxtBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addGap(17, 17, 17)
                        .addComponent(Rcodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Rnombre)
                        .addGap(18, 18, 18)
                        .addComponent(ComboOrdenar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Rcodigo)
                    .addComponent(Rnombre)
                    .addComponent(ComboOrdenar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(BtnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        int fila = TablaInventario.getSelectedRow();
        if (fila >= 0) {
            int estado = JOptionPane.showConfirmDialog(rootPane, "Desea Eliminar el Producto", "Comfirmar Eliminar", JOptionPane.YES_NO_OPTION);
            if (estado == 0) {
                if (fila == 1) {
                    int codigo = Integer.parseInt(TablaInventario.getValueAt(fila, 0).toString());
                    Producto producto = new Producto();
                    producto.Eliminar(codigo);
                    inventario.TablaInventario("");
                    JOptionPane.showMessageDialog(rootPane, "Eliminado");
                    Sistema sistema = new Sistema();
                    sistema.InsertarHistorial("Ha Elimindao un Producto");
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione Un Solo Producto");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un Producto");
        }

    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditarActionPerformed
        int fila = TablaInventario.getSelectedRow();
        if (fila >= 0) {
            int codigo = Integer.parseInt(TablaInventario.getValueAt(fila, 0).toString());
            String nombre = TablaInventario.getValueAt(fila, 1).toString();
            int cant = (int) Integer.parseInt(TablaInventario.getValueAt(fila, 2).toString());
            float precio = Float.parseFloat(TablaInventario.getValueAt(fila, 3).toString());
            Model.Inventario inventario = new Model.Inventario();
            int estado = inventario.EstadoProducto(codigo);
            RegistrarProducto producto = new RegistrarProducto();
            producto.Datos(codigo, nombre, cant, precio, estado);
            producto.setTitle("ACTUALIZAR DATOS");
            producto.BtnAgregar.setText("Actualizar");
            Sistema sistema = new Sistema();
            sistema.InsertarHistorial("Ha Actualizado un Producto");
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un Producto");
        }
    }//GEN-LAST:event_BtnEditarActionPerformed

    private void BtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAgregarActionPerformed
        RegistrarProducto producto = new RegistrarProducto();
        producto.RActivo.setSelected(true);
        producto.RActivo.setEnabled(false);
        producto.setVisible(true);
    }//GEN-LAST:event_BtnAgregarActionPerformed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        BtnEditar.setEnabled(false);
        BtnEliminar.setEnabled(false);
        TablaInventario.clearSelection();
    }//GEN-LAST:event_formMousePressed

    private void TablaInventarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaInventarioMousePressed
        BtnEditar.setEnabled(true);
        BtnEliminar.setEnabled(true);
    }//GEN-LAST:event_TablaInventarioMousePressed

    private void BtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarActionPerformed
        inventario.TablaInventario("");
    }//GEN-LAST:event_BtnActualizarActionPerformed

    private void TxtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtBuscarKeyReleased
        String buscar = TxtBuscar.getText();
        int codigo = 0;
        if (!buscar.equals("")) {
            if (Rcodigo.isSelected()) {
                try {
                    codigo = Integer.parseInt(buscar);
                } catch (Exception e) {
                }

                inventario.BuscarPorCodigo(codigo);
            } else if (Rnombre.isSelected()) {
                try {
                    inventario.BuscarPorNombre(buscar);
                } catch (Exception e) {
                }

            } else {
                inventario.TablaInventario("");
            }
        } else {
            inventario.TablaInventario("");
        }
    }//GEN-LAST:event_TxtBuscarKeyReleased

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
            java.util.logging.Logger.getLogger(ListaInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListaInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListaInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListaInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListaInventario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton BtnActualizar;
    private javax.swing.JButton BtnAgregar;
    private javax.swing.JButton BtnEditar;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JComboBox ComboOrdenar;
    private javax.swing.JRadioButton Rcodigo;
    private javax.swing.JRadioButton Rnombre;
    public static javax.swing.JTable TablaInventario;
    private javax.swing.JTextField TxtBuscar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
