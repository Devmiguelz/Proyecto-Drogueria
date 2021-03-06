/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import static Controllers.Multilista.lista;
import static Model.Password.Encriptar;
import static Model.Password.Descriptar;
import Model.Sistema;
import Model.Usuario;
import NodosMultilista.NodoDrogueria;
import NodosMultilista.NodoHijoDrogueria;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author 201611277427
 */
public class RegistrarUsuario extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    private String user;
    private int cant;
    private NodoHijoDrogueria hijo;
    private NodoDrogueria padre;

    public RegistrarUsuario() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("CREAR USUARIO");
        setIconImage(new ImageIcon(getClass().getResource("/Img_Ventanas/user.png")).getImage());
        Cerrar();
        Habilitar();
        hijo = new NodoHijoDrogueria();
        padre = new NodoDrogueria();
    }
    
    Sistema sistema = new Sistema();

    public void Datos(String nombre, String user, String pass, int tipo) {
        TxtNombres.setText(nombre);
        TxtUser.setText(user);
        TxtPass1.setText(Descriptar(pass));
        TxtPass2.setText(Descriptar(pass));
        this.user = user;
        if (tipo == 0) {
            Rempleado.setSelected(true);
        } else {
            Radmin.setSelected(true);
        }
        Habilitar();
    }

    private void Habilitar() {
        if (!TxtNombres.getText().equals("")
                && !TxtUser.getText().equals("")
                && !String.valueOf(TxtPass1.getPassword()).equals("")
                && !String.valueOf(TxtPass2.getPassword()).equals("")
                && (Radmin.isSelected() || Rempleado.isSelected())) {
            BtnRegistrar.setEnabled(true);
        } else {
            BtnRegistrar.setEnabled(false);
        }
    }

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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        TxtUser = new javax.swing.JTextField();
        BtnRegistrar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        Radmin = new javax.swing.JRadioButton();
        Rempleado = new javax.swing.JRadioButton();
        BtnCancelar = new javax.swing.JButton();
        TxtPass1 = new javax.swing.JPasswordField();
        TxtPass2 = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        TxtNombres = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Nombre de usuario");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 34, 104, -1));

        jLabel2.setText("Contraseña");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 104, 104, -1));

        jLabel3.setText("Confirmar contraseña");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 139, -1, -1));

        TxtUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtUserKeyTyped(evt);
            }
        });
        getContentPane().add(TxtUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 31, 164, -1));

        BtnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/aceptar.png"))); // NOI18N
        BtnRegistrar.setText("Registrar");
        BtnRegistrar.setMargin(new java.awt.Insets(2, 3, 2, 3));
        BtnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRegistrarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(239, 218, -1, -1));

        jLabel4.setText("Tipo de usuario");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 181, 104, -1));

        buttonGroup1.add(Radmin);
        Radmin.setText("Adminsitrador");
        Radmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RadminMouseClicked(evt);
            }
        });
        getContentPane().add(Radmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 177, -1, -1));

        buttonGroup1.add(Rempleado);
        Rempleado.setText("Empleado");
        Rempleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RempleadoMouseClicked(evt);
            }
        });
        getContentPane().add(Rempleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 177, -1, -1));

        BtnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/cancelar.png"))); // NOI18N
        BtnCancelar.setText("Cancelar");
        BtnCancelar.setMargin(new java.awt.Insets(2, 3, 2, 3));
        BtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 218, -1, -1));

        TxtPass1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtPass1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtPass1KeyTyped(evt);
            }
        });
        getContentPane().add(TxtPass1, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 101, 164, -1));

        TxtPass2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtPass2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtPass2KeyTyped(evt);
            }
        });
        getContentPane().add(TxtPass2, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 139, 164, -1));

        jLabel5.setText("Nombre y Apellido ");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 72, -1, -1));

        TxtNombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtNombresKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtNombresKeyTyped(evt);
            }
        });
        getContentPane().add(TxtNombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 69, 164, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRegistrarActionPerformed
        String user1 = TxtUser.getText();
        String pass1 = String.valueOf(TxtPass1.getPassword());
        String pass2 = String.valueOf(TxtPass2.getPassword());
        String nombre = TxtNombres.getText();
        int tipo;
        if (!nombre.equals("") && !user1.equals("") && !pass2.equals("") && !pass1.equals("") && (Radmin.isSelected() || Rempleado.isSelected())) {
            if (Radmin.isSelected()) {
                tipo = 1;
            } else {
                tipo = 0;
            }
            if (pass1.equals(pass2)) {
                String password = Encriptar(pass1);
                if (BtnRegistrar.getText().equals("Registrar")) {
                    Usuario usuario = new Usuario(nombre, user1, password, tipo);;
                    usuario.Insertar();
                    
                    hijo = new NodoHijoDrogueria();
                    hijo.id_usuario = lista.UltimoHijoUsuario(3)+1;
                    hijo.nombre = nombre;
                    hijo.user = user1;
                    hijo.password = password;
                    hijo.tipo = tipo;
                    lista.InsertarHijo(3, hijo);
                    
                    this.dispose();
                    Configuracion.BtnActualizar.doClick();
                    Modal modal = new Modal(new javax.swing.JFrame(), true);
                    modal.TxtMensaje.setText("Usuario Registrado");
                    modal.setVisible(true);
                    sistema.InsertarHistorial("Ha Registrado un Usuario");
                } else if (BtnRegistrar.getText().equals("Actualizar")) {
                    Usuario usuario = new Usuario(nombre, user1, password, tipo);
                    usuario.Actualizar(usuario.ObtenerId(user));
                    
                    hijo = new NodoHijoDrogueria();
                    hijo.id_usuario = usuario.ObtenerId(user);
                    hijo.nombre = nombre;
                    hijo.user = user1;
                    hijo.password = password;
                    hijo.tipo = tipo;
                    lista.ActualizarUsuario(3, hijo);
                    
                    this.dispose();
                    sistema.InsertarHistorial("Ha Actualizado un Usuario");
                    Configuracion.BtnActualizar.doClick();
                    Modal modal = new Modal(new javax.swing.JFrame(), true);
                    modal.TxtMensaje.setText("Datos Actualizados");
                    modal.setVisible(true);
                } else {
                    Usuario usuario = new Usuario(nombre, user1, password, tipo);
                    usuario.Insertar();
                    
                    hijo = new NodoHijoDrogueria();
                    hijo.id_usuario = lista.UltimoHijoUsuario(3)+1;
                    hijo.nombre = nombre;
                    hijo.user = user1;
                    hijo.password = password;
                    hijo.tipo = tipo;

                    lista.InsertarHijo(3, hijo);
                    
                    this.dispose();
                    Modal modal = new Modal(new javax.swing.JFrame(), true);
                    modal.TxtMensaje.setText("Usuario Registrado");
                    modal.setVisible(true);
                }
            }

        } else {
            //CAMPOS VACIOS
        }

    }//GEN-LAST:event_BtnRegistrarActionPerformed

    private void TxtUserKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtUserKeyTyped
        char c = evt.getKeyChar();
        if ((c < 'a' || c > 'z')) {
            evt.consume();
        }
    }//GEN-LAST:event_TxtUserKeyTyped

    private void TxtPass1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtPass1KeyTyped
        char c = evt.getKeyChar();
        if ((c < 'a' || c > 'z')) {
            evt.consume();
        }
    }//GEN-LAST:event_TxtPass1KeyTyped

    private void TxtPass2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtPass2KeyTyped
        char c = evt.getKeyChar();
        if ((c < 'a' || c > 'z')) {
            evt.consume();
        }
    }//GEN-LAST:event_TxtPass2KeyTyped

    private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_BtnCancelarActionPerformed

    private void TxtPass2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtPass2KeyReleased
        String pass1 = String.valueOf(TxtPass1.getPassword());
        String pass2 = String.valueOf(TxtPass2.getPassword());

        if (!pass1.equals("") && pass1.equals(pass2)) {
            TxtPass2.setBackground(Color.decode("#BCEE68"));
            TxtPass1.setBackground(Color.decode("#BCEE68"));
            String user1 = TxtUser.getText();
            String pas1 = String.valueOf(TxtPass1.getPassword());
            String pas2 = String.valueOf(TxtPass2.getPassword());
            String nombre = TxtNombres.getText();
            if (!nombre.equals("") && !user1.equals("") && !pas2.equals("") && !pas1.equals("") && (Rempleado.isSelected() || Radmin.isSelected())) {
                BtnRegistrar.setEnabled(true);
            }
        } else {
            TxtPass2.setBackground(Color.decode("#F9A28F"));
            BtnRegistrar.setEnabled(false);
        }
    }//GEN-LAST:event_TxtPass2KeyReleased

    private void TxtPass1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtPass1KeyReleased
        int cant = String.valueOf(TxtPass1.getPassword()).length();
        BtnRegistrar.setEnabled(false);
        if (cant < 5) {
            TxtPass1.setBackground(Color.decode("#F9A28F"));
        } else if (cant < 8) {
            TxtPass1.setBackground(Color.decode("#FF8C00"));
        } else {
            TxtPass1.setBackground(Color.decode("#BCEE68"));
        }
    }//GEN-LAST:event_TxtPass1KeyReleased

    private void TxtNombresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtNombresKeyReleased
        String nombre = TxtNombres.getText();
        if (!nombre.equals("")) {
            char[] caracteres = nombre.toCharArray();
            caracteres[0] = Character.toUpperCase(caracteres[0]);
            // el -2 es para evitar una excepción al caernos del arreglo
            for (int i = 0; i < nombre.length() - 2; i++) // Es 'palabra'
            {
                if (caracteres[i] == ' ' || caracteres[i] == '.' || caracteres[i] == ',') {
                    // Reemplazamos
                    caracteres[i + 1] = Character.toUpperCase(caracteres[i + 1]);
                }
            }
            String nuevo = new String(caracteres);
            TxtNombres.setText(nuevo);
        }
    }//GEN-LAST:event_TxtNombresKeyReleased

    private void TxtNombresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtNombresKeyTyped
        char c = evt.getKeyChar();
        if ((c < 'a' || c > 'z') && (c != KeyEvent.VK_SPACE)) {
            evt.consume();
        }
    }//GEN-LAST:event_TxtNombresKeyTyped

    private void RempleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RempleadoMouseClicked
        String user1 = TxtUser.getText();
        String pass1 = String.valueOf(TxtPass1.getPassword());
        String pass2 = String.valueOf(TxtPass2.getPassword());
        String nombre = TxtNombres.getText();
        if (!nombre.equals("") && !user1.equals("") && !pass2.equals("") && !pass1.equals("") && Rempleado.isSelected()) {
            BtnRegistrar.setEnabled(true);
        }
    }//GEN-LAST:event_RempleadoMouseClicked

    private void RadminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RadminMouseClicked
        String user1 = TxtUser.getText();
        String pass1 = String.valueOf(TxtPass1.getPassword());
        String pass2 = String.valueOf(TxtPass2.getPassword());
        String nombre = TxtNombres.getText();
        if (!nombre.equals("") && !user1.equals("") && !pass2.equals("") && !pass1.equals("") && Radmin.isSelected()) {
            BtnRegistrar.setEnabled(true);
        }
    }//GEN-LAST:event_RadminMouseClicked

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
            java.util.logging.Logger.getLogger(RegistrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistrarUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton BtnCancelar;
    public static javax.swing.JButton BtnRegistrar;
    public static javax.swing.JRadioButton Radmin;
    public static javax.swing.JRadioButton Rempleado;
    private javax.swing.JTextField TxtNombres;
    private javax.swing.JPasswordField TxtPass1;
    private javax.swing.JPasswordField TxtPass2;
    private javax.swing.JTextField TxtUser;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
