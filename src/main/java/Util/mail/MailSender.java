package Util.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {

    public static void sendMessage(String correo, String messageTxt) {

        final String username = "escomtfjaprueba@gmail.com";
        final String password = "SJL_2019";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(correo+", escomtfjaprueba@gmail.com")
            );
            message.setSubject("Mensaje proyecto redes");
            message.setText(messageTxt);

            Transport.send(message);

            System.out.println("Done Mail");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
