package sendmessage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static util.Constants.MESSAGE_TO_SEND;
import static util.Constants.PASSWORD_AUTHENTICATOR;

public class EmailConfig {

    private String senderEmail;

    public EmailConfig(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public void send(String recipientEmail) {
        // Recipient's recipientEmail ID needs to be mentioned.

        // Sender's recipientEmail ID needs to be mentioned

        // Assuming you are sending recipientEmail from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.transport.protocol", "smtp");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new MyAuthenticator(this.senderEmail, PASSWORD_AUTHENTICATOR));

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(this.senderEmail));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            // Set Subject: header field
            message.setSubject("Test Message!");

            // Now set the actual message
            message.setText(MESSAGE_TO_SEND);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
