package View;

import java.awt.BorderLayout;
import javax.swing.JWindow;

/**
 *
 * @author 201611277427
 */
public class ControlSplash extends JWindow {

    Splash obj = new Splash();

    public ControlSplash() throws InterruptedException {
        this.add(obj, BorderLayout.CENTER);
        this.setSize(obj.getWidth(), obj.getHeight());
        this.setLocationRelativeTo(null);
        
        this.setVisible(true);
        obj.barra();
        dispose();
        Login seguridad = new Login();
        seguridad.setVisible(true);

    }

    public static void main(String[] args) throws InterruptedException {
        new ControlSplash();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}