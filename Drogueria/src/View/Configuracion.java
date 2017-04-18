/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexion.Conexion;
import Model.Empresa;
import Model.Sistema;
import Model.Usuario;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author 201611277427
 */
public class Configuracion extends javax.swing.JFrame {

    /**
     * Creates new form Sistema
     */
    private int IdEmpresa;
    private int IdVenta;

    public Configuracion() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("CONFIGURACION");
        setIconImage(new ImageIcon(getClass().getResource("/Img_Ventanas/settings.png")).getImage());
        Cerrar();
        usuario.TablaUsuario();
        DatosEmpresa();
        DatosVenta();
        TxtNombre.setEditable(false);
        TxtNit.setEditable(false);
        TxtDireccion.setEditable(false);
        TxtTelefono.setEditable(false);
        TxtCorreo.setEditable(false);
        BtnActualizar.setVisible(false);

        TxtIva.setEditable(false);
        CheckDesc.setEnabled(false);
        TxtPor.setEditable(false);

        BtnGuardarE.setEnabled(false);
        BtnGuardarV.setEnabled(false);
    }

    Usuario usuario = new Usuario();

    //Instancia Conexion BD
    Conexion conexion = new Conexion();
    Connection cn = conexion.conexion();

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

    private void DatosEmpresa() {
        String sql = "SELECT * FROM empresa";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                IdEmpresa = rs.getInt("id_empresa");
                TxtNombre.setText(rs.getString("nombre"));
                TxtNit.setText(rs.getString("nit"));
                TxtDireccion.setText(rs.getString("direccion"));
                TxtTelefono.setText(rs.getString("telefono"));
                TxtCorreo.setText(rs.getString("correo"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Error" + ex);
        }
    }

    private void DatosVenta() {
        String sql = "SELECT * FROM configuracion";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                IdVenta = rs.getInt("id");
                TxtIva.setText(String.valueOf(rs.getFloat("iva")));
                int desc = rs.getInt("des");
                if (desc == 1) {
                    CheckDesc.setSelected(true);
                } else {
                    CheckDesc.setSelected(false);
                }
                TxtPor.setText(String.valueOf(rs.getFloat("por")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Error" + ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaUsuario = new javax.swing.JTable();
        BtnEditar = new javax.swing.JButton();
        BtnAgregar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        TxtCorreo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        TxtNombre = new javax.swing.JTextField();
        TxtNit = new javax.swing.JTextField();
        TxtDireccion = new javax.swing.JTextField();
        TxtTelefono = new javax.swing.JTextField();
        BtnGuardarE = new javax.swing.JButton();
        BtnAditarEmpresa = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        IVA = new javax.swing.JLabel();
        TxtIva = new javax.swing.JTextField();
        CheckDesc = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        TxtPor = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        BtnGuardarV = new javax.swing.JButton();
        BtnEditarV = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        TablaUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombres", "Usuario", "Password", "Tipo"
            }
        ));
        jScrollPane1.setViewportView(TablaUsuario);

        BtnEditar.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        BtnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/Editar.png"))); // NOI18N
        BtnEditar.setText("Editar");
        BtnEditar.setMargin(new java.awt.Insets(2, 0, 2, 13));
        BtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditarActionPerformed(evt);
            }
        });

        BtnAgregar.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        BtnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/Add.png"))); // NOI18N
        BtnAgregar.setText("Agregar");
        BtnAgregar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        BtnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAgregarActionPerformed(evt);
            }
        });

        BtnEliminar.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        BtnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/Eliminar.png"))); // NOI18N
        BtnEliminar.setText("Eliminar");
        BtnEliminar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BtnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnAgregar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BtnAgregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtnEliminar)))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Registros de  Usuarios ", jPanel1);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        TxtCorreo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtCorreoKeyReleased(evt);
            }
        });

        jLabel1.setText("Razón social");

        jLabel3.setText("Nit.");

        jLabel4.setText("Direccion");

        jLabel5.setText("Telefono");

        jLabel6.setText("Correo");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TxtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(TxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(TxtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(TxtNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(TxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        BtnGuardarE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/aceptar.png"))); // NOI18N
        BtnGuardarE.setText("Guardar");
        BtnGuardarE.setMargin(new java.awt.Insets(2, 5, 2, 5));
        BtnGuardarE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarEActionPerformed(evt);
            }
        });

        BtnAditarEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/lapiz.png"))); // NOI18N
        BtnAditarEmpresa.setText("Editar");
        BtnAditarEmpresa.setMargin(new java.awt.Insets(2, 5, 2, 5));
        BtnAditarEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAditarEmpresaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(BtnAditarEmpresa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtnGuardarE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnGuardarE)
                    .addComponent(BtnAditarEmpresa))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Datos Empresa", jPanel4);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        IVA.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        IVA.setText("IVA");

        TxtIva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtIvaKeyTyped(evt);
            }
        });

        CheckDesc.setMaximumSize(new java.awt.Dimension(30, 30));
        CheckDesc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CheckDescMouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        jLabel2.setText("Descuento");

        TxtPor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtPorKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        jLabel7.setText("Porcentaje Descuento");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 21, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TxtPor, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(IVA)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CheckDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtIva, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IVA)
                    .addComponent(TxtIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(CheckDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtPor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        BtnGuardarV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/aceptar.png"))); // NOI18N
        BtnGuardarV.setText("Guardar");
        BtnGuardarV.setMargin(new java.awt.Insets(2, 5, 2, 5));
        BtnGuardarV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarVActionPerformed(evt);
            }
        });

        BtnEditarV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/lapiz.png"))); // NOI18N
        BtnEditarV.setText("Editar");
        BtnEditarV.setMargin(new java.awt.Insets(2, 5, 2, 5));
        BtnEditarV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditarVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(BtnEditarV)
                        .addGap(33, 33, 33)
                        .addComponent(BtnGuardarV)))
                .addContainerGap(306, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnGuardarV)
                    .addComponent(BtnEditarV))
                .addGap(36, 36, 36))
        );

        jTabbedPane1.addTab("Configuracion Ventas", jPanel2);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/cancelar.png"))); // NOI18N
        jButton1.setText("Cancelar");
        jButton1.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        BtnActualizar.setText("Actualizar");
        BtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(212, 212, 212)
                        .addComponent(BtnActualizar)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(BtnActualizar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void BtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarActionPerformed
        usuario.TablaUsuario();
    }//GEN-LAST:event_BtnActualizarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        int fila = TablaUsuario.getSelectedRow();
        if (fila >= 0) {
            int estado = JOptionPane.showConfirmDialog(rootPane, "Desea Eliminar el Usuario", "Comfirmar Eliminar", JOptionPane.YES_NO_OPTION);
            if (estado == 0) {

                int fil = TablaUsuario.getSelectedRow();
                String user = TablaUsuario.getValueAt(fil, 1).toString();
                String pass = TablaUsuario.getValueAt(fil, 2).toString();
                usuario.Eliminar(usuario.ObtenerId(user));
                usuario.TablaUsuario();
                JOptionPane.showMessageDialog(rootPane, "Eliminado");
                Sistema sistema = new Sistema();
                sistema.InsertarHistorial("Ha Elimindao un Usuario");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un Usuario");
        }
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAgregarActionPerformed
        new RegistrarUsuario().setVisible(true);
    }//GEN-LAST:event_BtnAgregarActionPerformed

    private void BtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditarActionPerformed
        int fila = TablaUsuario.getSelectedRow();
        if (fila >= 0) {

            String nombre = TablaUsuario.getValueAt(fila, 0).toString();
            String user = TablaUsuario.getValueAt(fila, 1).toString();
            String pass = TablaUsuario.getValueAt(fila, 2).toString();
            int tipo = Integer.parseInt(TablaUsuario.getValueAt(fila, 3).toString());
            RegistrarUsuario registro = new RegistrarUsuario();
            registro.Datos(nombre, user, pass, tipo);
            registro.setTitle("ACTUALIZAR DATOS");
            registro.BtnRegistrar.setText("Actualizar");
            registro.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un Usuario");
        }
    }//GEN-LAST:event_BtnEditarActionPerformed

    private void TxtCorreoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtCorreoKeyReleased
        String correo = TxtCorreo.getText();
        String email = "";

        for (int x = 0; x < correo.length(); x++) {
            if (correo.charAt(x) == '@') {
                for (int i = x; i < correo.length(); i++) {
                    email = email + correo.charAt(i);
                }
                break;
            }
        }

        if (email.equals("@gmail.com") || email.equals("@hotmail.com")
                || email.equals("@yahoo.com") || email.equals("@hotmail.es")
                || email.equals("@outlook.com")) {
            TxtCorreo.setBackground(Color.decode("#BCEE68"));
            BtnGuardarE.setEnabled(true);
        } else {
            TxtCorreo.setBackground(Color.decode("#CD2626"));
        }
    }//GEN-LAST:event_TxtCorreoKeyReleased

    private void BtnGuardarEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarEActionPerformed
        String nombre = TxtNombre.getText();
        String nit = TxtNit.getText();
        String direccion = TxtDireccion.getText();
        String telefono = TxtTelefono.getText();
        String correo = TxtCorreo.getText();

        if (!nombre.equals("") && !nit.equals("") && !direccion.equals("") && !telefono.equals("") && !correo.equals("")) {

            Empresa empresa = new Empresa(nombre, nit, direccion, telefono, correo);
            empresa.Actualizar(IdEmpresa);
            Sistema sistema = new Sistema();
            sistema.InsertarHistorial("Ha Actualizado los datos de la Empresa");
            TxtNombre.setEditable(false);
            TxtNit.setEditable(false);
            TxtDireccion.setEditable(false);
            TxtTelefono.setEditable(false);
            TxtCorreo.setEditable(false);
            BtnGuardarE.setEnabled(false);
        } else {
            //campos vacios
        }
    }//GEN-LAST:event_BtnGuardarEActionPerformed

    private void BtnAditarEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAditarEmpresaActionPerformed
        TxtNombre.setEditable(true);
        TxtNit.setEditable(true);
        TxtDireccion.setEditable(true);
        TxtTelefono.setEditable(true);
        TxtCorreo.setEditable(true);

        BtnGuardarE.setEnabled(true);
    }//GEN-LAST:event_BtnAditarEmpresaActionPerformed

    private void BtnEditarVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditarVActionPerformed
        TxtIva.setEditable(true);
        CheckDesc.setEnabled(true);
        BtnGuardarV.setEnabled(true);
        if (CheckDesc.isSelected()) {
            TxtPor.setEditable(true);
        } else {
            TxtPor.setEditable(false);
        }
    }//GEN-LAST:event_BtnEditarVActionPerformed

    private void BtnGuardarVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarVActionPerformed
        if (!TxtIva.getText().equals("")) {
            float iva = Float.parseFloat(TxtIva.getText());
            int des;
            float por;
            if (CheckDesc.isSelected()) {
                des = 1;
                por = Float.parseFloat(TxtPor.getText());
            } else {
                des = 0;
                por = 0;
            }
            Model.Sistema venta = new Model.Sistema(iva, des, por);
            if (venta.RegistroVenta()) {
                venta.Actualizar(IdVenta);
                Sistema sistema = new Sistema();
                sistema.InsertarHistorial("Ha Actualizado las Ventas");
            } else {
                venta.Insertar();
            }
            CheckDesc.setEnabled(false);
            TxtIva.setEditable(false);
            BtnGuardarV.setEnabled(false);
        }
    }//GEN-LAST:event_BtnGuardarVActionPerformed

    private void CheckDescMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CheckDescMouseClicked
        if (CheckDesc.isSelected()) {
            TxtPor.setEditable(true);
        } else {
            TxtPor.setText("0.0");
            TxtPor.setEditable(false);
        }
    }//GEN-LAST:event_CheckDescMouseClicked

    private void TxtIvaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtIvaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_TxtIvaKeyTyped

    private void TxtPorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtPorKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_TxtPorKeyTyped

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
            java.util.logging.Logger.getLogger(Configuracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Configuracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Configuracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Configuracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Configuracion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton BtnActualizar;
    private javax.swing.JButton BtnAditarEmpresa;
    private javax.swing.JButton BtnAgregar;
    private javax.swing.JButton BtnEditar;
    private javax.swing.JButton BtnEditarV;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnGuardarE;
    private javax.swing.JButton BtnGuardarV;
    private javax.swing.JCheckBox CheckDesc;
    private javax.swing.JLabel IVA;
    public static javax.swing.JTable TablaUsuario;
    private javax.swing.JTextField TxtCorreo;
    private javax.swing.JTextField TxtDireccion;
    private javax.swing.JTextField TxtIva;
    private javax.swing.JTextField TxtNit;
    private javax.swing.JTextField TxtNombre;
    private javax.swing.JTextField TxtPor;
    private javax.swing.JTextField TxtTelefono;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}