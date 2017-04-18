/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.Modal;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author 201611277427
 */
public class EnviarEmail {
    
    private String from  = "drogueria.apps@gmail.com";
    private String password = "drogueria96";
    private String correo;
    private String asunto;
    private String mensaje;
    private String modal;
    
    public EnviarEmail() {
        
    }

    public EnviarEmail(String correo, String asunto, String mensaje, String modal) {
        this.correo = correo;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.modal = modal;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getModal() {
        return modal;
    }

    public void setModal(String modal) {
        this.modal = modal;
    }
  
    public void Enviar(){
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.user", "usuario");
            props.put("mail.smtp.port", 25);
            //
            SMTPAuthenticator auth = new SMTPAuthenticator( getFrom(), getPassword() );
            Session session = Session.getDefaultInstance(props, auth);
            session.setDebug(false);
            
            //Se crea destino y origen del mensaje
            MimeMessage mimemessage = new MimeMessage(session);
            InternetAddress addressFrom = new InternetAddress( getFrom() );
            mimemessage.setFrom(addressFrom);
            mimemessage.setRecipients(Message.RecipientType.TO, getCorreo());
            mimemessage.setSubject( getAsunto() );
            
            // Se crea el contenido del mensaje
            MimeBodyPart mimebodypart = new MimeBodyPart();
            mimebodypart.setText( getMensaje());
            mimebodypart.setContent( getMensaje() , "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimebodypart);            
            mimemessage.setContent(multipart);            
            mimemessage.setSentDate(new Date());
            Transport.send(mimemessage);
            
            Modal modals = new Modal(new javax.swing.JFrame(), true);
            modals.TxtMensaje.setText(getModal());
            modals.setVisible(true);
        } catch (MessagingException ex) {
            System.out.println(ex);
        }
    }
    
}
