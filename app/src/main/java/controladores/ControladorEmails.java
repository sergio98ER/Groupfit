package controladores;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ControladorEmails extends javax.mail.Authenticator {
    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;

    static {
        Security.addProvider(new JSSEProvider());
    }

    public ControladorEmails() {
        this.user = "groupfitmail@gmail.com";
        this.password = "Patata2k20";

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        session = Session.getDefaultInstance(props, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendMail(String subject, String body, String sender, String recipients) throws Exception {
        try {
            MimeMessage message = new MimeMessage(session);

            DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));

            message.setSender(new InternetAddress(sender));

            message.setSubject(subject);

            message.setDataHandler(handler);

            if (recipients.indexOf(',') > 0) {

                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            } else
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));

            Transport.send(message);

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    public synchronized boolean enviarEmailEntrenador(final String correo) throws MailException, ServidorPHPException {
        boolean enviado = true;
        ControladorUsuario ctrl = new ControladorUsuario();
        ControladorEntrenador ctrlentre = new ControladorEntrenador();
        final Usuario prueba = new Usuario();
        final Entrenador entrenador = new Entrenador();
        entrenador.setEmail(correo);
        ctrlentre.correo(entrenador);
        System.out.println(entrenador.toString());
        if (ctrlentre.correo(entrenador) == true) {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        String mensaje = "¡Hola " + entrenador.getNombre() + "! \nHas solicitado una recuperación de contraseña desde la aplicación GroupFit. \nSu contraseña es la siguiente: " + entrenador.getContrasena() + ". \nSaludos y gracias por su atención.";
                        sendMail("Recuperar contraseña.", mensaje, "groupfitmail@gmail.com", correo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
            enviado = true;
        } else {
            enviado = false;
        }


        return enviado;
    }

    public synchronized boolean enviarEmailUsuario(final String correo) throws MailException, ServidorPHPException {
        boolean enviado = true;
        ControladorUsuario ctrl = new ControladorUsuario();
        final Usuario prueba = new Usuario();

        prueba.setEmail(correo);
        ctrl.verificado(prueba);
        System.out.println(prueba.toString());
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... args) {
                    try {
                        String mensaje = "¡Hola " + prueba.getNombre() + "! \nHas solicitado una recuperación de contraseña desde la aplicación GroupFit. \nSu contraseña es la siguiente: " + prueba.getContrasena() + ". \nSaludos y gracias por su atención.";
                        sendMail("Recuperar contraseña.", mensaje, "groupfitmail@gmail.com", correo);
                    } catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }
                    return null;
                }
            }.execute();
            enviado = true;

        return enviado;
    }





    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }
}
